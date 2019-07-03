package com.example.offwork.services.api;
import com.example.offwork.domain.dtos.LoginCredentials;
import com.example.offwork.domain.dtos.PartialUserDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService extends UserDetailsService {

    PartialUserDto authenticate(LoginCredentials loginCredentials);

    UserDetails loadUserByUsername(String s);

    UserDetails loadUserById(Long id);

    void logout();
}
