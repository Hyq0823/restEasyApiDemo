package com.modules.menu.rest;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.stereotype.Component;

import com.comon.utils.JsonUtils;
import com.comon.utils.RestMsgUtils;
import com.modules.menu.service.MenuService;
import com.test.entity.Menu;


@Component
@Path("/menu")
public class MenuRest {
    
    private Logger logger = LoggerFactory.getLogger(MenuRest.class);

    @Autowired
    private MenuService menuService;
    
    @POST
    @RolesAllowed("authenticated")
    public Response addMenu(String menuJson) throws Exception{
        Menu menu = JsonUtils.json2Obj(menuJson, Menu.class);
        menuService.save(menu);
        return RestMsgUtils.ok();
    }
    
    @DELETE
    @RolesAllowed("authenticated")
    @Path("/{id}")
    public Response deleteMenu(@PathParam("id") String menuId){
        logger.info("删除菜单,id:{}",menuId);
        return RestMsgUtils.ok();
    }
    
    @GET
    @RolesAllowed("authenticated")
    @Path("/{id}")
    public Response getMenuById(@PathParam("id") String menuId){
        logger.info("根据菜单id查找，id:",menuId);
        Menu menu = new Menu("111","菜单名字","/href","mainFrame");
        return RestMsgUtils.ok(menu);
    }
    
    
}
