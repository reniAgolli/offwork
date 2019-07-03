package com.example.offwork.services.impl;

import com.example.offwork.domain.dtos.ApplicationDto;
import com.example.offwork.domain.dtos.UserPrincipal;
import com.example.offwork.domain.entities.ApplicationEnt;
import com.example.offwork.domain.entities.UserEnt;
import com.example.offwork.domain.exception.EntityNotFoundException;
import com.example.offwork.domain.exception.IncorrectDataException;
import com.example.offwork.domain.extras.ApplicationStatus;
import com.example.offwork.domain.extras.ErrorMessages;
import com.example.offwork.persistence.dao.AppRepo;
import com.example.offwork.persistence.dao.UserRepo;
import com.example.offwork.services.api.AppService;
import com.example.offwork.services.security.CurrentUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AppServiceImpl implements AppService {

    @Value("${app.maxDays}")
    private int maxDays;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private AppRepo appRepo;

    @Override
    public List<ApplicationDto> getAll(UserPrincipal principal) {
        System.out.println(this.calculateAppDaysForUser(principal));
        return appRepo.findAll().stream()
                .map(this::toApplicationDto)
                .collect(Collectors.toList());
    }

    @Override
    public ApplicationDto getById(Long id) {

        return appRepo.findById(id)
                .map(this::toApplicationDto)
                .orElse(null);
    }

    @Override
    public ApplicationDto create(ApplicationDto applicationDto, @CurrentUser UserPrincipal principal) {
        System.out.println(applicationDto.getEndDate() >= applicationDto.getStartDate());
        if (applicationDto.getEndDate() <= applicationDto.getStartDate()) {
            throw new IncorrectDataException(ErrorMessages.STARTDATE_LESS_THEN_ENDDATE);
        }
        UserEnt userEnt = userRepo.findById(principal.getId()).orElse(null);
        return Optional.of(this.toApplicationEnt(applicationDto))
                .map(e -> this.setStatus(e, ApplicationStatus.PENDING))
                .map(e -> {
                    e.setRequestedBy(userEnt);
                    return e;
                })
                .map(appRepo::save)
                .map(this::toApplicationDto)
                .orElse(null);
    }

    @Override
    public ApplicationDto update(ApplicationDto applicationDto) {
        System.out.println(applicationDto.getEndDate() + "=>" + applicationDto.getStartDate() + "=" + (applicationDto.getEndDate() - applicationDto.getStartDate()));
        if (applicationDto.getEndDate() <= applicationDto.getStartDate()) {
            throw new IncorrectDataException(ErrorMessages.STARTDATE_LESS_THEN_ENDDATE);
        }
        return appRepo.findById(applicationDto.getId())
                .map(e -> this.addProps(e, applicationDto))
                .map(appRepo::save)
                .map(this::toApplicationDto)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessages.APPLICATION_DOESNT_EXIST));
    }

    @Override
    public void delete(Long id) {
        appRepo.findById(id)
                .ifPresent(e -> appRepo.delete(e));
    }

    @Override
    public ApplicationDto confirm(Long id, UserPrincipal principal) {
        UserEnt userEnt = userRepo.findById(principal.getId()).orElse(null);
        return appRepo.findById(id)
                .map(e -> this.setStatus(e, ApplicationStatus.CONFIRMED)).map(e -> {
                    e.setConfirmedBy(userEnt);
                    return e;
                })
                .map(appRepo::save)
                .map(this::toApplicationDto)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessages.APPLICATION_DOESNT_EXIST));
    }

    @Override
    public ApplicationDto revoke(Long id, UserPrincipal principal) {
        UserEnt userEnt = userRepo.findById(principal.getId()).orElse(null);
        return appRepo.findById(id)
                .map(e -> this.setStatus(e, ApplicationStatus.REVOKED)).map(e -> {
                    e.setConfirmedBy(userEnt);
                    return e;
                })
                .map(appRepo::save)
                .map(this::toApplicationDto)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessages.APPLICATION_DOESNT_EXIST));
    }

    private ApplicationDto toApplicationDto(ApplicationEnt applicationEnt) {
        return modelMapper.map(applicationEnt, ApplicationDto.class);
    }

    private ApplicationEnt toApplicationEnt(ApplicationDto applicationDto) {
        return modelMapper.map(applicationDto, ApplicationEnt.class);
    }

    private ApplicationEnt setStatus(ApplicationEnt applicationEnt, ApplicationStatus applicationStatus) {
        applicationEnt.setStatus(applicationStatus);
        return applicationEnt;
    }

    private ApplicationEnt addProps(ApplicationEnt applicationEnt, ApplicationDto applicationDto) {
        applicationEnt.setStartDate(applicationDto.getStartDate());
        applicationEnt.setEndDate(applicationDto.getEndDate());
        return applicationEnt;
    }

    private double calculateAppDaysForUser(UserPrincipal userPrincipal) {
        return this.appRepo.findAllByRequestedById(userPrincipal.getId()).stream()
                .mapToDouble(e -> getDifferenceOfDays(e.getStartDate(), e.getEndDate()))
                .sum();

    }

    private double getDifferenceOfDays(long date, long date1) {
        System.out.println(date);
        System.out.println(date1);
        return Math.ceil((date1 - date) / (24 * 60 * 60 * 1000));
    }
}