package com.icetea.monstu_back.util;

import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Objects;

@Component
public class FileManager {

    // src 경로
    public String getSrcPath(){
        return new File("src").getAbsolutePath();
    }

}
