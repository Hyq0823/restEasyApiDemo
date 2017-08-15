package com.menu;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.base.BaseTest;

public class MenuTest extends BaseTest {
    private Map<String,Object> data = new HashMap<>();
  

    @Test
    public void testGetMenu(){
        data.put("a", "123");
        data.put("b", "反反复复");
        String value = rest3Post("http://localhost:9999/RestEasyApi/menu/222",data); 
       System.out.println(value);
    }
}
