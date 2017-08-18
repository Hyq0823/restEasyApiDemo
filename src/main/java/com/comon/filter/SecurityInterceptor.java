package com.comon.filter;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.core.Headers;
import org.jboss.resteasy.core.ResourceMethodInvoker;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Provider
@Component
public class SecurityInterceptor implements ContainerRequestFilter {
    private Logger logger = LoggerFactory.getLogger(SecurityInterceptor.class);
    private static final String AUTHORIZATION_PROPERTY = "Authorization";
    private static final String AUTHENTICATION_SCHEME = "Basic";

    private static final ServerResponse ACCESS_DENIED = new ServerResponse("Access denied for this resource", 401,new Headers<Object>());;
    private static final ServerResponse ACCESS_FORBIDDEN = new ServerResponse("Nobody can access this resource", 403,new Headers<Object>());;
    private static final ServerResponse SERVER_ERROR = new ServerResponse("internal server error", 500,new Headers<Object>());;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        ResourceMethodInvoker methodInvoker = (ResourceMethodInvoker) requestContext.getProperty("org.jboss.resteasy.core.ResourceMethodInvoker");
        URI uri = requestContext.getUriInfo().getAbsolutePath();
        String path = uri.getPath();
        Method method = methodInvoker.getMethod();

        if (method.isAnnotationPresent(PermitAll.class)) {
            logger.info("PermitAll: " + uri);
            return;
        }

        if (method.isAnnotationPresent(DenyAll.class)) {
            logger.info("DenyAll: " + uri);
            requestContext.abortWith(ACCESS_FORBIDDEN);
            return;
        }

        MultivaluedMap<String, String> headers = requestContext.getHeaders();
        String authorization = headers.getFirst(AUTHORIZATION_PROPERTY);

        if (authorization == null || authorization.isEmpty()) {
            logger.warn("authorization header is empty....");
            requestContext.abortWith(ACCESS_DENIED);
            return;
        }
        String encodedUserPassword = authorization.replaceFirst(AUTHENTICATION_SCHEME + " ", "");
        String usernameAndPassword;
        try {
            usernameAndPassword = new String(Base64.decode(encodedUserPassword));
        } catch (IOException e) {
            logger.warn("authorization decode failed!");
            requestContext.abortWith(SERVER_ERROR);
            return;
        }
        // Split username and password tokens
        logger.info("token decode result: {}", usernameAndPassword);
        final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
        final String username = tokenizer.nextToken();
        final String password = tokenizer.nextToken();

        // Verify user access
        if (method.isAnnotationPresent(RolesAllowed.class)) {
            // RolesAllowed rolesAnnotation =
            // method.getAnnotation(RolesAllowed.class);
            // Set<String> rolesSet = new
            // HashSet<String>(Arrays.asList(rolesAnnotation.value()));
            if (!isUserAllowed(username, path)) {
                requestContext.abortWith(ACCESS_DENIED);
            }
        }
        // Return to continue request processing
        return;
    }

    private boolean isUserAllowed(String username, String path) {
        logger.info("username:{}====req===>:{}", username, path);
        boolean isAllowed = false;
        String userRole = "hyqid";
        if (userRole.equals(username)) {
            isAllowed = true;
        }
        return isAllowed;
    }

}
