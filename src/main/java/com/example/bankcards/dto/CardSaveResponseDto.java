package com.example.bankcards.dto;

import com.example.bankcards.entity.CardStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import lombok.NoArgsConstructor;


import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class CardSaveResponseDto {
    private Long id;
    @NotNull
    @Pattern(regexp = "^[0-9]{16}$", message = "Номер карты должен содержать только 16 цифр")
    private String number;
    private String owner;
    @NotNull
    private LocalDate expiryDate;
    private CardStatus cardStatus;
    @NotNull
    @DecimalMin(value = "0.00", message = "Баланс не может быть отрицательным")
    @Digits(integer = 10, fraction = 2, message = "Баланс должен иметь максимум 10 цифр до запятой и 2 после")
    private BigDecimal balance;
    private Long cardUserId;
}
