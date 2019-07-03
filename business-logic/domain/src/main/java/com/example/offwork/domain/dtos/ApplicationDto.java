package com.example.offwork.domain.dtos;

import com.example.offwork.domain.extras.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationDto {

    private long id;
    private long startDate;
    private long endDate;
    private ApplicationStatus status;
    private PartialUserDto confirmedBy;
    private PartialUserDto requestedBy;

    public ApplicationDto() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public PartialUserDto getConfirmedBy() {
        return confirmedBy;
    }

    public void setConfirmedBy(PartialUserDto confirmedBy) {
        this.confirmedBy = confirmedBy;
    }

    public PartialUserDto getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(PartialUserDto requestedBy) {
        this.requestedBy = requestedBy;
    }
}
