package com.example.newsapp.requests;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class RegisterRequest {

    @NotNull(message = "firstname is required")
    @NotEmpty(message = "firstname is required")
    private String firstname;
    @NotNull(message = "lastname is required")
    @NotEmpty(message = "lastname is required")
    private String lastname;
    @NotNull(message = "email is required")
    @NotEmpty(message = "email is required")
    private String email;

    @NotNull(message = "password is required")
    @NotEmpty(message = "password is required")
    private String password;

    private String role;

    public RegisterRequest() {
    }

    public RegisterRequest(String firstname, String lastname, String email, String password, String role) {
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}