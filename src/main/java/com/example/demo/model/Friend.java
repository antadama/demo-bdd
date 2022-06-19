package com.example.demo.model;


import org.springframework.data.neo4j.core.schema.*;

@RelationshipProperties
public class Friend {

    @RelationshipId
    @GeneratedValue
    private Long id;

    @TargetNode
    private Person person;
}