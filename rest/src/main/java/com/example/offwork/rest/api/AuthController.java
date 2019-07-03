package com.example.offwork.rest.api;

import com.example.offwork.domain.dtos.LoginCredentials;
import com.example.offwork.domain.dtos.PartialUserDto;
import com.example.offwork.services.api.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public PartialUserDto authenticate(@RequestBody LoginCredentials loginCredentials) {
        return authService.authenticate(loginCredentials);
    }

    @GetMapping("/logout")
    public ResponseEntity logout() {
        authService.logout();
        return ResponseEntity.ok().build();
    }
}
