package com.modules.menu.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.comon.service.BaseService;
import com.comon.utils.BeanValidators;
import com.test.entity.Menu;

@Service
public class MenuService  extends BaseService{

    @Transactional(readOnly=false,propagation=Propagation.REQUIRED)
    public void save(Menu menu)throws Exception{
        BeanValidators.validateWithException(validator, menu);
        //模拟dao save()
        System.out.println("执行存储菜单操作......");
    } 
}
