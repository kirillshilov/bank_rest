package com.example.bankcards.config;


import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.CardStatus;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.security.model.Role;
import com.example.bankcards.security.model.User;
import com.example.bankcards.security.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserService userService;
    private final CardRepository cardRepository;

    public DataLoader(CardRepository cardRepository, UserService userService) {
        this.userService = userService;
        this.cardRepository = cardRepository;
    }

    @Override
    public void run(String... args) {
        User userCard = new User();
        if (userService.getByLogin("user").isEmpty()) {
            User user = new User();
            user.setLogin("user");
            user.setPassword("userpassword");
            user.setRoles(Collections.singleton(Role.USER));
            userCard = userService.createUser(user);
            System.out.println("Пользователь создан: " + user.getLogin());
        }
        else {
            userCard = userService.getByLogin("user").get();
        }
        if (userService.getByLogin("admin").isEmpty()) {
            User admin = new User();
            admin.setLogin("admin");
            admin.setPassword("adminpassword");
            admin.setRoles(Collections.singleton(Role.ADMIN));
            userService.createUser(admin);
            System.out.println("Администратор создан: " + admin.getLogin());
        }
        if (cardRepository.findAllByUser_Id(userCard.getId()).isEmpty()) {
            LocalDate localDate = LocalDate.now();
            localDate = localDate.plusYears(2);
            Card card = new Card();
            card.setCardStatus(CardStatus.ACTIVE);
            card.setUser(userCard);
            card.setBalance(BigDecimal.valueOf(100.00d));
            card.setNumber("1111111111111111");
            card.setOwner("testOwner");
            card.setExpiryDate(localDate);
            cardRepository.save(card);
            Card cardTwo = new Card();
            cardTwo.setCardStatus(CardStatus.ACTIVE);
            cardTwo.setUser(userCard);
            cardTwo.setBalance(BigDecimal.valueOf(100.00d));
            cardTwo.setOwner("testOwner");
            cardTwo.setNumber("2222222222222222");
            cardTwo.setExpiryDate(localDate);
            cardRepository.save(cardTwo);
            Card cardThree = new Card();
            cardThree.setCardStatus(CardStatus.BLOCKED);
            cardThree.setUser(userCard);
            cardThree.setBalance(BigDecimal.valueOf(100.00d));
            cardThree.setNumber("3333333333333333");
            cardThree.setOwner("testOwner");
            cardThree.setExpiryDate(localDate);
            cardRepository.save(cardThree);
        }
    }
}