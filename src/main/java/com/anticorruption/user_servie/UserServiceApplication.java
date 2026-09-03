package com.anticorruption.user_servie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Hello world!
 */
@SpringBootApplication(scanBasePackages = "com.anticorruption.user.*")
@EnableJpaRepositories(basePackages = "com.anticorruption.user.*")
@EntityScan(basePackages = "com.anticorruption.user.*")
@EnableFeignClients(basePackages = "com.anticorruption.user.*")
public class UserServiceApplication {
    public static void main(String[] args) {
       SpringApplication.run(UserServiceApplication.class, args);
    }
}
