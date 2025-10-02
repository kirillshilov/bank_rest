package com.example.bankcards.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferDto {
    @NotNull
    @Positive (message = "Неверный идентификатор карты")
    private Long fromCardId;
    @NotNull
    @Positive (message = "Неверный идентификатор карты")
    private Long toCardId;
    @NotNull
    @DecimalMin(value = "0.00", message = "Сумма не может быть отрицательной")
    @Digits(integer = 9, fraction = 2, message = "Сумма должна иметь максимум 11 цифр до запятой и 2 после")
    private BigDecimal amount;

}
