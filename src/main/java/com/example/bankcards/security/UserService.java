package com.example.bankcards.security;

import com.example.bankcards.exception.UtilException;
import com.example.bankcards.security.model.User;
import com.example.bankcards.security.repository.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;
    private final PasswordEncoderUtil passwordEncoderUtil;

    public User createUser(User user) {
        String encryptedPassword = passwordEncoderUtil.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        return userRepository.save(user);
    }

    public Optional<User> getByLogin(@NonNull String login) {
        return userRepository.findByLogin(login);
    }

    public User updateUser(User user) {
        if (user.getId() == null) {
            throw new UtilException("Отсутствует айди пользователя");
        }
        Optional<User> optionalUser = userRepository.findById(user.getId());
        if (!optionalUser.isPresent()) {
            throw new UtilException("Пользователь не найден");
        }
        String encryptedPassword = passwordEncoderUtil.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        return userRepository.save(user);
    }
}
