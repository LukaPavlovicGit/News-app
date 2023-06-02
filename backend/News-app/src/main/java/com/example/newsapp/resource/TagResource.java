package com.example.newsapp.resource;

import com.example.newsapp.entities.Tag;
import com.example.newsapp.service.TagService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
@Path("/tags")
public class TagResource {

    @Inject
    private TagService tagService;

    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    public Tag insert(Tag tag){ return tagService.insert(tag); }

    @GET
    @Path("/{keyword}")
    @Produces(MediaType.APPLICATION_JSON)
    public Tag findByKeyword(@PathParam("keyword") String keyword){ return tagService.findByKeyword(keyword); }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Tag> findAll(){ return tagService.findAll(); }
}
