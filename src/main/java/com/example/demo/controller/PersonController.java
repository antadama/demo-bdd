package com.example.demo.controller;

import com.example.demo.model.Person;
import com.example.demo.model.User;
import com.example.demo.repository.PersonRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/user")
class PersonController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PersonRepository personRepository;

    @GetMapping("/{user_id}/friend")
    ResponseEntity<List<Person>> getFriends(@PathVariable("user_id") String user_id) {
        try {
            List<Person> results = new ArrayList<Person>();
            Optional<User> _user = userRepository.findById(user_id);
            personRepository.getUsersWhoAreFriendWith(_user.get().getLogin()).forEach(results::add);

            if (results.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
  
    @GetMapping("/test_friendship")
    boolean areFriends(@RequestBody Map<String, String> payload) {
        String login1 = payload.get("login1");
        String login2 = payload.get("login2");
        return personRepository.personExists(login1) && personRepository.personExists(login2) && personRepository.areFriends(login1, login2);
    }
    
    @PostMapping("{user_id}/friend")
    ResponseEntity<HttpStatus> addFriend(@PathVariable("user_id") String user_id, @RequestBody Map<String, String> payload) {
        try {
            Optional<User> _user = userRepository.findById(user_id);
            String login1 = payload.get("login");
            String login2 = _user.get().getLogin();

            if (personRepository.personExists(login1) && personRepository.personExists(login2) && !personRepository.areFriends(login1, login2)) {
                personRepository.addFriendship(login1, login2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.CREATED);

    }
    
    @DeleteMapping("/{user_id}/friend")
    ResponseEntity<HttpStatus> removeFriend(@PathVariable("user_id") String user_id, @RequestBody Map<String, String> payload) {
        Optional<User> _user = userRepository.findById(user_id);
        String user_login = _user.get().getLogin();

        String friend_login = payload.get("login");

        if (personRepository.personExists(user_login) && personRepository.personExists(friend_login) && personRepository.areFriends(user_login, friend_login)) {
        	personRepository.removeFriendship(user_login, friend_login);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}