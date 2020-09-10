package com.chenjianxiong.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@ComponentScan(basePackages = {"com.chenxiaoyu.cloud.controller"})
@ComponentScan(basePackages = {"com.chenxiaoyu.cloud.service"})
public class CloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudApplication.class, args);
    }

}
