package com.tesis.aike.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReservationDTO {
    private Long id;
    private UserDTO user;
    private CabinDTO cabin;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;

    public Long getId() {
        return id;
    }

    public UserDTO getUser() {
        return user;
    }

    public CabinDTO getCabin() {
        return cabin;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public void setCabin(CabinDTO cabin) {
        this.cabin = cabin;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ReservationDTO{" +
                "id=" + id +
                ", user=" + user +
                ", cabin=" + cabin +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", status='" + status + '\'' +
                '}';
    }
}
