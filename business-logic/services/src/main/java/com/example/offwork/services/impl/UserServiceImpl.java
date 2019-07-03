package com.example.offwork.services.impl;

import com.example.offwork.domain.dtos.PartialUserDto;
import com.example.offwork.domain.dtos.ResetPasswordDto;
import com.example.offwork.domain.dtos.UserDto;
import com.example.offwork.domain.entities.UserEnt;
import com.example.offwork.domain.exception.EntityNotFoundException;
import com.example.offwork.domain.exception.IncorrectDataException;
import com.example.offwork.domain.extras.ErrorMessages;
import com.example.offwork.persistence.dao.UserRepo;
import com.example.offwork.services.api.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    ModelMapper modelMapper;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepo userRepo;

    @Override
    public List<UserDto> getAll() {
        return userRepo.findAll().stream()
                .map(this::toUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getById(Long id) {
        return userRepo.findById(id).map(this::toUserDto).orElse(null);
    }

    @Override
    public UserDto create(UserEnt userEnt) {
        return Optional.of(userEnt)
                .map(e -> this.setPassword(e, e.getPassword()))
                .map(userRepo::save)
                .map(this::toUserDto)
                .orElseThrow(()-> new IncorrectDataException(ErrorMessages.INCORRECT_DATA));
    }

    @Override
    public UserDto update(PartialUserDto partialUserDto) {
        return userRepo.findById(partialUserDto.getId())
                .map(e -> this.attachFields(e, partialUserDto))
                .map(userRepo::save)
                .map(this::toUserDto)
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
}
