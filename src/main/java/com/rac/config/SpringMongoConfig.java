package com.rac.config;

import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.MongoClient;
import com.rac.dao.listener.CommentCascadeDeleteMongoEventListener;

@EnableMongoRepositories(basePackages = { "com.rac.dao" })
public class SpringMongoConfig {

    public @Bean MongoDbFactory mongoDbFactory() throws Exception {
	return new SimpleMongoDbFactory(new MongoClient("localhost", 27017), "test");
    }

    // public @Bean MongoTemplate mongoTemplate() throws Exception {
    // MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());
    // return mongoTemplate;
    // }

    public @Bean CommentCascadeDeleteMongoEventListener commentCascadingMongoEventListener() {
	return new CommentCascadeDeleteMongoEventListener();
    }

}
