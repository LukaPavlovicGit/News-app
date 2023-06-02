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
    public List<Category> getAll(@QueryParam("page") Integer page){ return categoryService.getAll(page); }
    @GET
    @Path("/by-id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Category getById(@PathParam("id") Integer id){ return categoryService.getById(id); }
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Category create(@Valid Category category){ return categoryService.insert(category); }
    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Category update(@PathParam("id") Integer id, Category category){
        category.setId(id);
        return categoryService.update(category);
    }
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Category delete(@PathParam("id") Integer id) {return categoryService.delete(id); }
}
