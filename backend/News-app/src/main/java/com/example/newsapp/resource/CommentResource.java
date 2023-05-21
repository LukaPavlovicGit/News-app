package com.example.newsapp.resource;

import com.example.newsapp.entities.Comment;
import com.example.newsapp.service.CommentService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
@Path("/comments")
public class CommentResource {

    @Inject
    private CommentService commentService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Comment create(@Valid Comment comment){ return commentService.insert(comment); }

    @GET
    @Path("/{newsId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Comment> getAllByNewsId(@PathParam("newsId") Integer newsId){ return commentService.findAllByNewsId(newsId); }

//    @GET
//    @Path("/byPostId/{id}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public List<Comment> getByPostId(@PathParam("id") int id) { return commentService.getAllByPostId(id); }
//
//    @GET
//    @Path("/{id}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Comment getById(@PathParam("id") int id) {return commentService.findById(id); }
//
//    @DELETE
//    @Path("/{id}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public void delete(@PathParam("id") int id) { commentService.delete(id); }
}
