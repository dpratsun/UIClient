package com.kator.weightguard.ui.entity;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class Users {

    @JacksonXmlProperty(localName = "user")
    private List<User> users = new ArrayList<>();

    public Users() {
    }

    public Users(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        String listString = users.stream().map(Object::toString).collect(Collectors.joining(", "));

        return "{" + listString + "}";
    }
}
