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

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public News update(@PathParam("id") Integer id, @Valid News news){ return null; }

    @PUT
    @Path("/visited/{newsId}")
    @Produces(MediaType.APPLICATION_JSON)
    public News visited(@PathParam("newsId") Integer newsId){ return null; }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public News delete(@PathParam("id") Integer id){ return null; }

    @GET
    @Path("/most-read")
    @Produces(MediaType.APPLICATION_JSON)
    public News getMostRead(){ return null; }

    @GET
    @Path("/by-category/{categoryName}")
    @Produces(MediaType.APPLICATION_JSON)
    public News getByCategoryName(@PathParam("categoryName") String categoryName){ return null; }

    @GET
    @Path("/by-tag/{tagName}")
    @Produces(MediaType.APPLICATION_JSON)
    public News getByTagName(@PathParam("tagName") String tagName){ return null; }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public News findById(@PathParam("id") Integer id) { return newsService.findById(id); }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<News> findAll(@QueryParam("page") int page){ return newsService.findAll(page); }

}
