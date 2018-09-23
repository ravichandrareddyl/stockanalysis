package com.ravi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiWrapper {

    @Autowired
    private RestTemplate restTemplate;

}