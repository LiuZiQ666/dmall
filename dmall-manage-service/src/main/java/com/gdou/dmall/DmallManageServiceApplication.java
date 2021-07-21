package com.gdou.dmall;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableDubbo
@MapperScan(basePackages = "com.gdou.dmall.manage.mapper")
public class DmallManageServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DmallManageServiceApplication.class, args);
	}

}
