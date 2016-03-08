package com.rac.config;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

//@EnableAutoConfiguration
//@ComponentScan(basePackages = { "com.rac" })
//@Configuration
@SpringBootApplication(scanBasePackages = { "com.rac" })
@Import({ WebSecurityConfig.class, SpringMongoConfig.class })
public class AppConfig {

}
