package com.ms.user.enums;

public enum UserRole {
    ADMIN("Admin - Can manager the books"),
    CLIENT("Client - Can get books");

    private String description;

    UserRole(String description) {
        this.description = description;
    }

    public String getDescription(){
        return description;
    }
}
