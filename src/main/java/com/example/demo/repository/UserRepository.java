package com.example.demo.repository;


import com.example.demo.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

import com.example.demo.model.User;
import org.springframework.data.mongodb.repository.Query;

public interface UserRepository extends MongoRepository<User, String>{

    List<User> findByLoginContaining(String login);


}



