package com.example.bankcards.controller;

import com.example.bankcards.dto.CardDto;
import com.example.bankcards.dto.TransferDto;
import com.example.bankcards.service.cards.card.CardOperationService;
import com.example.bankcards.util.UtilResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("card/operation")
@RequiredArgsConstructor
@SecurityRequirement(name = "JWT")
public class CardOperationController {
    private final CardOperationService cardOperationService;
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping("getUserCards")
    public ResponseEntity<Page<CardDto>> getUserCards( @RequestParam Integer page, @RequestParam Integer size) {
        return ResponseEntity.ok(cardOperationService.getUserCards( page, size));
    }
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping("getBalanceByCardId")
    public ResponseEntity<BigDecimal> getBalanceByCardId(@RequestParam Long cardId) {
        return ResponseEntity.ok(cardOperationService.getBalanceByCardId(cardId));
    }
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @PostMapping("transferMoney")
    public ResponseEntity<UtilResponse> transferMoney(@RequestBody TransferDto transferDto) {
        return ResponseEntity.ok(cardOperationService.transferMoney(transferDto));
    }
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @PostMapping("blockCardRequest")
    public ResponseEntity<UtilResponse> blockCardRequest(@RequestParam Long id, @RequestParam String message) {
        return ResponseEntity.ok(cardOperationService.blockCardRequest(id, message));
    }
}
