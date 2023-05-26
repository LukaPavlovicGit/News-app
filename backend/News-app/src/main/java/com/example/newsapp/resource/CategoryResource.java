package com.example.newsapp.resource;

import com.example.newsapp.entities.Category;
import com.example.newsapp.service.CategoryService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/categories")
public class CategoryResource {

    @Inject
    private CategoryService categoryService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Category> getAll(){ return categoryService.getAll();  }

    @POST
    @Path("/content-creator")
    @Produces(MediaType.APPLICATION_JSON)
    public Category create(@Valid Category category){ return categoryService.insert(category); }
    @PUT
    @Path("/content-creator")
    @Produces(MediaType.APPLICATION_JSON)
    public Category update(Category category){ return categoryService.update(category); }
    @DELETE
    @Path("/content-creator/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Category delete(@PathParam("id") Integer id) {return categoryService.delete(id); }
}
