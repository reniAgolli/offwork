package com.example.offwork.services.utils;

import com.example.offwork.domain.dtos.UserPrincipal;
import org.springframework.stereotype.Service;

@Service
public class PrincipalUtils {

    public static boolean hasRole(UserPrincipal principal, String role) {
        return principal.getAuthorities().stream()
                .anyMatch(e -> e.toString().equals(role));
    }
}
