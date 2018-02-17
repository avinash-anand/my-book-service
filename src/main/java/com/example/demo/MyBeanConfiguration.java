package com.example.demo;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

@Configuration
public class MyBeanConfiguration {
    
    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
    
    @Bean
    @LoadBalanced
    public AsyncRestTemplate getAsyncRestTemplate() {
        return new AsyncRestTemplate();
    }
    
    @Bean
    public AlwaysSampler alwaysSampler() {
        return new AlwaysSampler();
    }
    
}
