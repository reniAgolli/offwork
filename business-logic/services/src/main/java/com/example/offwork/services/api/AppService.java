package com.example.offwork.services.api;


import com.example.offwork.domain.dtos.ApplicationDto;
import com.example.offwork.domain.dtos.UserPrincipal;

import java.util.List;

public interface AppService {

    List<ApplicationDto> getAll(UserPrincipal principal);

    ApplicationDto getById(Long id);

    ApplicationDto create(ApplicationDto ApplicationDto, UserPrincipal userPrincipal);

    ApplicationDto update(ApplicationDto ApplicationDto);

    void delete(Long id);

    ApplicationDto confirm(Long id, UserPrincipal userPrincipal);

    ApplicationDto revoke(Long id, UserPrincipal userPrincipal);
}
