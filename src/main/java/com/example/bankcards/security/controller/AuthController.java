package com.example.bankcards.security.controller;

import com.example.bankcards.security.AuthService;
import com.example.bankcards.security.UserService;
import com.example.bankcards.security.model.JwtRequest;
import com.example.bankcards.security.model.JwtResponse;
import com.example.bankcards.security.model.RefreshJwtRequest;
import com.example.bankcards.security.model.User;
import com.example.bankcards.util.UtilResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
@SecurityRequirement(name = "JWT")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest authRequest)  {
        final JwtResponse token = authService.login(authRequest);
        return ResponseEntity.ok(token);
    }

    @PostMapping("token")
    public ResponseEntity<JwtResponse> getNewAccessToken(@RequestBody RefreshJwtRequest request)  {
        final JwtResponse token = authService.getAccessToken(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }

    @PostMapping("refresh")
    public ResponseEntity<JwtResponse> getNewRefreshToken(@RequestBody RefreshJwtRequest request)  {
        final JwtResponse token = authService.refresh(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("registerUser")
    public ResponseEntity<UtilResponse> registerUser(@RequestBody User user) {
         userService.createUser(user);
        return ResponseEntity.ok(UtilResponse.builder().message("Пользователь создан").build());
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("updateUser")
    public ResponseEntity<UtilResponse> updateUser(@RequestBody User user) {
        userService.updateUser(user);
        return ResponseEntity.ok(UtilResponse.builder().message("Пользователь обновлен").build());
    }

}
