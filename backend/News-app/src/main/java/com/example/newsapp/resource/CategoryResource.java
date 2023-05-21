package com.example.newsapp.resource;

import com.example.newsapp.entities.Category;
import com.example.newsapp.service.CategoryService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
@Path("/comments")
public class CategoryResource {

    @Inject
    private CategoryService categoryService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Category create(@Valid Category category){ return categoryService.insert(category); }

    @GET
    @Path("/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Category getById(@PathParam("name") String name) {return categoryService.findByName(name); }
}
