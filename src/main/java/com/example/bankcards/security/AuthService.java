package com.example.bankcards.security;

import com.example.bankcards.security.exception.AuthException;
import com.example.bankcards.security.model.JwtRequest;
import com.example.bankcards.security.model.JwtResponse;
import com.example.bankcards.security.model.TokenStoreEntity;
import com.example.bankcards.security.model.User;
import com.example.bankcards.security.repository.TokenRepository;
import io.jsonwebtoken.Claims;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final PasswordEncoderUtil passwordEncoderUtil;
    private final TokenRepository tokenRepository;

    public JwtResponse login(@NonNull JwtRequest authRequest) throws AuthException {
        final User user = userService.getByLogin(authRequest.getLogin())
                .orElseThrow(() -> new AuthException("Пользователь не найден"));
        if (passwordEncoderUtil.matches(authRequest.getPassword(), user.getPassword())) {
            final String accessToken = jwtProvider.generateAccessToken(user);
            String refreshToken = jwtProvider.generateRefreshToken(user);
            Optional<TokenStoreEntity> token = tokenRepository.findByLogin(authRequest.getLogin());
            String refreshTokenEncoded = passwordEncoderUtil.encode(refreshToken);
            if (token.isPresent()) {
                token.get().setToken(refreshTokenEncoded);
                tokenRepository.save(token.get());
            }
            else {
                tokenRepository.save(TokenStoreEntity.builder().login(authRequest.getLogin()).token(refreshTokenEncoded).build());
            }
            return new JwtResponse(accessToken, refreshToken);
        } else {
            throw new AuthException("Неправильный пароль");
        }
    }

    public JwtResponse getAccessToken(@NonNull String refreshToken) throws AuthException {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            Optional<TokenStoreEntity> token = tokenRepository.findByLogin(login);
            if (token.isPresent() && passwordEncoderUtil.matches(refreshToken, token.get().getToken())) {
                final User user = userService.getByLogin(login)
                        .orElseThrow(() -> new AuthException("Пользователь не найден"));
                final String accessToken = jwtProvider.generateAccessToken(user);
                return new JwtResponse(accessToken, null);
            }
        }
        return new JwtResponse(null, null);
    }

    public JwtResponse refresh(@NonNull String refreshToken) throws AuthException {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            Optional<TokenStoreEntity> token = tokenRepository.findByLogin(login);
            if (token.isPresent() && passwordEncoderUtil.matches(refreshToken, token.get().getToken())) {
                final User user = userService.getByLogin(login)
                        .orElseThrow(() -> new AuthException("Пользователь не найден"));
                final String accessToken = jwtProvider.generateAccessToken(user);
                final String newRefreshToken = jwtProvider.generateRefreshToken(user);
                token.get().setToken(passwordEncoderUtil.encode(newRefreshToken));
                tokenRepository.save(token.get());
                return new JwtResponse(accessToken, newRefreshToken);
            }
        }
        throw new AuthException("Невалидный JWT токен");
    }

}
