package com.example.newsapp.resource;

import com.example.newsapp.entities.User;
import com.example.newsapp.requests.LoginRequest;
import com.example.newsapp.requests.RegisterRequest;
import com.example.newsapp.requests.UpdateUserRequest;
import com.example.newsapp.service.UserService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
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
    @Path("/admin/register")
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(@Valid RegisterRequest registerRequest){
        Map<String, Object> response = new HashMap<>();
        String email = registerRequest.getEmail();
        String firstname = registerRequest.getFirstname();
        String lastname = registerRequest.getLastname();
        String password = registerRequest.getPassword();
        User user = userService.register(registerRequest.getRole(), firstname, lastname, email, password, registerRequest.getStatus());
        if(user == null){
            response.put("error", "A username is already used...");
            return Response.status(406, "Not acceptable").entity(response).build();
        }
        response.put("message", "User registration Successful");
        return Response.ok(response).build();
    }

    @PUT
    @Path("/admin/update/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") int userId, @Valid UpdateUserRequest updateUserRequest){
        Map<String, Object> response = new HashMap<>();
        Integer id = userId;
        String role = updateUserRequest.getRole();
        String email = updateUserRequest.getEmail();
        String firstname = updateUserRequest.getFirstname();
        String lastname = updateUserRequest.getLastname();
        String password = updateUserRequest.getPassword();
        Boolean status = updateUserRequest.getStatus();
        User user = userService.update(id, role, firstname, lastname, email, password, status);
        if(user == null){
            response.put("error", "A username is already used...");
            return Response.status(406, "Not acceptable").entity(response).build();
        }
        response.put("message", "User update Successful");
        return Response.ok(response).build();
    }
    @PUT
    @Path("/admin/status-activation/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void statusActivation(@PathParam("id") Integer userId){ userService.statusActivation(userId); }

    @PUT
    @Path("/admin/status-deactivation/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void statusDeactivation(@PathParam("id") Integer userId){ userService.statusDeactivation(userId); }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/admin")
    public List<User> getAll(){ return userService.getAll(); }
}
