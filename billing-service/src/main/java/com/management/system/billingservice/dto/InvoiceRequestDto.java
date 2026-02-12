package com.management.system.billingservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;
import java.util.UUID;

public class InvoiceRequestDto {
    @NotNull(message = "patient Id is required")
    @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
            message = "The provided string is not a valid UUID format")
    private String patientId;

    @NotNull(message = "invoice amount is required")
    @Range(min = 0)
    private BigDecimal amount;

    public @NotNull(message = "patient Id is required") String getPatientId() {
        return patientId;
    }

    public void setPatientId(@NotNull(message = "patient Id is required") String patientId) {
        this.patientId = patientId;
    }

    public @NotNull(message = "invoice amount is required") @Range(min = 0) BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(@NotNull(message = "invoice amount is required") @Range(min = 0) BigDecimal amount) {
        this.amount = amount;
    }
}
