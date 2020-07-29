package com.yunan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 启动程序
 * 
 * @author yunan
 */
@EnableSwagger2
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class YunAnApplication
{
    public static void main(String[] args)
    {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(YunAnApplication.class, args);
        System.out.println("欢迎来到云岸后台管理系统-----------------------------");
    }
}