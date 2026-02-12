package com.management.system.billingservice.exception;

import io.grpc.*;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ExceptionInterceptor implements ServerInterceptor {

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
            ServerCall<ReqT, RespT> call,
            Metadata headers,
            ServerCallHandler<ReqT, RespT> next) {

        ServerCall.Listener<ReqT> delegate = next.startCall(call, headers);

        return new ForwardingServerCallListener.SimpleForwardingServerCallListener<ReqT>(delegate) {
            @Override
            public void onHalfClose() {
                try {
                    super.onHalfClose();
                } catch (Exception ex) {
                    handleException(ex, call);
                }
            }
        };
    }

    private void handleException(Exception ex, ServerCall<?, ?> call) {
        Status status;
        switch (ex) {
            case ConstraintViolationException constraintViolationException -> {
                String errors = constraintViolationException.getConstraintViolations()
                        .stream()
                        .map(ConstraintViolation::getMessage)
                        .collect(Collectors.joining(", "));

                status = Status.INVALID_ARGUMENT.withDescription("Validation failed: " + errors);
            }
            case IllegalArgumentException illegalArgumentException ->
                    status = Status.INVALID_ARGUMENT.withDescription(ex.getMessage());
            case PatientNotFoundException patientNotFoundException ->
                    status = Status.NOT_FOUND.withDescription(ex.getMessage());
            default ->
                    status = Status.INTERNAL.withDescription("An unexpected error occurred: " + ex.getMessage());
        }
        call.close(status, new Metadata());
    }
}