package com.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.BasicAuthentication;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataOutput;
import org.junit.Test;

public class BaseTest{
    public static Builder builder(String url){
        WebTarget target = ClientBuilder.newClient().target(url);
        target.register(new BasicAuthentication("hyq","123"));
        try {
            return target.request(MediaType.APPLICATION_JSON_TYPE.withCharset("utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private static Response postJson(String url, Object data) {
        return builder(url).post(Entity.json(data));
    }
    
    
    private static Response get(String url) {
        return builder(url).get();
    }
    
    private static Response delete(String url){
        return builder(url).delete();
    }
    
    private static Response put(String url,Object data){
        return builder(url).put(Entity.json(data));
    }
    
    private static String outputResponse(Response response){
        String result = response.readEntity(String.class);
        System.out.println("-------------Status:" + response.getStatus() + "--------");
        System.out.println("-------------result:" + result + "--------");
        return result; 
    }
   

  //====================================================================================================
    public static String executePost(String url,Object data){
        Response response = postJson(url,data);
        return outputResponse(response);
     
    }
    
    public static String executeGet(String url){
        Response response =get(url);
        return outputResponse(response);
    }
    
    public static String executeDelete(String url){
        Response response = delete(url);
        return outputResponse(response);
    }
    
    public static String executePut(String url,Object data){
        Response response = put(url, data);
        return outputResponse(response);
    }
    
    public static String executeUploadFile(String url,File file,Map<String,String> params){
       WebTarget target = ClientBuilder.newClient().target(url);
       MultipartFormDataOutput mfo = new MultipartFormDataOutput();
       String ret = "";
       try{
           //public OutputPart addFormData(String key, Object entity, MediaType mediaType, String filename)
           mfo.addFormData("file", new FileInputStream(file),MediaType.APPLICATION_OCTET_STREAM_TYPE,file.getName());
           if(params!=null){
               for(Map.Entry<String,String> me: params.entrySet()){
                   mfo.addFormData(me.getKey(), me.getValue(),MediaType.TEXT_PLAIN_TYPE.withCharset("utf-8"));
               }
               /*Constructs a new generic entity. Derives represented class from type
               * parameter. Note that this constructor is protected, users should create
               * a (usually anonymous) subclass as shown above.
               * @param entity the entity instance, must not be {@code null}.
               * @throws IllegalArgumentException if entity is {@code null}.*/
               GenericEntity<MultipartFormDataOutput> entity = new GenericEntity<MultipartFormDataOutput>(mfo){};
               Response response = target.request().post(Entity.entity(entity, MediaType.MULTIPART_FORM_DATA_TYPE));
               ret = response.readEntity(String.class);
               response.close();
               System.out.println("-------------Status :" + response.getStatus() + "--------");
               System.out.println("-------------Result :" + ret + "--------");
           }
        
       } catch (FileNotFoundException e) {
           e.printStackTrace();
    }
    return ret;
 }
    
    
    @Test
    public void testPOst(){
        Map<String,Object> data = new HashMap<>();
        data.put("a", "123");
        data.put("b", "123");
        
        String value = executePost("http://localhost:9999/RestEasyApi/menu",data); 
        System.out.println(value);
    }
    
    public static void main(String[] args) {
        Map<String,String> data = new HashMap<>();
        data.put("a", "123");
        data.put("b", "123");
        File file = new File("C:/1.xls");
        String value = executeUploadFile("http://localhost:9999/RestEasyApi/menu/upload", file, data);
        System.out.println(value);
        
        //String value = executePost("http://localhost:9999/RestEasyApi/menu",data); 
//        String value = rest3Get("http://localhost:9999/RestEasyApi/cate/1/anlimal2"); 
//        String value = rest3Post("http://localhost:9999/RestEasyApi/menu/222",data); 
//        String value = rest3Get("http://localhost:9999/RestEasyApi/menu/222"); 
//        System.out.println(value);
    }
    
}