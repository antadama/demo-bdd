package com.example.demo.repository;


import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

import com.example.demo.model.User;

public interface UserRepository extends MongoRepository<User, String>{

    Optional<User> findByLogin(String login);


}



