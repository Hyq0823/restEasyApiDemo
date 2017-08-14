package com.comon.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class RestMsgUtils {
   
    
    /**
     *调用失败
     */
    public static Response fail(Exception e){
        Map<String,Object> result = new HashMap<>();
        result.put(ConstantUtils.ERROR_CODE,ErrorUtils.SYSTEM_ERROR);
        if(e instanceof ConstraintViolationException){
            List<String> msgs = BeanValidators.extractMessage((ConstraintViolationException)e);
            result.put(ConstantUtils.ERROR_MSG,String.join(",",msgs));
        }else{
            result.put(ConstantUtils.ERROR_MSG,e.getMessage()==null?"未知错误":e.getMessage());
        }
        result.put(ConstantUtils.DATA,new HashMap<String,Object>());
        return Response.status(Status.BAD_REQUEST).entity(result).build();
    }
    
    
    /**
     * 响应成功
     * @param data
     * @return
     */
    public static Response ok(Object data){
        Map<String,Object> result = new HashMap<>();
        result.put(ConstantUtils.ERROR_CODE,ErrorUtils.SUCCESS);
        result.put(ConstantUtils.ERROR_MSG,"处理成功!");
        result.put("data",data);
        return Response.status(Status.OK).entity(result).build();
    }
   
}
