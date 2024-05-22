package com.lelar.runner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication(scanBasePackages = "com.lelar.*")
@EnableJdbcRepositories(basePackages = "com.lelar.database.dao")
//@EnableWebMvc
//@EnableWebSecurity
public class ApplicationRunner {


    public static void main(String[] args) {
        SpringApplication.run(ApplicationRunner.class);
    }

}
