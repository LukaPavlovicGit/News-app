package com.example.newsapp.entities;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Comment {
    @NotNull(message = "author field is required")
    @NotEmpty(message = "author field is required")
    private String author;
    @NotNull(message = "content field is required")
    @NotEmpty(message = "content field is required")
    private String content;

    private Long createdAt;

}
