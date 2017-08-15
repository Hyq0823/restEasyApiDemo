package com.comon.service;

import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
public class BaseService {
    @Autowired
    protected Validator validator;
}
