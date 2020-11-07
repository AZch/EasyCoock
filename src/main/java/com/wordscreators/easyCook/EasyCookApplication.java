package com.wordscreators.easyCook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import com.wordscreators.easyCook.common.config.AppProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class EasyCookApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasyCookApplication.class, args);
    }

}
