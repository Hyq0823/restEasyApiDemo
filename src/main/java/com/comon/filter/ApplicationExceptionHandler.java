package com.comon.filter;

import javax.ws.rs.NotAllowedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.comon.utils.RestMsgUtils;



/**
 * 统一异常处理
 */
@Provider
@Component
public class ApplicationExceptionHandler implements ExceptionMapper<Exception> {
    private static Logger logger = LoggerFactory.getLogger(ApplicationExceptionHandler.class); 

    @Override
    public Response toResponse(Exception e) {
        logger.error(e.getMessage());
        if (e instanceof NotFoundException) {// return Response.status(Status.NOT_FOUND).build();
            return Response.status(Status.NOT_FOUND).build();
        }
        if(e instanceof NotAllowedException){
            return Response.status(Status.METHOD_NOT_ALLOWED).build();
        }
        return RestMsgUtils.fail(e);
    }

}
