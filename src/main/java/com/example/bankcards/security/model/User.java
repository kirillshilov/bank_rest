package com.example.bankcards.security.model;

import com.example.bankcards.entity.Card;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table (name = "regUser")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String password;
    private Set<Role> roles;

    @OneToMany( mappedBy = "user")
    @ToString.Exclude
    @JsonIgnore
    private List<Card> cardList = new ArrayList<>();
}
