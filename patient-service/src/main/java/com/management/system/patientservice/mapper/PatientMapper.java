package com.management.system.patientservice.mapper;

import com.management.system.patientservice.dto.PatientRequestDTO;
import com.management.system.patientservice.dto.PatientResponseDTO;
import com.management.system.patientservice.dto.PatientUpdateDTO;
import com.management.system.patientservice.model.Patient;

import java.time.LocalDate;

public class PatientMapper {
    public static PatientResponseDTO toDTO(Patient patient){
        PatientResponseDTO patientDTO = new PatientResponseDTO();
        patientDTO.setId(patient.getId().toString());
        patientDTO.setName(patient.getName());
        patientDTO.setEmail(patient.getEmail());
        patientDTO.setGender(patient.getGender().toString());
        patientDTO.setAddress(patient.getAddress());
        patientDTO.setDateOfBirth(patient.getDateOfBirth().toString());

        return patientDTO;
    }

    public static Patient toModel(PatientRequestDTO patientRequestDTO) {
        Patient patient = new Patient();
        patient.setName(patientRequestDTO.getName());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setGender(patientRequestDTO.getGender());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));
        patient.setRegisteredDate(LocalDate.parse(patientRequestDTO.getRegisteredDate()));

        return patient;
    }

    public static Patient updatePatient(PatientUpdateDTO newData, Patient existingPatient){
        if(newData.getName() != null){
            existingPatient.setName(newData.getName());
        }
        if(newData.getGender() != null){
            existingPatient.setGender(newData.getGender());
        }
        if(newData.getAddress() != null){
            existingPatient.setAddress(newData.getAddress());
        }
        if(newData.getDateOfBirth() != null){
            existingPatient.setDateOfBirth(LocalDate.parse(newData.getDateOfBirth()));
        }

        return existingPatient;
    }
}
