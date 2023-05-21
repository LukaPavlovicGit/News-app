package com.example.newsapp.resource;

import com.example.newsapp.entities.News;
import com.example.newsapp.service.NewsService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
@Path("/news")
public class NewsResource {

    @Inject
    private NewsService newsService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public News create(@Valid News news){ return newsService.insert(news); }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public News findById(@PathParam("id") Integer id) { return newsService.findById(id); }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<News> findAll(){ return newsService.findAll(); }

    @GET
    @Path("/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<News> findAllByCategory(@PathParam("name") String categoryName){ return newsService.findAllByCategory(categoryName); }

}
