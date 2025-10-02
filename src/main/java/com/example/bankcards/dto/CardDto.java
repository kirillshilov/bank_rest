package com.example.bankcards.dto;

import com.example.bankcards.entity.CardStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;
import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardDto {
    private Long id;
    private String number;
    private String owner;
    private LocalDate expiryDate;
    @Enumerated(EnumType.STRING)
    private CardStatus cardStatus;
    private BigDecimal balance;
}
