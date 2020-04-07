package com.kill;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "com.kill.mapper")
@SpringBootApplication
public class KillApplication {

    public static void main(String[] args) {
        SpringApplication.run(KillApplication.class, args);
    }

}
