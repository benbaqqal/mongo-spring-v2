package com.rac;

import org.springframework.boot.SpringApplication;

import com.rac.config.AppConfig;

public class Start {

    public static void main(String[] args) {

	// For XML
	// ApplicationContext ctx = new
	// GenericXmlApplicationContext("SpringConfig.xml");

	// For Annotation
	// ApplicationContext ctx = new AnnotationConfigApplicationContext(
	// SpringMongoConfig.class);

	// BeanFactory ctx =
	SpringApplication.run(AppConfig.class, args);

    }

}
