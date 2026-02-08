package com.management.system.patientservice.controller;

import com.management.system.patientservice.dto.PatientRequestDTO;
import com.management.system.patientservice.dto.PatientResponseDTO;
import com.management.system.patientservice.dto.PatientUpdateDTO;
import com.management.system.patientservice.service.PatientsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
@Tag(name = "Patient", description = "API for Patient service")
public class PatientController {
    private final PatientsService patientsService;

    public PatientController(PatientsService patientsService) {
        this.patientsService = patientsService;
    }

    @Operation(summary = "Get all patients data")
    @GetMapping
    public ResponseEntity<List<PatientResponseDTO>> getPatients() {
        List<PatientResponseDTO> patients = patientsService.getPatients();
        return ResponseEntity.ok().body(patients);
    }

    @Operation(summary = "Get a patient details")
    @GetMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> getPatientDetails(@PathVariable UUID id){
        PatientResponseDTO patient = patientsService.getPatientDetails(id);
        return ResponseEntity.ok().body(patient);
    }

    @Operation(summary = "Create patient data")
    @PostMapping
    public ResponseEntity<PatientResponseDTO> createPatient(@Valid @RequestBody PatientRequestDTO patientDetail){
        PatientResponseDTO patient = patientsService.createPatient(patientDetail);
        return ResponseEntity.ok().body(patient);
    }

    @Operation(summary = "Update a patient details")
    @PatchMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> updatePatient(@PathVariable UUID id,
                                                            @Valid @RequestBody PatientUpdateDTO newData){
        PatientResponseDTO updatedPatient = patientsService.updatePatientDetails(newData, id);
        return ResponseEntity.ok().body(updatedPatient);
    }

    @Operation(summary = "Delete a patient data")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable UUID id){
        patientsService.deletePatient(id);

        return ResponseEntity.noContent().build();
    }
}