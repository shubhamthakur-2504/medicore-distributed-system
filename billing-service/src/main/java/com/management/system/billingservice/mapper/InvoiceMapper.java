package com.management.system.billingservice.mapper;

import billing.InvoiceRequest;
import billing.InvoiceResponse;
import com.management.system.billingservice.dto.InvoiceRequestDto;
import com.management.system.billingservice.model.Invoice;
import com.management.system.billingservice.model.InvoiceStatus;

import java.math.BigDecimal;
import java.util.UUID;

public class InvoiceMapper {

    public static InvoiceRequestDto toDto(InvoiceRequest request) {
        InvoiceRequestDto invoiceDto = new InvoiceRequestDto();
        invoiceDto.setPatientId(request.getPatientId().trim());
        try {
            invoiceDto.setAmount(new BigDecimal(request.getAmount()));
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid amount");
        }
        return invoiceDto;
    }

    public static Invoice toModel(InvoiceRequestDto request) {
        Invoice invoice = new Invoice();
        invoice.setPatientId(UUID.fromString(request.getPatientId()));
        invoice.setAmount(request.getAmount());
        invoice.setStatus(InvoiceStatus.ISSUED);

        return invoice;
    }

    public static InvoiceResponse toProto(Invoice invoice) {
        return InvoiceResponse.newBuilder().setInvoiceId(invoice.getId().toString()).setStatus(invoice.getStatus().toString()).setRemainingAmount(invoice.getAmount().toString()).build();
    }
}
