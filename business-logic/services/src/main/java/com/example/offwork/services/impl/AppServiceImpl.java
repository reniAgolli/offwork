package com.example.offwork.services.impl;

import com.example.offwork.domain.dtos.ApplicationDto;
import com.example.offwork.domain.dtos.UserPrincipal;
import com.example.offwork.domain.entities.ApplicationEnt;
import com.example.offwork.domain.entities.UserEnt;
import com.example.offwork.domain.exception.EntityNotFoundException;
import com.example.offwork.domain.exception.IncorrectDataException;
import com.example.offwork.domain.extras.ApplicationStatus;
import com.example.offwork.domain.extras.ErrorMessages;
import com.example.offwork.domain.extras.Role;
import com.example.offwork.persistence.dao.AppRepo;
import com.example.offwork.persistence.dao.UserRepo;
import com.example.offwork.services.api.AppService;
import com.example.offwork.services.security.CurrentUser;
import com.example.offwork.services.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AppServiceImpl implements AppService {

    private static Logger logger = LogManager.getLogger(AppServiceImpl.class);

    @Value("${app.maxDays}")
    private int maxDays;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private AppRepo appRepo;
    @Autowired
    private Utils utils;

    @Override
    public List<ApplicationDto> getAll(UserPrincipal principal) {
        List<ApplicationEnt> leaves = Utils.hasRole(principal, Role.ROLE_ADMIN.name()) ?
                appRepo.findAll() : appRepo.findAllByRequestedById(principal.getId());

        return leaves.stream()
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
        logger.info(applicationDto.getEndDate() >= applicationDto.getStartDate());
        if (applicationDto.getEndDate() <= applicationDto.getStartDate()) {
            throw new IncorrectDataException(ErrorMessages.STARTDATE_LESS_THEN_ENDDATE);
        }
        double dif = Utils.getDifferenceOfDays(applicationDto.getStartDate(), applicationDto.getEndDate());
        if (dif > maxDays) {
            throw new IncorrectDataException(ErrorMessages.LEAVE_HAS_EXCIDED_MAX_DAYS);
        }
        logger.info(dif);
        double ttn = utils.calculateAppDaysForUser(principal.getId());
        logger.info(ttn);
        if (dif > maxDays) {
            throw new IncorrectDataException(ErrorMessages.LEAVE_HAS_EXCIDED_MAX_DAYS);
        }
        logger.info(ttn + dif);

        if (ttn + dif > maxDays) {
            throw new IncorrectDataException(ErrorMessages.NOT_ENAUGHT_LEAVE_DAYS);
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
        logger.info(applicationDto.getEndDate() + "=>" + applicationDto.getStartDate() + "=" + (applicationDto.getEndDate() - applicationDto.getStartDate()));
        if (applicationDto.getEndDate() <= applicationDto.getStartDate()) {
            throw new IncorrectDataException(ErrorMessages.STARTDATE_LESS_THEN_ENDDATE);
        }
        return appRepo.findById(applicationDto.getId())
                .map(e -> this.addProps(e, applicationDto))
                .map(this::checkValidity)
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

    private ApplicationEnt checkValidity(ApplicationEnt applicationEnt) {
        if (applicationEnt.getStatus().equals(ApplicationStatus.CONFIRMED)) {
            List<ApplicationEnt> apps = appRepo.findAllByRequestedByIdAndStatusNotApproved(applicationEnt.getRequestedBy().getId())
                    .stream()
                    .filter(e -> applicationEnt.getId() != e.getId())
                    .collect(Collectors.toList());

            logger.info(applicationEnt.getEndDate() >= applicationEnt.getStartDate());
            if (applicationEnt.getEndDate() <= applicationEnt.getStartDate()) {
                throw new IncorrectDataException(ErrorMessages.STARTDATE_LESS_THEN_ENDDATE);
            }
            double dif = Utils.getDifferenceOfDays(applicationEnt.getStartDate(), applicationEnt.getEndDate());
            if (dif > maxDays) {
                throw new IncorrectDataException(ErrorMessages.LEAVE_HAS_EXCIDED_MAX_DAYS);
            }
            logger.info(dif);
            double ttn = Utils.getDifferenceOfDaysForList(apps);
            logger.info(ttn);
            if (dif > maxDays) {
                throw new IncorrectDataException(ErrorMessages.LEAVE_HAS_EXCIDED_MAX_DAYS);
            }
            logger.info(ttn + dif);

            if (ttn + dif > maxDays) {
                throw new IncorrectDataException(ErrorMessages.NOT_ENAUGHT_LEAVE_DAYS);
            }
        }
        return applicationEnt;
    }

}