package com.icetea.monstu_back.r2dbc.sqlBuilder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class KClassGenerator {

    private static final String PACKAGE_NAME = "com.kclass.generated";
    private static final String BASE_PATH = "src/generated-classes";

    public void process() {
        createPackageStructure();
        generateKClassInterface();
        generateKClasses();
    }

    private static void generateKClassInterface() {
        String classContent = String.format("package %s;\n\npublic interface KClass {}", PACKAGE_NAME);
        saveClassToFile("KClass", classContent);
    }

    private static void generateKClasses() {
        AnnotationReader.init();
        Map<String, Map<String, String>> classInfo = AnnotationReader.getColumnMapping();

        for (Map.Entry<String, Map<String, String>> entry : classInfo.entrySet()) {
            String className = "K" + entry.getKey();
            String classContent = generateClassContent(entry, className);
            saveClassToFile(className, classContent);
        }
    }

    private static String generateClassContent(Map.Entry<String, Map<String, String>> entry, String className) {
        StringBuilder classContent = new StringBuilder();
        classContent.append("package ").append(PACKAGE_NAME).append(";\n\n")
                .append("public class ").append(className).append(" implements KClass {\n");

        StringBuilder withNickMethod = new StringBuilder();
        withNickMethod.append("\n    public ").append(className).append(" withNick(String nick) {\n");

        // 필드 정의 및 withNick 메서드 작성
        for (Map.Entry<String, String> field : entry.getValue().entrySet()) {
            String fieldName = field.getKey();
            classContent.append("    public String ").append(fieldName)
                    .append(" = \"").append(field.getValue()).append("\";\n");
            withNickMethod.append("        this.").append(fieldName)
                    .append(" = nick + \".\" + this.").append(fieldName).append(";\n");
        }

        classContent.append(withNickMethod)
                .append("        return this;\n")
                .append("    }\n")
                .append("}\n");

        return classContent.toString();
    }

    private static void saveClassToFile(String className, String classContent) {
        File file = new File(BASE_PATH + "/" + PACKAGE_NAME.replace('.', '/') + "/" + className + ".java");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(classContent);
            System.out.println("Class " + className + " generated at: " + file.getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createPackageStructure() {
        File packageDir = new File(BASE_PATH + "/" + PACKAGE_NAME.replace('.', '/'));
        if (!packageDir.exists() && !packageDir.mkdirs()) {
            throw new RuntimeException("디렉토리 생성 실패: " + packageDir.getPath());
        }
        System.out.println("Package structure created at: " + packageDir.getPath());
    }

    public static void main(String[] args) {
        new KClassGenerator().process();
    }
}
