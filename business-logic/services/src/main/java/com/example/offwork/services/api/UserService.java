package com.example.offwork.services.api;

import com.example.offwork.domain.dtos.PartialUserDto;
import com.example.offwork.domain.dtos.ResetPasswordDto;
import com.example.offwork.domain.dtos.UserDto;
import com.example.offwork.domain.entities.UserEnt;

import java.util.List;

public interface UserService {

    List<UserDto> getAll();

    UserDto getById(Long id);

    UserDto create(UserEnt userEnt);

    UserDto update(PartialUserDto partialUserDto);

    void delete(Long id);

    PartialUserDto resetPassword(ResetPasswordDto resetPasswordDto);

}
