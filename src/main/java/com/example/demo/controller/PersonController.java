package com.example.demo.controller;

import com.example.demo.model.Person;
import com.example.demo.repository.PersonRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/friends")
class PersonController {

    PersonRepository personRepository;

    @GetMapping("/{id}")
    List<Person> getFriendsFromLogin(@PathVariable("login") String login) {
        List<Person> results = personRepository.getUsersWhoAreFriendWith(login);
        return results;
    }
    
  
    @GetMapping("/areFriends")
    boolean areFriends(@RequestBody Map<String, String> payload) {
        String login1 = payload.get("login1");
        String login2 = payload.get("login2");
        return personRepository.personExists(login1) && personRepository.personExists(login2) && personRepository.areFriends(login1, login2);
    }
    
    @GetMapping("/addFriend")
    boolean addFriend(@RequestBody Map<String, String> payload) {
        String login1 = payload.get("login1");
        String login2 = payload.get("login2");
        if (personRepository.personExists(login1) && personRepository.personExists(login2) && !personRepository.areFriends(login1, login2)) {
        	personRepository.addFriendship(login1, login2);      
        }
        return false;
    }
    
    @GetMapping("/removeFriend")
    boolean removeFriend(@RequestBody Map<String, String> payload) {
        String login1 = payload.get("login1");
        String login2 = payload.get("login2");
        if (personRepository.personExists(login1) && personRepository.personExists(login2) && personRepository.areFriends(login1, login2)) {
        	personRepository.removeFriendship(login1, login2);
        }
        return false;
    }
}