package com.gdou.dmall.manage;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class DmallManageWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(DmallManageWebApplication.class, args);
	}

}
