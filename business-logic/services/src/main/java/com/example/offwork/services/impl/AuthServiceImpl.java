package com.example.offwork.services.impl;
import com.example.offwork.domain.dtos.LoginCredentials;
import com.example.offwork.domain.dtos.PartialUserDto;
import com.example.offwork.domain.dtos.UserPrincipal;
import com.example.offwork.domain.exception.CredentialsValidationException;
import com.example.offwork.domain.exception.EntityNotFoundException;
import com.example.offwork.domain.extras.ErrorMessages;
import com.example.offwork.persistence.dao.UserRepo;
import com.example.offwork.services.api.AuthService;
import com.example.offwork.services.security.JwtTokenProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImpl implements AuthService {

    private static Logger logger = LogManager.getLogger(AuthServiceImpl.class);

    @Autowired
    ModelMapper modelMapper;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtTokenProvider tokenProvider;
    @Autowired
    private UserRepo userRepo;

    @Override
    public PartialUserDto authenticate(LoginCredentials loginCredentials) {
        logger.info(loginCredentials);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginCredentials.getEmailOrUsername(),
                        loginCredentials.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);

        return userRepo.getByUSernameOrEmail(loginCredentials.getEmailOrUsername())
                .map(e -> modelMapper.map(e, PartialUserDto.class))
                .map(e -> {
                    e.setToken(jwt);
                    return e;
                })
                .orElseThrow(() -> new CredentialsValidationException(ErrorMessages.USERNAME_OR_EMAIL_INCORRECT));
    }

    @Override
    public void logout() {
        logger.info("Logged off");
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepo.getByUSernameOrEmail(s)
                .map(UserPrincipal::create)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessages.USER_DOESNT_EXIST));
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        return userRepo.findById(id)
                .map(UserPrincipal::create)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessages.USER_DOESNT_EXIST));
    }
}
