package com.example.offwork.services.impl;

import com.example.offwork.domain.dtos.PartialUserDto;
import com.example.offwork.domain.dtos.ResetPasswordDto;
import com.example.offwork.domain.dtos.UserDto;
import com.example.offwork.domain.entities.UserEnt;
import com.example.offwork.domain.exception.EntityNotFoundException;
import com.example.offwork.domain.exception.IncorrectDataException;
import com.example.offwork.domain.extras.ErrorMessages;
import com.example.offwork.domain.extras.Role;
import com.example.offwork.persistence.dao.UserRepo;
import com.example.offwork.services.api.UserService;
import com.example.offwork.services.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {


    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    @Value("${app.maxDays}")
    private int maxDays;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private Utils utils;

    @PostConstruct
    public void insertUserIfNoUsers() {
        long uc = userRepo.count();
        logger.info("number of users: {}", uc);
        if(uc == 0) {
           insertFirstAdmin();
        }
    }

    private void insertFirstAdmin() {
        UserEnt userEnt = new UserEnt("admin", "admin", "admin", "admin", "admin", Role.ROLE_ADMIN, null);
        setPassword(userEnt,"A1admin");
        userRepo.save(userEnt);
    }

    @Override
    public List<UserDto> getAll() {
        logger.info("getting all users");
        return userRepo.findAll().stream()
                .map(this::toUserDto)
                .map(this::setAppDays)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getById(Long id) {
        return userRepo.findById(id).map(this::toUserDto).map(this::setAppDays).orElse(null);
    }

    @Override
    public UserDto create(UserEnt userEnt) {
        return Optional.of(userEnt)
                .map(e -> this.setPassword(e, e.getPassword()))
                .map(this::saveUser)
                .map(this::toUserDto)
                .map(this::setAppDays)
                .orElseThrow(()-> new IncorrectDataException(ErrorMessages.INCORRECT_DATA));
    }

    @Override
    public UserDto update(PartialUserDto partialUserDto) {
        return userRepo.findById(partialUserDto.getId())
                .map(e -> this.attachFields(e, partialUserDto))
                .map(this::saveUser)
                .map(this::toUserDto)
                .map(this::setAppDays)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessages.USER_DOESNT_EXIST));
    }

    @Override
    public void delete(Long id) {
        userRepo.findById(id).ifPresent(e -> userRepo.delete(e));
    }

    @Override
    public PartialUserDto resetPassword(ResetPasswordDto resetPasswordDto) {
        return userRepo.findById(resetPasswordDto.getUserId())
                .map(e -> this.setPassword(e, resetPasswordDto.getNewPassword()))
                .map(userRepo::save)
                .map(this::toPartialUserDto)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessages.USER_DOESNT_EXIST));
    }

    private UserDto toUserDto(UserEnt userEnt) {
        return modelMapper.map(userEnt, UserDto.class);
    }

    private PartialUserDto toPartialUserDto(UserEnt userEnt) {
        return modelMapper.map(userEnt, PartialUserDto.class);
    }

    private UserEnt userDtoToEnt(UserDto userDto) {
        return modelMapper.map(userDto, UserEnt.class);
    }

    private UserEnt setPassword(UserEnt userEnt, String password) {
        userEnt.setPassword(passwordEncoder.encode(password));
        return userEnt;
    }

    private UserEnt attachFields(UserEnt userEnt, PartialUserDto partialUserDto) {
        userEnt.setName(partialUserDto.getName());
        userEnt.setEmail(partialUserDto.getEmail());
        userEnt.setSurname(partialUserDto.getSurname());
        userEnt.setRole(partialUserDto.getRole());
        userEnt.setUsername(partialUserDto.getUsername());
        return userEnt;
    }

    private UserDto setAppDays(UserDto userDto) {
        userDto.setLeavesLeft(maxDays - utils.calculateAppDaysForUser(userDto.getId()));
        return userDto;
    }

    private UserEnt saveUser(UserEnt userEnt) {
        try {
            return userRepo.save(userEnt);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IncorrectDataException(e.getCause());
        }
    }
}
