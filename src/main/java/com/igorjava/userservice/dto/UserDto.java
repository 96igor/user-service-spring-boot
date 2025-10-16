package com.igorjava.userservice.dto;

import com.igorjava.userservice.entity.User;
import java.time.LocalDate;

public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String email;
    private Long ageInDays;

    public static UserDto fromEntity(User u) {
        UserDto d = new UserDto();
        d.id = u.getId();
        d.firstName = u.getFirstName();
        d.lastName = u.getLastName();
        d.birthDate = u.getBirthDate();
        d.email = u.getEmail();
        d.ageInDays = u.getAgeInDays();
        return d;
    }

    public Long getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public LocalDate getBirthDate() { return birthDate; }
    public String getEmail() { return email; }
    public Long getAgeInDays() { return ageInDays; }
}
