package com.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.comon.utils.ConstantUtils;
import com.comon.utils.JsonUtils;
import com.comon.utils.RestMsgUtils;
import com.test.entity.Menu;
import com.test.service.TestService;

@Path("/cate")
@Component
public class Test {

    Logger logger = LoggerFactory.getLogger(Test.class);
    
    @Autowired
    TestService testService;
    
    @GET
    @Path("/{cateId}/anlimal")
    @Produces("application/json")
    @DenyAll
    public String getSomeResponse()throws Exception{
        throw new Exception("测试异常");
        
    }
    
    
    @GET
    @Path("/{cateId}/anlimal2")
    @Produces("application/json")
    @PermitAll
    public Response getSomeResponse2()throws Exception{
        try{
            Map<String,Object> result = new HashMap<>();
            result.put("b", 2);
            result.put("c", testService.service());
            return RestMsgUtils.ok(result);
        }catch(Exception e){
            logger.error(e.getMessage());
            return RestMsgUtils.fail(e);
        }
       
    }
    
    
    @POST
    @Path("/{cateId}/anlimal3")
    @Produces(ConstantUtils.CONTENT_TYPE_UTF8)
    @RolesAllowed("authenticated")
    public String getSomeResponse3(String params)throws Exception{
        System.out.println(params);
       return params;
    }
    
    
    
    
    @GET
    @RolesAllowed("authenticated")
    @Path("/menu/{id}")
    public Response get(@PathParam("id") String id){
        Menu menu1 = new Menu(id,"系统管理","/sys","");
        Menu menu2 = new Menu(id,"车辆管理","/sys","");
        Menu menu3 = new Menu(id,"运营管理","/sys","");
        Menu menu4 = new Menu(id,"监控管理","/sys","");
        List<Menu> list = new ArrayList<>();
        list.add(menu1);
        list.add(menu2);
        list.add(menu3);
        list.add(menu4);
        return RestMsgUtils.ok(list);
    }
}
