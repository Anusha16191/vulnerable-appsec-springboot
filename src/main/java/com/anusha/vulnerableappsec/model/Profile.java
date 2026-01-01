package com.anusha.vulnerableappsec.model;

import java.io.Serializable;

public class Profile implements Serializable {

    private String name;
    private String email;

    public Profile() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
