package com.example.bankcards.service.cards.cardImpl;

import com.example.bankcards.dto.CardDto;
import com.example.bankcards.dto.TransferDto;
import com.example.bankcards.entity.*;
import com.example.bankcards.exception.CardException;
import com.example.bankcards.exception.TransactionException;
import com.example.bankcards.repository.CardBlockRepository;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.TransferRepository;
import com.example.bankcards.security.model.JwtAuthentication;
import com.example.bankcards.security.repository.UserRepository;
import com.example.bankcards.service.cards.card.CardOperationService;
import com.example.bankcards.util.UtilResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CardOperationServiceImpl implements CardOperationService {
    private final CardRepository cardRepository;
    private final TransferRepository transferRepository;
    private final CardBlockRepository cardBlockRepository;

    @Override
    public Page<CardDto> getUserCards(Integer page, Integer size) {
        Long userId = 0L;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication instanceof JwtAuthentication) {
            userId  = ((JwtAuthentication) authentication).getUserId();
        }
        Pageable pageable = PageRequest.of(page, size);
        return cardRepository.findCardByUser_Id(userId, pageable).map(card -> new CardDto(card.getId(),
                card.getNumber(),
                card.getOwner(),
                card.getExpiryDate(),
                card.getCardStatus(),
                card.getBalance()
        ));
    }

    @Override
    public BigDecimal getBalanceByCardId(Long cardId) {
        Optional<Card> card = cardRepository.findById(cardId);
        if (card.isPresent()) {
            return card.get().getBalance();
        } else {
            throw new CardException("Карта с таким идентификатором не найдена");
        }
    }

    @Override
    @Transactional
    public UtilResponse transferMoney(TransferDto transferDto) {
        Card fromCard = cardRepository.findByIdForUpdate(transferDto.getFromCardId())
                .orElseThrow(() -> new CardException("Карта с идентификатором: " + transferDto.getFromCardId() + " не найдена"));
        Card toCard = cardRepository.findByIdForUpdate(transferDto.getToCardId())
                .orElseThrow(() -> new CardException("Карта с идентификатором: " + transferDto.getToCardId() + " не найдена"));
        if (fromCard.getBalance().compareTo(transferDto.getAmount()) < 0) {
            throw new CardException("Баланс недостаточен");
        }
        if (fromCard.getCardStatus().equals(CardStatus.BLOCKED) || fromCard.getCardStatus().equals(CardStatus.EXPIRED)) {
            throw new CardException("Карта с идентификатором: " + transferDto.getFromCardId() + " недействительна");
        }
        if (toCard.getCardStatus().equals(CardStatus.BLOCKED) || toCard.getCardStatus().equals(CardStatus.EXPIRED)) {
            throw new CardException("Карта с идентификатором: " + transferDto.getToCardId() + " недействительна");
        }
        fromCard.setBalance(fromCard.getBalance().subtract(transferDto.getAmount()));
        toCard.setBalance(toCard.getBalance().add(transferDto.getAmount()));
        try {
            cardRepository.save(fromCard);
            cardRepository.save(toCard);
            transferRepository.save(Transfer.builder()
                    .id(null).
                    cardNumberFrom(fromCard.getId()).
                    cardNumberTo(toCard.getId()).
                    transferDate(LocalDateTime.now()).
                    status(TransferStatus.DONE).
                    amount(transferDto.getAmount()).
                    build());
        } catch (Exception e) {
            log.error("Ошибка транзакции {}  {}", transferDto, e.getMessage());
            transferRepository.save(Transfer.builder()
                    .id(null).
                    cardNumberFrom(fromCard.getId()).
                    cardNumberTo(toCard.getId()).
                    transferDate(LocalDateTime.now()).
                    status(TransferStatus.ERROR).
                    amount(transferDto.getAmount()).
                    build());
            throw new TransactionException("Не удалось выполнить перевод");
        }
        return UtilResponse.builder().message("Перевод выполнен успешно").build();
    }

    @Override
    public UtilResponse blockCardRequest(Long id, String message) {
        Optional<Card> card = cardRepository.findById(id);
        if (card.isPresent()) {
            cardBlockRepository.save(CardBlockRequest.builder().cardId(card.get().getId()).message(message).build());
            return UtilResponse.builder().message("Отправлен запрос на блокировку карты").build();
        } else {
            throw new CardException("Карта с идентификатором: " + id + " не найдена");
        }
    }
}
