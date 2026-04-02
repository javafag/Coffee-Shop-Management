package com.example.Coffee.Shop.Management.service;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ReservationTimeService {
    public LocalDateTime calculateEndTime(LocalDateTime startTime, Integer guestCount) {
        return guestCount <= 2 ? startTime.plusHours(2) : startTime.plusHours(4);
    }
}
