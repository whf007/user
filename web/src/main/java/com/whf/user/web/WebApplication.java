package com.whf.user.web;

import com.whf.user.common.process.ProcessFactory;
import com.whf.user.common.req.Request;
import com.whf.user.common.resp.Response;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.web.SpringBootServletInitializer;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;

@SpringBootApplication
//@EnableEurekaClient
@MapperScan("com.whf.user.dao.mapper")
//@ComponentScan(basePackages={ "com.whf.user" })
public class WebApplication  {
	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
	}
}

