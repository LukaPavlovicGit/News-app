package com.example.newsapp.filters;

import com.example.newsapp.service.UserService;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
public class AuthFilter implements ContainerRequestFilter {

    @Inject
    private UserService userService;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        boolean authenticationRequired = this.isAuthenticationRequired(requestContext);
        boolean authorizationRequired = this.isAuthorizationRequired(requestContext);

        if (!authenticationRequired && !authorizationRequired) {
            return;
        }

        try {
            String token = requestContext.getHeaderString("Authorization");
            if(token != null && token.startsWith("Bearer ")) {
                token = token.replace("Bearer ", "");
            }

            if(authorizationRequired && !this.userService.isHighAuthorized(token)){
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            }
            else if(authenticationRequired && !this.userService.isLowAuthorized(token)){
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            }

        } catch (Exception exception) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }

    private boolean isAuthenticationRequired(ContainerRequestContext req) {
        if (req.getUriInfo().getPath().contains("login")) {
            return false;
        }

//        List<Object> matchedResources = req.getUriInfo().getMatchedResources();
//        for (Object matchedResource : matchedResources) {
//            if (matchedResource instanceof PostResource) {
//                return true;
//            }
//            if (matchedResource instanceof CommentResource) {
//                return true;
//            }
//        }

        return false;
    }

    private boolean isAuthorizationRequired(ContainerRequestContext req) {
        if (req.getUriInfo().getPath().contains("register")) {
            return true;
        }

        return false;
    }
}
