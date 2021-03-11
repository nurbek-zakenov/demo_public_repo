package com.example.iasback.models;

import lombok.Data;


@Data
public class User{
	private Integer id;
	private String name;
	private String surname;
	private String patronymic;
	private String username;
	private String email;
	private String password;
	private Integer role_id;




	private Role role;



	public User() {
	}

	public User(String username, String name, String surname, String patronymic, String email, String password, Integer role_id) {
		this.username = username;
		this.name=name;
		this.surname=surname;
		this.patronymic=patronymic;
		this.email = email;
		this.password = password;
		this.role_id = role_id;
	}

}
