package com.microlittleblog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author jihongyuan
 * @date 2020/2/28 22:55
 */

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableCaching()
public class MicroLittleBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroLittleBlogApplication.class, args);
    }

}