package com.management.system.billingservice.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import patient.PatientLookupRequest;
import patient.PatientServiceGrpc;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class PatientGrpcClientService {
    private final PatientServiceGrpc.PatientServiceBlockingStub patientStub;
    private final ManagedChannel channel;

    public PatientGrpcClientService(@Value("${patient.service.address:localhost}") String serverAddress,
                                    @Value("${patient.service.port:9000}") int serverPort){
        this.channel = ManagedChannelBuilder.forAddress(serverAddress,serverPort).usePlaintext().build();
        this.patientStub = PatientServiceGrpc.newBlockingStub(channel);
    }

    public boolean checkPatientExist(UUID patientId){
        try {
            PatientLookupRequest request = PatientLookupRequest.newBuilder().setPatientId(patientId.toString()).build();
            return patientStub.verifyPatient(request).getExists();
        } catch (Exception e){
            throw new RuntimeException("Patient service is unreachable");
        }
    }

    @PreDestroy
    public void shutdown() throws InterruptedException {
        if (channel != null) {
            channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}
