package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.model.Person;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserRepository userRepository;
    PersonRepository personRepository;

    /**
     * @return List of users
     */
    @GetMapping("/user")
    public ResponseEntity<Map<String, Object>> getAllUsersPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "30") int size) {

        try {
            List<User> users;
            Pageable paging = PageRequest.of(page, size);

            Page<User> pageUsers;
            pageUsers = userRepository.findAll(paging);

            users = pageUsers.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("Users", users);
            response.put("currentPage", pageUsers.getNumber());
            response.put("totalItems", pageUsers.getTotalElements());
            response.put("totalPages", pageUsers.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/{login}")
    public ResponseEntity<User> findByLogin(@PathVariable("login") String login ) {

        Optional<User> userData = userRepository.findByLogin(login);

        if (userData.isPresent()) {
            return new ResponseEntity<>(userData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }


    /**
     * @param user
     * @return user, HttpStatus
     */
    @PostMapping("/user")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            User _user = userRepository.save( new User(user.getName(), user.getLogin(), user.getPassword(), user.getDescription(), user.getPhoto_link() ) );
            Person p = personRepository.save( new Person(_user.getId(), _user.getLogin()));

            return new ResponseEntity<>(_user, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/user")
    public ResponseEntity<HttpStatus> deleteAllUsers() {
        try {
            userRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    /**
     * @param id
     * @return Map<String,Boolean>
     */
    @DeleteMapping("/user/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") String id) {
        try {
            userRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
