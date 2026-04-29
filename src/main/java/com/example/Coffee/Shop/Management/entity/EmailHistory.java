package com.example.Coffee.Shop.Management.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "email_history")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Email
    @Column(name = "recipient_email")
    String recipientEmail;

    @NotBlank
    String subject;

    @NotBlank
    String body;

    @Enumerated(EnumType.STRING)
    private EmailStatus status;

    @Enumerated(EnumType.STRING)
    private EmailType type;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = true)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", nullable = true)
    private Reservation reservation;

    @NotNull
    @Column(name = "created_at")
    private LocalDateTime  createdAt;

    @Column(name = "error_message")
    private String errorMessage;



}
