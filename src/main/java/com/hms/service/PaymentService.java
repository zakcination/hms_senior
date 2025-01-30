package com.hms.service;

import com.hms.entity.Payment;
import com.hms.enums.PaymentStatus;
import com.hms.enums.PaymentType;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface PaymentService {
    Payment createPayment(Payment payment);

    Payment updatePayment(UUID id, Payment payment);

    void deletePayment(UUID id);

    Payment getPaymentById(UUID id);

    List<Payment> getAllPayments();

    List<Payment> getPaymentsByLease(UUID leaseId);

    List<Payment> getPaymentsByTenant(UUID tenantId);

    List<Payment> getPaymentsByProperty(UUID propertyId);

    List<Payment> getPaymentsByStatus(PaymentStatus status);

    List<Payment> getPaymentsByType(PaymentType type);

    List<Payment> getPaymentsByDateRange(LocalDate startDate, LocalDate endDate);

    Payment processPayment(UUID id);

    Payment cancelPayment(UUID id, String reason);

    List<Payment> getOverduePayments();

    List<Payment> getUpcomingPayments();

    Double calculateTotalPayments(UUID propertyId, LocalDate startDate, LocalDate endDate);
}