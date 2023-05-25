package com.example.newsapp.resource;

import com.example.newsapp.entities.User;
import com.example.newsapp.requests.LoginRequest;
import com.example.newsapp.requests.RegisterRequest;
import com.example.newsapp.requests.UpdateUserRequest;
import com.example.newsapp.service.UserService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/users")
public class UserResource {

    @Inject
    private UserService userService;

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@Valid LoginRequest loginRequest){
        Map<String, String> response = new HashMap<>();

        String jwt = this.userService.login(loginRequest.getEmail(), loginRequest.getPassword());
        if (jwt == null) {
            response.put("error", "These credentials do not match our records");
            return Response.status(422, "Unprocessable Entity").entity(response).build();
        }

        response.put("jwt", jwt);
        return Response.ok(response).build();
    }

    @POST
    @Path("/register")
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(@Valid RegisterRequest registerRequest){
        Map<String, Object> response = new HashMap<>();
        String email = registerRequest.getEmail();
        String firstname = registerRequest.getFirstname();
        String lastname = registerRequest.getLastname();
        String password = registerRequest.getPassword();
        User user = userService.register(registerRequest.getRole(), firstname, lastname, email, password);
        if(user == null){
            response.put("error", "A username is already used...");
            return Response.status(406, "Not acceptable").entity(response).build();
        }
        response.put("message", "User registration Successful");
        return Response.ok(response).build();
    }

    @POST
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@Valid UpdateUserRequest updateUserRequest){
        Map<String, Object> response = new HashMap<>();
        Integer id = updateUserRequest.getId();
        String role = updateUserRequest.getRole();
        String username = updateUserRequest.getEmail();
        String firstname = updateUserRequest.getFirstname();
        String lastname = updateUserRequest.getLastname();
        String password = updateUserRequest.getPassword();
        User user = userService.update(id, role, username, firstname, lastname, password);
        if(user == null){
            response.put("error", "A username is already used...");
            return Response.status(406, "Not acceptable").entity(response).build();
        }
        response.put("message", "User update Successful");
        return Response.ok(response).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getAll(){ return userService.getAll(); }
}
