package com.example.bankcards.service.cards.cardImpl;

import com.example.bankcards.dto.CardDto;
import com.example.bankcards.dto.CardSaveResponseDto;
import com.example.bankcards.dto.SavedCardId;
import com.example.bankcards.entity.Card;
import com.example.bankcards.exception.CardException;
import com.example.bankcards.exception.UtilException;
import com.example.bankcards.mapper.CardMapper;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.security.model.User;
import com.example.bankcards.security.repository.UserRepository;
import com.example.bankcards.service.cards.card.CardCRUDService;
import com.example.bankcards.util.UtilResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CRUDCardServiceImpl implements CardCRUDService {
    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    @Override
    public SavedCardId saveCard(CardSaveResponseDto cardSaveResponseDto) {
        Card card = CardMapper.cardToEntityFromCardSaveResponseDto(cardSaveResponseDto);
        Optional<User> user = userRepository.findById(cardSaveResponseDto.getCardUserId());
        if (!user.isPresent()) {
            throw new CardException("User not found");
        }

        card.setUser(user.get());
        Card cardSaved = cardRepository.save(card);
        return SavedCardId.builder().id(cardSaved.getId()).build();
    }

    @Override
    public CardDto getCardById(Long id) {
        Optional <Card> card = cardRepository.findById(id);
        if (card.isPresent()) {
            return CardMapper.cardToDTOFromEntity(card.get());
        }
        return null;
    }

    @Override
    public Page<CardDto> getAllCards(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return cardRepository.findAll(pageable).map(card -> new CardDto(card.getId(),
                card.getNumber(),
                card.getOwner(),
                card.getExpiryDate(),
                card.getCardStatus(),
                card.getBalance()
        ));
    }

    @Override
    public UtilResponse updateCard(CardSaveResponseDto cardSaveResponseDto) {
        Optional<Card> card = cardRepository.findById(cardSaveResponseDto.getId());
        if (card.isPresent()) {
            CardMapper.cardToEntityFromCardSaveResponseDto(card.get(), cardSaveResponseDto);
        } else {
            throw new CardException("Карта с таким идентификатором не найдена");
        }
        return UtilResponse.builder().message("Обновление прошло успешно").build();
    }

    @Override
    public UtilResponse deleteCardById(Long id) {
        cardRepository.deleteById(id);
        return UtilResponse.builder().message("Удаление прошло успешно").build();
    }

    @Override
    public List<CardDto> getCardsByUserLogin(String login) {
        Optional<User> user =  userRepository.findByLogin(login);
        if (!user.isPresent()){
            throw new UtilException("User not found");
        }
        else {
            return cardRepository.findAllByUser_Id(user.get().getId()).stream().map(card -> new CardDto(card.getId(),
                    card.getNumber(),
                    card.getOwner(),
                    card.getExpiryDate(),
                    card.getCardStatus(),
                    card.getBalance()
            )).toList();
        }
    }
}
