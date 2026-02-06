package com.management.system.patientservice.dto;

import com.management.system.patientservice.model.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class PatientRequestDTO {

    @NotBlank(message = "Name is required")
    @Size(max = 80, message = "Name should be less then 80 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Size(max = 254, message = "Max size of email exceeded")
    @Email(message = "Invalid email format")
    private String email;

    @NotNull(message = "Gender is required")
    private Gender gender;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "Date Of Birth is required")
    private String dateOfBirth;

    @NotBlank(message = "Register date is required")
    private String registeredDate;

    public @NotBlank(message = "Name is required") @Size(max = 80, message = "Name should be less then 80 characters") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Name is required") @Size(max = 80, message = "Name should be less then 80 characters") String name) {
        this.name = name;
    }

    public @NotBlank(message = "Email is required") @Size(max = 254, message = "Max size of email exceeded") @Email(message = "Invalid email format") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "Email is required") @Size(max = 254, message = "Max size of email exceeded") @Email(message = "Invalid email format") String email) {
        this.email = email;
    }

    public @NotNull(message = "Gender is required") Gender getGender() {
        return gender;
    }

    public void setGender(@NotBlank(message = "Gender is required") Gender gender) {
        this.gender = gender;
    }

    public @NotBlank(message = "Address is required") String getAddress() {
        return address;
    }

    public void setAddress(@NotBlank(message = "Address is required") String address) {
        this.address = address;
    }

    public @NotBlank(message = "Date Of Birth is required") String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(@NotBlank(message = "Date Of Birth is required") String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public @NotBlank(message = "Register date is required") String getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(@NotBlank(message = "Register date is required") String registeredDate) {
        this.registeredDate = registeredDate;
    }
}
