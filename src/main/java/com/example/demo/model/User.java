package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
public class User implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String name;
	private String login;
	private String password;
	private String description;
	private String photo_link;
	private Person person;


	public User() {

	}

	public User(String name, String login, String password, String description, String photo_link) {
		this.name = name;
		this.login = login;
		this.password = password;
		this.description = description;
		this.photo_link = photo_link;

	}

	public String getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	public String getLogin() {
		return login;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	public String getDescription() {
		return description;
	}

	public String getPhoto_link() {
		return photo_link;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}


	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", login=" + login + ", profile picture=" + photo_link + "]";
	}


}