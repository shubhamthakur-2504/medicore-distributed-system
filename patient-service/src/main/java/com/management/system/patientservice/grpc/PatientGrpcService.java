package com.management.system.patientservice.grpc;

import com.management.system.patientservice.service.PatientsService;
import io.grpc.stub.StreamObserver;
import org.springframework.grpc.server.service.GrpcService;
import patient.PatientLookupRequest;
import patient.PatientLookupResponse;
import patient.PatientServiceGrpc.PatientServiceImplBase;

@GrpcService
public class PatientGrpcService extends PatientServiceImplBase {
    private final PatientsService patientsService;

    public PatientGrpcService(PatientsService patientsService) {
        this.patientsService = patientsService;
    }

    @Override
    public void verifyPatient(PatientLookupRequest request,
                              StreamObserver<PatientLookupResponse> responseObserver){

        boolean exist = patientsService.exists((request.getPatientId()));
        PatientLookupResponse response = PatientLookupResponse.newBuilder().setExists(exist).build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
