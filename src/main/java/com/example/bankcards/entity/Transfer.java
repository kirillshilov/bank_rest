package com.example.bankcards.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long cardNumberFrom;
    private Long cardNumberTo;
    private LocalDateTime transferDate;
    @Enumerated(EnumType.STRING)
    private TransferStatus status;
    @Column(nullable = false, precision = 11, scale = 2)
    private BigDecimal amount;
}



