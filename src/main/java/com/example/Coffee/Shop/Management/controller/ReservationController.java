package com.example.Coffee.Shop.Management.controller;

import com.example.Coffee.Shop.Management.dto.CreateReservationRequestDto;
import com.example.Coffee.Shop.Management.dto.UpdateReservationRequestDto;
import com.example.Coffee.Shop.Management.dto.UpdateReservationResponseDto;
import com.example.Coffee.Shop.Management.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reservations")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<UpdateReservationResponseDto> createReservation(@Valid @RequestBody CreateReservationRequestDto dto) {
        UpdateReservationResponseDto result = reservationService.createReservation(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UpdateReservationResponseDto> getReservationById(@PathVariable Long id) {
        return ResponseEntity.ok(reservationService.getReservationByTable(id));
    }

    @GetMapping("/table/{id}")
    public ResponseEntity<List<UpdateReservationResponseDto>> getReservationsByTable(@PathVariable Long id) {
        return ResponseEntity.ok(reservationService.getReservationsByTable(id));
    }

    @GetMapping("/available-slots")
    public ResponseEntity<List<LocalTime>> getAvailableTimeSlots(@RequestParam Long tableId, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date){
        return ResponseEntity.ok(reservationService.getAvailableTimeSlots(tableId, date));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateReservationResponseDto> updateReservation(@PathVariable Long id, @Valid @RequestBody UpdateReservationRequestDto dto) {
        return ResponseEntity.ok(reservationService.updateReservation(dto, id));
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelReservation(@PathVariable Long id) {
        reservationService.cancelReservation(id);
        return ResponseEntity.noContent().build();

    }
}
