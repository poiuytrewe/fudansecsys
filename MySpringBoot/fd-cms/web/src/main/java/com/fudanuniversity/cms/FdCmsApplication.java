package com.fudanuniversity.cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
@EnableScheduling //开启定时任务
////@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class FdCmsApplication {

    public static void main(String[] args) {

        SpringApplication.run(FdCmsApplication.class, args);
        System.out.println("================服务器启动成功================\n");

    }

    @PostConstruct
    void started(){
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
    }
}
