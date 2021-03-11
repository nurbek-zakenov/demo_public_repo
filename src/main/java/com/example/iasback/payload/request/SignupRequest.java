package com.example.iasback.payload.request;

import lombok.Data;


@Data
public class SignupRequest {

    private String username;
    private String name;
    private String surname;
    private String patronymic;
    private String email;
    private String password;
    private Integer role_id;
}
