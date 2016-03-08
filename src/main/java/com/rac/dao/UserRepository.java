package com.rac.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.rac.model.User;

@RepositoryRestResource
public interface UserRepository extends MongoRepository<User, String> {

    public List<User> findByUsername(String username);

}