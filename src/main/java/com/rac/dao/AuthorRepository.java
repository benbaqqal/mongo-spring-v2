package com.rac.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.rac.model.Author;

@RepositoryRestResource
public interface AuthorRepository extends MongoRepository<Author, String> {

}
