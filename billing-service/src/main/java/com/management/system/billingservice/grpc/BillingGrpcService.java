package com.management.system.billingservice.grpc;

import billing.InvoiceRequest;
import billing.InvoiceResponse;
import billing.BillingServiceGrpc.BillingServiceImplBase;
import com.management.system.billingservice.service.BillingService;
import io.grpc.stub.StreamObserver;
import org.springframework.grpc.server.service.GrpcService;

@GrpcService
public class BillingGrpcService extends BillingServiceImplBase {

    private final BillingService billingService;

    public BillingGrpcService(BillingService billingService) {
        this.billingService = billingService;
    }

    @Override
    public void createInvoice (InvoiceRequest invoiceRequest,
                                      StreamObserver<InvoiceResponse> responseObserver) {

        InvoiceResponse response = billingService.createInvoice(invoiceRequest);

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
