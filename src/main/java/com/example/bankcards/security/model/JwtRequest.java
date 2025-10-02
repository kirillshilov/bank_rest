package com.example.bankcards.security.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class JwtRequest {

    private String login;
    private String password;

}
