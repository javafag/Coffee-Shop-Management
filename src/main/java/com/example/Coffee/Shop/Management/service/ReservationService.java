package com.example.Coffee.Shop.Management.service;

import com.example.Coffee.Shop.Management.dto.CreateReservationRequestDto;
import com.example.Coffee.Shop.Management.dto.UpdateReservationRequestDto;
import com.example.Coffee.Shop.Management.dto.UpdateReservationResponseDto;
import com.example.Coffee.Shop.Management.entity.DiningTable;
import com.example.Coffee.Shop.Management.entity.Reservation;
import com.example.Coffee.Shop.Management.entity.ReservationStatus;
import com.example.Coffee.Shop.Management.repository.DiningTableRepository;
import com.example.Coffee.Shop.Management.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final DiningTableRepository diningTableRepository;
    @Autowired
    private ModelMapper modelMapper;

    private UpdateReservationResponseDto toDto(Reservation entity) {
        return modelMapper.map(entity, UpdateReservationResponseDto.class);
    }

    public UpdateReservationResponseDto getReservationByTable(Long id) {
        Reservation entity = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
        return toDto(entity);
    }

    public UpdateReservationResponseDto createReservation(CreateReservationRequestDto dto) {

        DiningTable table = diningTableRepository.findById(dto.getDiningTableId())
                .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Table not found"));

        if (table.getIsActive() == null || !table.getIsActive()) {
            throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.BAD_REQUEST, "Table is InActive");
        }

        if (dto.getGuestCount() > table.getCapacity()) {
            throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.BAD_REQUEST, "More guests than capacity");
        }

        List<Reservation> existingReservations = reservationRepository
                .findByDiningTableIdAndReservationTimeBetween(
                        dto.getDiningTableId(),
                        dto.getReservationTime().toLocalDate().atStartOfDay(),
                        dto.getReservationTime().toLocalDate().plusDays(1).atStartOfDay()
                );


        for (Reservation existing : existingReservations) {
            if (existing.getStatus() != ReservationStatus.CANCELLED && timesOverlap(dto.getStartTime(), dto.getEndTime(), existing.getStartTime(), existing.getEndTime())) {
                throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.CONFLICT, "Time overlaps with existing reservation");
            }
        }

        Reservation entity = modelMapper.map(dto, Reservation.class);
        entity.setDiningTable(table);
        entity.setStatus(ReservationStatus.PENDING);
        Reservation saved = reservationRepository.save(entity);
        return toDto(saved);
    }

    public UpdateReservationResponseDto updateReservation(UpdateReservationRequestDto dto,Long id){
      Reservation entity = reservationRepository.findById(id)
              .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "This reservation does not exist"));
      if(entity.getStatus() == ReservationStatus.CANCELLED) {
          throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.BAD_REQUEST, "Cannot update cancelled reservation");
      }

      modelMapper.map(dto,entity);
      Reservation saved = reservationRepository.save(entity);
      return toDto(entity);

    }

    public void cancelReservation(Long id){
        Reservation entity = reservationRepository.findById(id)
                .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "This reservation does not exist"));
        if (entity.getStatus() == ReservationStatus.CANCELLED) {
            throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.BAD_REQUEST, "Reservation already cancelled");
        }
        entity.setStatus(ReservationStatus.CANCELLED);
        // Do not alter table.setIsReserved(false) blindly!
        reservationRepository.save(entity);
    }

    public List<UpdateReservationResponseDto> getReservationsByTable(Long tableId) {
        List<Reservation> reservations = reservationRepository.findByDiningTableId(tableId);
        
        List<Reservation> activeReservations = reservations.stream()
                .filter(r -> r.getStatus() != ReservationStatus.CANCELLED)
                .collect(Collectors.toList());
        
        return activeReservations.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
    
    public List<LocalTime> getAvailableTimeSlots(Long tableId, LocalDate date) {

        List<Reservation> dayReservations = reservationRepository
                .findByDiningTableIdAndReservationTimeBetween(
                        tableId,
                        date.atStartOfDay(),
                        date.plusDays(1).atStartOfDay()
                );
        

        List<LocalTime> allSlots = List.of(
                LocalTime.of(9, 0), LocalTime.of(10, 0), LocalTime.of(11, 0),
                LocalTime.of(12, 0), LocalTime.of(13, 0), LocalTime.of(14, 0),
                LocalTime.of(15, 0), LocalTime.of(16, 0), LocalTime.of(17, 0),
                LocalTime.of(18, 0), LocalTime.of(19, 0), LocalTime.of(20, 0),
                LocalTime.of(21, 0), LocalTime.of(22, 0), LocalTime.of(23, 0)
        );
        

        return allSlots.stream()
                .filter(slot -> {
                    LocalDateTime slotStart = date.atTime(slot);
                    LocalDateTime slotEnd = slotStart.plusHours(1);
                    return dayReservations.stream()
                            .filter(r -> r.getStatus() != ReservationStatus.CANCELLED)
                            .noneMatch(reservation -> timesOverlap(slotStart, slotEnd, reservation.getStartTime(), reservation.getEndTime()));
                })
                .collect(Collectors.toList());
    }
    

    private boolean timesOverlap(LocalDateTime newStart, LocalDateTime newEnd, LocalDateTime existingStart, LocalDateTime existingEnd) {
        return newStart.isBefore(existingEnd) && newEnd.isAfter(existingStart);
    }


}
