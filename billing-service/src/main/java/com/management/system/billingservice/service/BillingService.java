package com.management.system.billingservice.service;

import billing.InvoiceRequest;
import billing.InvoiceResponse;
import com.management.system.billingservice.dto.InvoiceRequestDto;
import com.management.system.billingservice.exception.PatientNotFoundException;
import com.management.system.billingservice.grpc.PatientGrpcClientService;
import com.management.system.billingservice.mapper.InvoiceMapper;
import com.management.system.billingservice.model.Invoice;
import com.management.system.billingservice.repository.InvoiceRepository;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BillingService {
    private final PatientGrpcClientService patientClient;
    private final InvoiceRepository invoiceRepository;
    private final Validator validator;

    public BillingService(PatientGrpcClientService patientClient, InvoiceRepository invoiceRepository,
                          Validator validator) {
        this.patientClient = patientClient;
        this.invoiceRepository = invoiceRepository;
        this.validator = validator;
    }

    public InvoiceResponse createInvoice(InvoiceRequest request){
        InvoiceRequestDto dto = InvoiceMapper.toDto(request);
        var violations = validator.validate(dto);
        if(!violations.isEmpty()){
            throw new ConstraintViolationException(violations);
        }
        UUID patientId = UUID.fromString(dto.getPatientId());
        boolean exist = patientClient.checkPatientExist(patientId);
        if(!exist){
            throw new PatientNotFoundException("patient with Id "+patientId+" do not exist");
        }
        Invoice invoice = InvoiceMapper.toModel(dto);
        invoice = invoiceRepository.save(invoice);
        return InvoiceMapper.toProto(invoice);
    }
}
