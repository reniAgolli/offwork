package com.example.offwork.domain.entities;

import com.example.offwork.domain.extras.ApplicationStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "applications")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ApplicationEnt implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private long startDate;
    @Column(nullable = false)
    private long endDate;
    @Column(nullable = false)
    private ApplicationStatus status;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "confirmedBy_id", nullable = true)
    @JsonIgnoreProperties("applications")
    private UserEnt confirmedBy;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "requestedBy_id", nullable = true)
    @JsonIgnoreProperties("applications")
    private UserEnt requestedBy;

    public ApplicationEnt() {
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

    public UserEnt getConfirmedBy() {
        return confirmedBy;
    }

    public void setConfirmedBy(UserEnt confirmedBy) {
        this.confirmedBy = confirmedBy;
    }

    public UserEnt getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(UserEnt requestedBy) {
        this.requestedBy = requestedBy;
    }
}
