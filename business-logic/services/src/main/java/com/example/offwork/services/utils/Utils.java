package com.example.offwork.services.utils;

import com.example.offwork.domain.dtos.UserPrincipal;
import com.example.offwork.domain.entities.ApplicationEnt;
import com.example.offwork.persistence.dao.AppRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Utils {

    @Autowired
    AppRepo appRepo;

    public static boolean hasRole(UserPrincipal principal, String role) {
        return principal.getAuthorities().stream()
                .anyMatch(e -> e.toString().equals(role));
    }

    public static double getDifferenceOfDaysForList(List<ApplicationEnt> applicationEnts) {
        return applicationEnts.stream()
                .mapToDouble(e -> getDifferenceOfDays(e.getStartDate(), e.getEndDate()))
                .sum();
    }

    public static double getDifferenceOfDays(long startdate, long enddate) {
        return Math.ceil((enddate - startdate) / (24 * 60 * 60 * 1000.0));
    }

    public double calculateAppDaysForUser(Long id) {
        return this.appRepo.findAllByRequestedByIdAndStatusNotApproved(id).stream()
                .mapToDouble(e -> getDifferenceOfDays(e.getStartDate(), e.getEndDate()))
                .sum();
    }
}
