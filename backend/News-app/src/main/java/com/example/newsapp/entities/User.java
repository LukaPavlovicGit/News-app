package com.example.newsapp.entities;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class User {
    private int privilege;
    @NotNull(message = "firstname field is required")
    @NotEmpty(message = "firstname field is required")
    private String firstname;
    @NotNull(message = "lastname field is required")
    @NotEmpty(message = "lastname field is required")
    private String lastname;
    @NotNull(message = "email field is required")
    @NotEmpty(message = "email field is required")
    private String email;
    @NotNull(message = "password field is required")
    @NotEmpty(message = "password field is required")
    private String hashedPassword;
    private boolean status;

    public User() { }

    public User(int privilege, String firstname, String lastname, String email, String hashedPassword, boolean status) {
        this.privilege = privilege;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.status = status;
    }

    public int getPrivilege() {
        return privilege;
    }

    public void setPrivilege(int privilege) {
        this.privilege = privilege;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
