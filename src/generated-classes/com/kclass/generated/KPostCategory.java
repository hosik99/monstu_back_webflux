package com.kclass.generated;

public class KPostCategory implements KClass {
    public String id = "id";
    public String category = "category";
    public String table = "post_category";

    public KPostCategory withNick(String nick) {
        this.id = nick + "." + this.id;
        this.category = nick + "." + this.category;
        this.table = nick + "." + this.table;
        return this;
    }
}
