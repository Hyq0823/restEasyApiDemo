package com.comon.filter;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.core.ResourceMethodInvoker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Provider
@Component
public class RequestLogFilter implements ContainerRequestFilter {
    private static Logger logger = LoggerFactory.getLogger(RequestLogFilter.class);

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        ResourceMethodInvoker methodInvoker = (ResourceMethodInvoker) requestContext
                .getProperty("org.jboss.resteasy.core.ResourceMethodInvoker");
        Method method = methodInvoker.getMethod();
        UriInfo uriInfo = requestContext.getUriInfo();
        logger.info("=======================================log start==========================================================");
        logger.info(requestContext.getMethod()+"  "+uriInfo.getAbsolutePath().toString()+" "+method.getName());
        
        
        MultivaluedMap<String,String> queryParameters = requestContext.getUriInfo().getQueryParameters();
        List<String> params = new ArrayList<>();
        for(Map.Entry<String,List<String>> me : queryParameters.entrySet()){
            String key = me.getKey();
            List<String> values = me.getValue();
            if(values.size() == 1){
                params.add("\"" + key + "\"" + ":" + "\"" +values.get(0) + "\"");
            }else{
                params.add("\"" + key + "\"" + ":" + "[\"" + String.join(",",values)+ "\"]");
            }
        }
        String param = "{" + String.join(",",params) + "}";
        logger.info(param);
            
    }

}
