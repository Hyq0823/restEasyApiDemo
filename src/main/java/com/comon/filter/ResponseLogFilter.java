package com.comon.filter;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.comon.utils.JsonUtils;


@Component
@Provider
public class ResponseLogFilter implements ContainerResponseFilter {
    private static Logger logger = LoggerFactory.getLogger(ResponseLogFilter.class);

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)throws IOException {
        Object entity = responseContext.getEntity();
        logger.info(JsonUtils.obj2Json(entity));
        logger.info("=======================================log end==========================================================\r\n");
    }

}
