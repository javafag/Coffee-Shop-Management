package com.example.Coffee.Shop.Management.service;

import com.example.Coffee.Shop.Management.entity.*;
import com.example.Coffee.Shop.Management.repository.EmailHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.example.Coffee.Shop.Management.entity.EmailStatus.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final EmailHistoryRepository emailHistoryRepository;



    //1
   private EmailHistory saveEmailHistory (String recipientEmail, String subject, String body, EmailType type, Order order, Reservation reservation){
       EmailHistory emailHistory = EmailHistory.builder()
               .recipientEmail(recipientEmail)
               .subject(subject)
               .body(body)
               .status(PENDING)
               .type(type)
               .order(order)
               .reservation(reservation)
               .createdAt(LocalDateTime.now())
               .build();

       return emailHistoryRepository.save(emailHistory);
   }

    //2
    public void sendReservationConfirmation(Reservation reservation) {
        if (reservation.getCustomerEmail() == null || reservation.getCustomerEmail().isEmpty()) {
            log.warn("Cannot send confirmation: No email provided for reservation {}", reservation.getId());
            return;
        }

        EmailHistory emailHistory = null;

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(reservation.getCustomerEmail());
            message.setSubject("Reservation Confirmation");
            message.setText("Dear " + reservation.getCustomerName() + ",\n\n" +
                    "Your reservation at our coffee shop has been confirmed.\n" +
                    "Date & Time: " + reservation.getStartTime() + "\n" +
                    "Guests: " + reservation.getGuestCount() + "\n\n" +
                    "We look forward to serving you!");

            emailHistory = saveEmailHistory(
                    reservation.getCustomerEmail(),
                    "Reservation Confirmation",
                    "Dear " + reservation.getCustomerName() + ",\n\n" +
                    "Your reservation at our coffee shop has been confirmed.\n" +
                    "Date & Time: " + reservation.getStartTime() + "\n" +
                            "Guests: " + reservation.getGuestCount() + "\n\n" +
                            "We look forward to serving you!",
                    EmailType.RESERVATION_CONFIRMATION,
                    null,
                    reservation
            );
            javaMailSender.send(message);
            emailHistory.setStatus(SENT);
            emailHistoryRepository.save(emailHistory);
            log.info("Reservation confirmation email sent to {}", reservation.getCustomerEmail());
        } catch ( Exception e) {
            if (emailHistory != null){
                emailHistory.setStatus(FAILED);
                emailHistory.setErrorMessage(e.getMessage());
                emailHistoryRepository.save(emailHistory);
            }

            log.error("Failed to send reservation confirmation email to {}", reservation.getCustomerEmail(), e);


        }
    }

    //3
    public void sendReservationCancellation(Reservation reservation) {
        if (reservation.getCustomerEmail() == null || reservation.getCustomerEmail().isEmpty()) {
            return;
        }

        EmailHistory emailHistory = null;

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(reservation.getCustomerEmail());
            message.setSubject("Reservation Cancelled");
            message.setText("Dear " + reservation.getCustomerName() + ",\n\n" +
                    "Your reservation for " + reservation.getStartTime() + " has been cancelled.\n\n" +
                    "We hope to see you another time.");

            emailHistory = saveEmailHistory(
                    reservation.getCustomerEmail(),
                    "Reservation Cancelled",
                    "Dear " + reservation.getCustomerName() + ",\n\n" +
                    "Your reservation for " + reservation.getStartTime() + " has been cancelled.\n\n" +
                    "We hope to see you another time.",
                    EmailType.RESERVATION_CANCELLED,
                    null,
                    reservation
            );
            
            javaMailSender.send(message);
            emailHistory.setStatus(SENT);
            emailHistoryRepository.save(emailHistory);
            log.info("Reservation cancellation email sent to {}", reservation.getCustomerEmail());
        } catch (Exception e) {
            if (emailHistory != null) {
                emailHistory.setStatus(FAILED);
                emailHistory.setErrorMessage(e.getMessage());
                emailHistoryRepository.save(emailHistory);
            }
            log.error("Failed to send reservation cancellation email to {}", reservation.getCustomerEmail(), e);
        }
    }


}
