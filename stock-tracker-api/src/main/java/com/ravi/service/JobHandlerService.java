package com.ravi.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class JobHandlerService {

    @Value("${tracker.host}")
    private String host;

    public void trackSavedJobs() {
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.postForObject("http://"+host+":8081/trackOrder", null, String.class);
    }
}