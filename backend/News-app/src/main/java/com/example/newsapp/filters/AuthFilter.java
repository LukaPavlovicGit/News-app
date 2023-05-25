package com.example.newsapp.filters;

import com.example.newsapp.resource.*;
import com.example.newsapp.service.UserService;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.List;

@Provider
public class AuthFilter implements ContainerRequestFilter {

    @Inject
    UserService userService;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        if(requestContext.getUriInfo().getPath().contains("login")){
            return;
        }

        try {
            String token = requestContext.getHeaderString("Authorization");
            if(token != null && token.startsWith("Bearer ")) {
                token = token.replace("Bearer ", "");
            }

            if(this.isAuthorizationRequired(requestContext) && !this.userService.isAuthorized(token)){
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            }
            else if(this.isAuthenticationRequired(requestContext) && !this.userService.isAuthenticated(token)){
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            }

        } catch (Exception exception) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }

    private boolean isAuthenticationRequired(ContainerRequestContext req) {
        List<Object> matchedResources = req.getUriInfo().getMatchedResources();
        for (Object matchedResource : matchedResources) {
            if (    matchedResource instanceof CategoryResource ||
                    matchedResource instanceof CommentResource ||
                    matchedResource instanceof NewsResource ||
                    matchedResource instanceof TagResource ||
                    matchedResource instanceof UserResource     ){
                return true;
            }
        }
        return false;
    }

    private boolean isAuthorizationRequired(ContainerRequestContext req) {
        if (req.getUriInfo().getPath().contains("admin")) {
            return true;
        }
        return false;
    }
}
