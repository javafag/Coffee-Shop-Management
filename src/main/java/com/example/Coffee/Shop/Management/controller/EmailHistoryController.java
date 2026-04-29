package com.example.Coffee.Shop.Management.controller;

import com.example.Coffee.Shop.Management.entity.EmailHistory;
import com.example.Coffee.Shop.Management.entity.EmailStatus;
import com.example.Coffee.Shop.Management.entity.EmailType;
import com.example.Coffee.Shop.Management.repository.EmailHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/email-history")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class EmailHistoryController {

    private final EmailHistoryRepository emailHistoryRepository;

    @GetMapping
    public ResponseEntity<Page<EmailHistory>> getAllEmails(


    }

    @GetMapping("/{id}")
    public ResponseEntity<EmailHistory> getEmailById(@PathVariable Long id) {
       return null;
    }

    @GetMapping
    public ResponseEntity<List<EmailHistory>> getEmailsByOrderId() {
        return null;
    }

    @GetMapping
    public ResponseEntity<List<EmailHistory>> getEmailsByReservationId() {
        return null;
    }
}
