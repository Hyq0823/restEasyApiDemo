package com.modules.menu.rest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.comon.utils.ConstantUtils;
import com.comon.utils.JsonUtils;
import com.comon.utils.RestMsgUtils;
import com.modules.menu.service.MenuService;
import com.test.entity.Menu;


@Component
@Path("/menu")
public class MenuRest {
    private final String UPLOADED_FILE_PATH = "d:\\resteasy\\";
    private Logger logger = LoggerFactory.getLogger(MenuRest.class);

    @Autowired
    private MenuService menuService;
    
    @POST
    @PermitAll
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(ConstantUtils.CONTENT_TYPE_UTF8)
    @Path("/upload")
    public Response upload(MultipartFormDataInput input)throws Exception{
        String fileName = "";
        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
        List<InputPart> inputParts = uploadForm.get("file");
        for (InputPart inputPart : inputParts) {
         try {
            MultivaluedMap<String, String> header = inputPart.getHeaders();
            fileName = getFileName(header);
            //convert the uploaded file to inputstream
            InputStream inputStream = inputPart.getBody(InputStream.class,null);
            byte [] bytes = IOUtils.toByteArray(inputStream);
            //constructs upload file path
            fileName = UPLOADED_FILE_PATH + fileName;
            writeFile(bytes,fileName);
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
        return  RestMsgUtils.ok(fileName);
    }
    
    private String getFileName(MultivaluedMap<String, String> header) {
        String[] contentDisposition = header.getFirst("Content-Disposition").split(";");
        for (String filename : contentDisposition) {
            if ((filename.trim().startsWith("filename"))) {
                String[] name = filename.split("=");
                String finalFileName = name[1].trim().replaceAll("\"", "");
                return finalFileName;
            }
        }
        return "unknown";
    }
 
    //save to somewhere
    private void writeFile(byte[] content, String filename) throws IOException {
        File file = new File(filename);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream fop = new FileOutputStream(file);
        fop.write(content);
        fop.flush();
        fop.close();
    }
    
    
    @GET
    @PermitAll
    @Produces(ConstantUtils.CONTENT_TYPE_UTF8)
    @Path("/info")
    public Response info(){
        Map<String,Object> info = new HashMap<String,Object>();
        info.put("notice","notices");
        info.put("topic", "topic");
        return RestMsgUtils.ok(info);
    }
    
    @GET
    @DenyAll
    @Produces(ConstantUtils.CONTENT_TYPE_UTF8)
    @Path("/deny")
    public Response deny(){
        Map<String,Object> info = new HashMap<String,Object>();
        info.put("notice","notices");
        info.put("topic", "topic");
        return RestMsgUtils.ok(info);
    }
    
    
    
    
    @POST
    @RolesAllowed("authenticated")
    @Produces(ConstantUtils.CONTENT_TYPE_UTF8)
    public Response addMenu(String menuJson) throws Exception{
        Menu menu = JsonUtils.json2Obj(menuJson, Menu.class);
        menuService.save(menu);
        return RestMsgUtils.ok();
    }
    
    @DELETE
    @RolesAllowed("authenticated")
    @Path("/{id}")
    @Produces(ConstantUtils.CONTENT_TYPE_UTF8)
    public Response deleteMenu(@PathParam("id") String menuId){
        logger.info("删除菜单,id:{}",menuId);
        return RestMsgUtils.ok();
    }
    
    @GET
    @RolesAllowed("authenticated")
    @Path("/{id}")
    @Produces(ConstantUtils.CONTENT_TYPE_UTF8)
    public Response getMenuById(@PathParam("id") String menuId){
        logger.info("根据菜单id查找，id:",menuId);
        Menu menu = new Menu("111","菜单名字","/href","mainFrame");
        return RestMsgUtils.ok(menu);
    }
    
    
}
