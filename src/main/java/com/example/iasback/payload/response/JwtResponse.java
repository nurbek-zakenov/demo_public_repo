package com.example.iasback.payload.response;

import com.example.iasback.models.Role;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class JwtResponse implements Serializable {
	private String token;
	private static String type = "Bearer";
	private Integer id;
	private String username;
	private String email;
	private Role role;
	private String name;
	private String surname;
	private String patronymic;

//	public JwtResponse(String accessToken, Integer id, String username, String email, Role role) {
//		this.token = accessToken;
//		this.id = id;
//		this.username = username;
//		this.email = email;
//		this.role = role;
//	}

	public JwtResponse(String token, Integer id, String username, String email, Role role, String name, String surname, String patronymic) {
		this.token = token;
		this.type = type;
		this.id = id;
		this.username = username;
		this.email = email;
		this.role = role;
		this.name = name;
		this.surname = surname;
		this.patronymic = patronymic;
	}

	public JwtResponse( Integer id, String username, String email, Role role, String name, String surname, String patronymic) {
		this.type = type;
		this.id = id;
		this.username = username;
		this.email = email;
		this.role = role;
		this.name = name;
		this.surname = surname;
		this.patronymic = patronymic;
	}

	public JwtResponse(Integer id, String username, String email, Role role) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.role = role;
	}


}
