package com.example.Coffee.Shop.Management.service;

import com.example.Coffee.Shop.Management.entity.Reservation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender javaMailSender;

    public void sendReservationConfirmation(Reservation reservation) {
        if (reservation.getCustomerEmail() == null || reservation.getCustomerEmail().isEmpty()) {
            log.warn("Cannot send confirmation: No email provided for reservation {}", reservation.getId());
            return;
        }

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(reservation.getCustomerEmail());
            message.setSubject("Reservation Confirmation");
            message.setText("Dear " + reservation.getCustomerName() + ",\n\n" +
                    "Your reservation at our coffee shop has been confirmed.\n" +
                    "Date & Time: " + reservation.getStartTime() + "\n" +
                    "Guests: " + reservation.getGuestCount() + "\n\n" +
                    "We look forward to serving you!");
            
            javaMailSender.send(message);
            log.info("Reservation confirmation email sent to {}", reservation.getCustomerEmail());
        } catch (Exception e) {
            log.error("Failed to send reservation confirmation email to {}", reservation.getCustomerEmail(), e);
        }
    }

    public void sendReservationCancellation(Reservation reservation) {
        if (reservation.getCustomerEmail() == null || reservation.getCustomerEmail().isEmpty()) {
            return;
        }

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(reservation.getCustomerEmail());
            message.setSubject("Reservation Cancelled");
            message.setText("Dear " + reservation.getCustomerName() + ",\n\n" +
                    "Your reservation for " + reservation.getStartTime() + " has been cancelled.\n\n" +
                    "We hope to see you another time.");
            
            javaMailSender.send(message);
            log.info("Reservation cancellation email sent to {}", reservation.getCustomerEmail());
        } catch (Exception e) {
            log.error("Failed to send reservation cancellation email to {}", reservation.getCustomerEmail(), e);
        }
    }
}
