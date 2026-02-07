package com.management.system.patientservice.dto;

import com.management.system.patientservice.model.Gender;
import com.management.system.patientservice.validation.AtLeastOneNotNull;
import jakarta.validation.constraints.Size;

@AtLeastOneNotNull(message = "You must provide at least one field to update")
public class PatientUpdateDTO {

    @Size(max = 80)
    private String name;

    private Gender gender;
    private String address;
    private String dateOfBirth;

    public @Size(max = 80) String getName() {
        return name;
    }

    public void setName(@Size(max = 80) String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
