package com.patsnap.magic.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * Created by Owen on 2018/1/3.
 */
@EnableZuulProxy
@EnableEurekaClient
@SpringBootApplication(scanBasePackages = "com.patsnap.magic.store")
public class ZuulServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZuulServerApplication.class, args);
    }
}
