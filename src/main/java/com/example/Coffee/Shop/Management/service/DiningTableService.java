package com.example.Coffee.Shop.Management.service;

import com.example.Coffee.Shop.Management.dto.DiningTableRequestDto;
import com.example.Coffee.Shop.Management.dto.DiningTableResponseDto;
import com.example.Coffee.Shop.Management.entity.DiningTable;
import com.example.Coffee.Shop.Management.repository.DiningTableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiningTableService {
    private final DiningTableRepository diningTableRepository;

    private DiningTableResponseDto toDto(DiningTable entity) {
        return DiningTableResponseDto.builder()
                .id(entity.getId())
                .tableNumber(entity.getTableNumber())
                .capacity(entity.getCapacity())
                .isAvailable(entity.getIsAvailable())
                .isReserved(entity.getIsReserved())
                .isActive(entity.getIsActive())
                .build();
    }


    public DiningTableResponseDto getTableById(Long id) {
        DiningTable entity = diningTableRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Table not found"));

        return toDto(entity);
    }
    
    public List<DiningTableResponseDto> getAllTables() {
        List<DiningTable> entity = diningTableRepository.findAll();
        return entity.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<DiningTableResponseDto> getAvailableTables(){
        List<DiningTable> entity = diningTableRepository.findByIsActiveTrue();
        return entity.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<DiningTableResponseDto> getTablesByCapacity(Integer capacity){
        List<DiningTable> entity = diningTableRepository.findByCapacityGreaterThanEqual(capacity);
        return entity.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public void deleteTable(Long id) {
        DiningTable table = diningTableRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Table not found"));
        diningTableRepository.delete(table);
    }

    public DiningTableResponseDto createTable(DiningTableRequestDto dto){
        if(diningTableRepository.findByTableNumber(dto.getTableNumber()).isPresent()){
            throw new RuntimeException("Table already exists");
        }

        DiningTable entity = DiningTable.builder()
                .tableNumber(dto.getTableNumber())
                .capacity(dto.getCapacity())
                .isAvailable(dto.getIsAvailable())
                .isActive(true)
                .isReserved(false)
                .build();

        DiningTable saved = diningTableRepository.save(entity);
        return toDto(saved);
    }

    public DiningTableResponseDto updateTable(Long id, DiningTableRequestDto dto){

        DiningTable entity = diningTableRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "order not found"));

        if (dto.getTableNumber() != null && !dto.getTableNumber().equals(entity.getTableNumber())) {
            if (diningTableRepository.findByTableNumber(dto.getTableNumber()).isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Table number already exists");
            }
            entity.setTableNumber(dto.getTableNumber());
        }

        if (dto.getCapacity() != null) {
            entity.setCapacity(dto.getCapacity());
        }
        if (dto.getIsAvailable() != null) {
            entity.setIsAvailable(dto.getIsAvailable());
        }
        if (dto.getIsActive() != null) {
            entity.setIsActive(dto.getIsActive());
        }

        DiningTable saved = diningTableRepository.save(entity);
        return toDto(saved);
    }







}
