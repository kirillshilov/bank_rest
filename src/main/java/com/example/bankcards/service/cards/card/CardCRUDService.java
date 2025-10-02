package com.example.bankcards.service.cards.card;

import com.example.bankcards.dto.CardDto;
import com.example.bankcards.dto.CardSaveResponseDto;
import com.example.bankcards.dto.SavedCardId;
import com.example.bankcards.util.UtilResponse;
import org.springframework.data.domain.Page;

import java.util.List;


public interface CardCRUDService {
    SavedCardId saveCard(CardSaveResponseDto cardSaveResponseDto);
    CardDto getCardById(Long id);
    Page<CardDto> getAllCards(Integer page, Integer size);
    UtilResponse updateCard(CardSaveResponseDto cardSaveResponseDto);
    UtilResponse deleteCardById(Long id);

    List<CardDto> getCardsByUserLogin(String login);
}
