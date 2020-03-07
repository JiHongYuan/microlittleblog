package com.microlittleblog.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author jihongyuan
 * @date 2020/2/29 16:50
 */

@Configuration
@EnableAspectJAutoProxy(exposeProxy = true)
@MapperScan("com.microlittleblog.**.mapper")
public class ApplicationConfig {
}
