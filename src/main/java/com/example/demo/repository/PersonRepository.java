package com.example.demo.repository;

import java.util.List;
import java.util.Set;

import com.example.demo.model.Person;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "people", path = "people")
public interface PersonRepository extends Neo4jRepository<Person, String> {

    // returns the node with id equal to idOfPerson parameter
    @Query("MATCH (n) WHERE id(n)=$0 RETURN n")
    Person getPersonFromId(String idOfPerson);
    
    // Checks if person exists
    @Query("MATCH (n) WHERE EXISTS(n.User) RETURN n")
    boolean personExists(String login);
    
    // adds a friendship 
  
    @Query("MATCH(user1:User {name: $login1}), (user2:User {name: $login2})"
    		+ "  CREATE (user1)-[r:Friendship]->(user2)"
    		+ "  RETURN type(r)")
    void addFriendship(String login1, String login2);
    
    @Query("MATCH (user1:User {name: $login1})-[r:RELEASED]-(user2:User {name: $login2}) DELETE r")
    void removeFriendship(String login1, String login2);
    
    // returns the nodes which have a login according to the loginP parameter
    @Query("MATCH (person:Person {login=$0}) RETURN person")
    Person getPersonFromLogin(String loginP);

    // returns Persons that have a FRIEND_WITH relationship to the person node with the login equal to login parameter.
    @Query("MATCH (p:Person)-[:FRIEND_WITH]->(partner:Person) " +
            "OR MATCH (p:Person)<-[:FRIEND_WITH]-(partner:Person) " +
            "WHERE p.login = $0 " +
            "RETURN partner")
    List<Person> getUsersWhoAreFriendWith(String login);
    
    @Query("MATCH (user1:User {name: $login1}), (user2:User {name: $login2}) RETURN EXISTS( (user1)-[:Friendship]-(user2) )")
    boolean areFriends(String login1, String login2);
    

}
