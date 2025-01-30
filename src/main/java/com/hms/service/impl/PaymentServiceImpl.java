package com.hms.service.impl;

import com.hms.entity.Payment;
import com.hms.enums.PaymentStatus;
import com.hms.enums.PaymentType;
import com.hms.exception.ResourceNotFoundException;
import com.hms.repository.LeaseRepository;
import com.hms.repository.PaymentRepository;
import com.hms.repository.PropertyRepository;
import com.hms.repository.UserRepository;
import com.hms.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final LeaseRepository leaseRepository;
    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;

    @Override
    public Payment createPayment(Payment payment) {
        payment.setStatus(PaymentStatus.PENDING);
        payment.setPaymentDate(LocalDate.now());

        if (payment.getLease() != null && payment.getLease().getId() != null) {
            payment.setLease(leaseRepository.findById(payment.getLease().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Lease not found")));
        }
        if (payment.getProperty() != null && payment.getProperty().getId() != null) {
            payment.setProperty(propertyRepository.findById(payment.getProperty().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Property not found")));
        }
        if (payment.getTenant() != null && payment.getTenant().getId() != null) {
            payment.setTenant(userRepository.findById(payment.getTenant().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Tenant not found")));
        }

        return paymentRepository.save(payment);
    }

    @Override
    public Payment updatePayment(UUID id, Payment payment) {
        Payment existingPayment = getPaymentById(id);
        payment.setId(id);

        if (payment.getLease() != null && payment.getLease().getId() != null) {
            payment.setLease(leaseRepository.findById(payment.getLease().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Lease not found")));
        }
        if (payment.getProperty() != null && payment.getProperty().getId() != null) {
            payment.setProperty(propertyRepository.findById(payment.getProperty().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Property not found")));
        }
        if (payment.getTenant() != null && payment.getTenant().getId() != null) {
            payment.setTenant(userRepository.findById(payment.getTenant().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Tenant not found")));
        }

        return paymentRepository.save(payment);
    }

    @Override
    public void deletePayment(UUID id) {
        Payment payment = getPaymentById(id);
        paymentRepository.delete(payment);
    }

    @Override
    @Transactional(readOnly = true)
    public Payment getPaymentById(UUID id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Payment> getPaymentsByLease(UUID leaseId) {
        return paymentRepository.findByLeaseId(leaseId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Payment> getPaymentsByTenant(UUID tenantId) {
        return paymentRepository.findByTenantId(tenantId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Payment> getPaymentsByProperty(UUID propertyId) {
        return paymentRepository.findByPropertyId(propertyId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Payment> getPaymentsByStatus(PaymentStatus status) {
        return paymentRepository.findByStatus(status);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Payment> getPaymentsByType(PaymentType type) {
        return paymentRepository.findByType(type);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Payment> getPaymentsByDateRange(LocalDate startDate, LocalDate endDate) {
        return paymentRepository.findByPaymentDateBetween(startDate, endDate);
    }

    @Override
    public Payment processPayment(UUID id) {
        Payment payment = getPaymentById(id);
        payment.setStatus(PaymentStatus.PROCESSED);
        payment.setProcessedDate(LocalDate.now());
        return paymentRepository.save(payment);
    }

    @Override
    public Payment cancelPayment(UUID id, String reason) {
        Payment payment = getPaymentById(id);
        payment.setStatus(PaymentStatus.CANCELLED);
        payment.setCancellationReason(reason);
        payment.setCancelledDate(LocalDate.now());
        return paymentRepository.save(payment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Payment> getOverduePayments() {
        return paymentRepository.findByDueDateBeforeAndStatus(LocalDate.now(), PaymentStatus.PENDING);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Payment> getUpcomingPayments() {
        LocalDate today = LocalDate.now();
        LocalDate thirtyDaysFromNow = today.plusDays(30);
        return paymentRepository.findByDueDateBetweenAndStatus(today, thirtyDaysFromNow, PaymentStatus.PENDING);
    }

    @Override
    @Transactional(readOnly = true)
    public Double calculateTotalPayments(UUID propertyId, LocalDate startDate, LocalDate endDate) {
        List<Payment> payments = paymentRepository.findByPropertyIdAndPaymentDateBetweenAndStatus(
                propertyId, startDate, endDate, PaymentStatus.PROCESSED);
        return payments.stream()
                .mapToDouble(Payment::getAmount)
                .sum();
    }
}