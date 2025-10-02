package com.example.bankcards.service.cards.card;

import com.example.bankcards.dto.CardDto;
import com.example.bankcards.dto.TransferDto;
import com.example.bankcards.util.UtilResponse;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;


public interface CardOperationService {

    Page<CardDto> getUserCards( Integer page, Integer size);

    BigDecimal getBalanceByCardId(Long cardId);
    UtilResponse transferMoney (TransferDto transferDto);
    UtilResponse blockCardRequest(Long id,String message);
}
