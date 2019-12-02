package com.kator.weightguard.ui.entity;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.kator.weightguard.ui.enums.UserOperations;

import java.util.List;

public final class User {
    @JacksonXmlProperty(localName = "name")
    private String name;

    @JacksonXmlProperty(localName = "password")
    private String password;

    public User(String name, String password){
        this.name = name;
        this.password = password;
    }

    public String getName() { return name; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    @Override
    public String toString() {
        return "User{" +
                ", name=" + name +
                ", password='" + password + '\'' +
                '}';
    }
}
