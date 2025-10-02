package com.example.bankcards.entity;

import com.example.bankcards.security.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Card {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String number;
    private String owner;
    private LocalDate expiryDate;
    @Column(nullable = false, precision = 11, scale = 2)
    private BigDecimal balance;
    @Enumerated(EnumType.STRING)
    private CardStatus cardStatus;
    @ManyToOne(cascade = CascadeType.REFRESH)
    @ToString.Exclude
    @JoinColumn(name = "user_id")
    private User user;
}
