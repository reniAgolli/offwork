package com.example.offwork.domain.dtos;

import com.example.offwork.domain.extras.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
//@NoArgsConstructor
public class UserDto extends PartialUserDto {
    private List<ApplicationDto> applications;

    public UserDto(List<ApplicationDto> applications) {
        this.applications = applications;
    }

    public UserDto() {}

    public List<ApplicationDto> getApplications() {
        return applications;
    }

    public void setApplications(List<ApplicationDto> applications) {
        this.applications = applications;
    }
}
