package com.test.service;

import org.springframework.stereotype.Service;

@Service
public class TestService {

    public int service(){
        return 1/0;
    }
}
