package com.example.bankcards.controller;

import com.example.bankcards.dto.CardDto;
import com.example.bankcards.dto.CardSaveResponseDto;
import com.example.bankcards.dto.SavedCardId;
import com.example.bankcards.service.cards.card.CardCRUDService;
import com.example.bankcards.util.UtilResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("card/crud")
@RequiredArgsConstructor
@Validated
@SecurityRequirement(name = "JWT")
public class CardCRUDController {
    private final CardCRUDService cardCRUDService;
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/createCard")
    public ResponseEntity<SavedCardId> saveCard(@RequestBody @Valid CardSaveResponseDto cardSaveResponseDto) {
        return ResponseEntity.ok(cardCRUDService.saveCard(cardSaveResponseDto));
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getAllCards")
    public ResponseEntity<Page<CardDto>> getAllCards(Integer page,  Integer size) {
        return ResponseEntity.ok(cardCRUDService.getAllCards(page, size));
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getCardById")
    public ResponseEntity<CardDto> getCardById(@RequestParam @Positive  Long id) {
        return ResponseEntity.ok(cardCRUDService.getCardById(id));
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/updateCard")
    public ResponseEntity<UtilResponse> updateCard(@RequestBody @Valid CardSaveResponseDto cardSaveResponseDto) {
        return ResponseEntity.ok(cardCRUDService.updateCard(cardSaveResponseDto));
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/deleteCard")
    public ResponseEntity<UtilResponse> deleteCard(@RequestParam @Positive Long id) {
        return ResponseEntity.ok(cardCRUDService.deleteCardById(id));
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getCardsByUserLogin")
    public ResponseEntity<List<CardDto>> getCardsByUserLogin(@RequestParam String login) {
        return ResponseEntity.ok(cardCRUDService.getCardsByUserLogin(login));
    }
}
