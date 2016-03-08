package com.rac.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.rac.model.Comment;

@RepositoryRestResource
public interface CommentRepository extends MongoRepository<Comment, String> {

}
