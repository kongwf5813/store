package com.patsnap.magic.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Created by Owen on 2018/1/2.
 */
@EnableEurekaServer
@SpringBootApplication(scanBasePackages = "com.patsnap.magic.store")
public class EurekaServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }
}
