package com.management.system.patientservice.service;

import com.management.system.patientservice.dto.PatientRequestDTO;
import com.management.system.patientservice.dto.PatientResponseDTO;
import com.management.system.patientservice.dto.PatientUpdateDTO;
import com.management.system.patientservice.exceptions.EmailAlreadyExistsException;
import com.management.system.patientservice.exceptions.ResourceNotFoundException;
import com.management.system.patientservice.mapper.PatientMapper;
import com.management.system.patientservice.model.Patient;
import com.management.system.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PatientsService {
    private final PatientRepository patientRepository;

    public PatientsService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<PatientResponseDTO> getPatients() {
        List<Patient> patients = patientRepository.findAll();
        //        patients.stream().map(patient -> PatientMapper.toDTO(patient)).toList();
        return patients.stream().map(PatientMapper::toDTO).toList();
    }

    public PatientResponseDTO getPatientDetails(UUID id) {
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Patient with the id " + id + " doesn't exist", "Patient"));
        return PatientMapper.toDTO(patient);
    }

    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {

        if (patientRepository.existsByEmail(patientRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException("A patient with this email: " + patientRequestDTO.getEmail() + " already exists");
        }

        Patient patient = patientRepository.save(PatientMapper.toModel(patientRequestDTO));
        return PatientMapper.toDTO(patient);
    }

    public PatientResponseDTO updatePatientDetails(PatientUpdateDTO newDate, UUID id) {

        Patient patient = patientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "Patient with the id " + id + " doesn't exist", "Patient"));
        patient = patientRepository.save(PatientMapper.updatePatient(newDate, patient));

        return PatientMapper.toDTO(patient);
    }

    public void deletePatient(UUID id) {
        if (!patientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Patient with the id " + id + " doesn't exist", "Patient");
        }
        patientRepository.deleteById(id);
    }

}
