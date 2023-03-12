package com.dizma.dizmademo.model.enums;

public enum UserRoleEnum {
    ADMIN("Admin"),
    MODERATOR("Moderator"),
    MEMBER("Member");

    private String name;
    UserRoleEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
