package com.example.Coffee.Shop.Management.repository;

import com.example.Coffee.Shop.Management.entity.EmailHistory;
import com.example.Coffee.Shop.Management.entity.EmailStatus;
import com.example.Coffee.Shop.Management.entity.EmailType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EmailHistoryRepository extends JpaRepository<EmailHistory,Long> {

    List<EmailHistory> findByEmail(String recipientEmail);
    List<EmailHistory> findByRecipientEmailAndStatus(String recipientEmail, EmailStatus status);
    List<EmailHistory> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
    List<EmailHistory> findByStatus(EmailStatus status);
    List<EmailHistory> findByType(EmailType type);
    List<EmailHistory> findByOrderId(Long orderId);
    List<EmailHistory> findByReservationId(Long reservationId);



}
