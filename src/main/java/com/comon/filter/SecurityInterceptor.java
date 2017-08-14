package com.comon.filter;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.core.Headers;
import org.jboss.resteasy.core.ResourceMethodInvoker;
import org.jboss.resteasy.core.ServerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Provider
@Component
public class SecurityInterceptor implements ContainerRequestFilter {
    private Logger logger = LoggerFactory.getLogger(SecurityInterceptor.class);
    
    private static final ServerResponse ACCESS_DENIED = new ServerResponse("Access denied for this resource", 401, new Headers<Object>());;
    private static final ServerResponse ACCESS_FORBIDDEN = new ServerResponse("Nobody can access this resource", 403, new Headers<Object>());;
    private static final ServerResponse SERVER_ERROR = new ServerResponse("internal server error", 500, new Headers<Object>());;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        ResourceMethodInvoker methodInvoker = (ResourceMethodInvoker) requestContext.getProperty("org.jboss.resteasy.core.ResourceMethodInvoker");
        URI uri = requestContext.getUriInfo().getAbsolutePath();
        Method method = methodInvoker.getMethod();
        
        if(method.isAnnotationPresent(PermitAll.class)){
            logger.info("PermitAll: "+uri);
            return;
        }
        
        if(method.isAnnotationPresent(DenyAll.class)){
            logger.info("DenyAll: " + uri);
            requestContext.abortWith(ACCESS_DENIED);
            return;
        }
        
        
        if(method.isAnnotationPresent(RolesAllowed.class)){
          //判断用户是否有权限访问
        }
        
    }

}
