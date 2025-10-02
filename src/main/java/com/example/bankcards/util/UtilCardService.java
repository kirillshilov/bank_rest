package com.example.bankcards.util;

import org.springframework.stereotype.Service;

@Service
public class UtilCardService {
public static String maskCardNumber(String cardNumber){
    return cardNumber.replaceAll("\\d(?=\\d{4})", "*");
}
}
