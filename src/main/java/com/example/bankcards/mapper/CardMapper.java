package com.example.bankcards.mapper;

import com.example.bankcards.dto.CardDto;
import com.example.bankcards.dto.CardSaveResponseDto;
import com.example.bankcards.entity.Card;
import com.example.bankcards.util.UtilCardService;
import org.springframework.stereotype.Service;

@Service
public class CardMapper {
    public static CardDto cardToDTOFromEntity(Card card) {
        CardDto cardDto = new CardDto();
        cardDto.setId(card.getId());
        cardDto.setCardStatus(card.getCardStatus());
        cardDto.setOwner(card.getOwner());
        cardDto.setBalance(card.getBalance());
        cardDto.setExpiryDate(card.getExpiryDate());
        cardDto.setNumber(UtilCardService.maskCardNumber(card.getNumber()));
        return cardDto;
    }

    public static Card cardToEntityFromCardSaveResponseDto( CardSaveResponseDto cardSaveResponseDto) {
        Card cardEntity = new Card();
        cardEntity.setId(cardSaveResponseDto.getId());
        cardEntity.setCardStatus(cardSaveResponseDto.getCardStatus());
        cardEntity.setOwner(cardSaveResponseDto.getOwner());
        cardEntity.setBalance(cardSaveResponseDto.getBalance());
        cardEntity.setExpiryDate(cardSaveResponseDto.getExpiryDate());
        cardEntity.setNumber(cardSaveResponseDto.getNumber());
        return cardEntity;
    }
    public static Card cardToEntityFromCardSaveResponseDto(Card card, CardSaveResponseDto cardSaveResponseDto) {
        card.setId(cardSaveResponseDto.getId());
        card.setCardStatus(cardSaveResponseDto.getCardStatus());
        card.setOwner(cardSaveResponseDto.getOwner());
        card.setBalance(cardSaveResponseDto.getBalance());
        card.setExpiryDate(cardSaveResponseDto.getExpiryDate());
        card.setNumber(cardSaveResponseDto.getNumber());
        return card;
    }
}
