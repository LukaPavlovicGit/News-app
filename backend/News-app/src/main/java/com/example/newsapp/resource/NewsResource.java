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
    @Path("/content-creator")
    @Produces(MediaType.APPLICATION_JSON)
    public News create(@Valid News news){ return newsService.insert(news); }

    @PUT
    @Path("/content-creator/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public News update(@PathParam("id") Integer id, News news){
        news.setId(id);
        return newsService.update(news);
    }

    @PUT
    @Path("/visited/{newsId}")
    @Produces(MediaType.APPLICATION_JSON)
    public News visited(@PathParam("newsId") Integer newsId){ return newsService.newsVisited(newsId); }

    @DELETE
    @Path("/content-creator/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public News delete(@PathParam("id") Integer id){ return newsService.delete(id); }

    @GET
    @Path("/most-read")
    @Produces(MediaType.APPLICATION_JSON)
    public List<News> getMostRead(){ return newsService.mostRead(); }

    @GET
    @Path("/by-category/{categoryId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<News> getByCategoryName(@PathParam("categoryId") Integer categoryId){ return newsService.findAllByCategory(categoryId); }

    @GET
    @Path("/by-tag/{tagName}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<News> getByTagName(@PathParam("tagName") String tagName){ return newsService.findAllByTag(tagName); }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public News findById(@PathParam("id") Integer id) { return newsService.findById(id); }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<News> findAll(@QueryParam("page") int page){ return newsService.findAll(page); }

}
