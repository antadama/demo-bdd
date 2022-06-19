package com.example.demo.controller;
;
import com.example.demo.model.Person;
import com.example.demo.model.User;
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


}