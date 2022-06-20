package com.example.demo.model;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.data.neo4j.core.schema.Relationship.Direction.INCOMING;


@Node
public class Person {

    @Id
    private final String id;

    private String login;

    @Relationship("FRIEND_WITH") // direction can be OUTGOING (default) or INCOMING
    private Friend friend;


    public Person(String id, String login) {
        this.id = id;
        this.login = login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }
    
   
}
