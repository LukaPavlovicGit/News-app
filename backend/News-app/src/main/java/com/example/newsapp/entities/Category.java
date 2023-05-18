package com.example.newsapp.entities;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


public class Category {
    private Integer id;
    @NotNull(message = "name field is required")
    @NotEmpty(message = "name field is required")
    private String name;
    @NotNull(message = "description field is required")
    @NotEmpty(message = "description field is required")
    private String description;

    public Category() {  }

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}