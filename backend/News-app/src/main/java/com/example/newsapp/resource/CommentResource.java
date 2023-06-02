package com.example.newsapp.resource;

import com.example.newsapp.entities.Comment;
import com.example.newsapp.service.CommentService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
@Path("/comments")
public class CommentResource {

    @Inject
    private CommentService commentService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Comment create(@Valid Comment comment){
        System.out.println(comment);
        return commentService.insert(comment);
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Comment update(@PathParam("id") Integer id, Comment comment){
        comment.setId(id);
        return commentService.update(comment);
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Comment delete(@PathParam("id") Integer id){ return commentService.delete(id); }

    @GET
    @Path("/by-news/{newsId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Comment> getAllByNewsId(@PathParam("newsId") Integer newsId){ return commentService.findAllByNewsId(newsId); }

}
