package com.rubypaper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.rubypaper", "com.ruby"}) //현재는 rubypaper여서 다른 곳에서도 추가하고자 할 때
public class Chapter01Application {

	public static void main(String[] args) {
		SpringApplication.run(Chapter01Application.class, args);
	}

}
