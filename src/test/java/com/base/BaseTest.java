package com.base;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.junit.Test;

public class BaseTest{
    
    
    public static String testGet(String url){
        Client client = ClientBuilder.newBuilder().build();
        WebTarget target = client.target(url);
        Response response = target.request().get();
        String value = response.readEntity(String.class);
        System.out.println("-------------Status:" + response.getStatus() + "--------");
        response.close();  // You should close connections!
        return value;
       // ResteasyClient client = new ResteasyClientBuilder().build();
        //ResteasyWebTarget target = client.target("http://foo.com/resource");
    }
    
    public static String rest3Post(String url,Object data){
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url);
        Invocation.Builder builder = target.request();
        //Entity<String> entity = Entity.entity("hello world", "text/plain");
        Response response = builder.post(Entity.json(data));
        System.out.println("-------------Status:" + response.getStatus() + "--------");
        String result = response.readEntity(String.class);
        return result;
    }

    public static String rest3Get(String url){
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url);
        Invocation.Builder builder = target.request(MediaType.APPLICATION_JSON_TYPE.withCharset("utf-8"));
        Response response = builder.get();
        System.out.println("-------------Status:" + response.getStatus() + "--------");
        String result = response.readEntity(String.class);
        return result;
    }
    
    
    @Test
    public void testPOst(){
        Map<String,Object> data = new HashMap<>();
        data.put("a", "123");
        data.put("b", "反反复复");
        String value = rest3Post("http://localhost:9999/RestEasyApi/cate/1/anlimal3",data); 
        System.out.println(value);
    }
    
    public static void main(String[] args) {
        Map<String,Object> data = new HashMap<>();
        data.put("a", "123");
        data.put("b", "反反复复");
        
//        String value = rest3Get("http://localhost:9999/RestEasyApi/cate/1/anlimal2"); 
        String value = rest3Post("http://localhost:9999/RestEasyApi/cate/1/anlimal3",data); 
//       String value = rest3Get("http://localhost:9999/RestEasyApi/user-management/users");
       // String value = rest3Get("http://localhost:9999/RestEasyApi/listener/ping");
        System.out.println(value);
    }
    
}