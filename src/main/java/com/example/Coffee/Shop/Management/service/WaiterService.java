package com.example.Coffee.Shop.Management.service;

import com.example.Coffee.Shop.Management.dto.WaiterDto;
import com.example.Coffee.Shop.Management.entity.Waiter;
import com.example.Coffee.Shop.Management.repository.WaiterRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WaiterService {

    private final WaiterRepository waiterRepository;
    private final ModelMapper modelMapper;

    public WaiterDto createWaiter(WaiterDto dto) {
        Waiter waiter = modelMapper.map(dto, Waiter.class);
        waiter = waiterRepository.save(waiter);
        return modelMapper.map(waiter, WaiterDto.class);
    }

    public WaiterDto getWaiter(Long id) {
        Waiter waiter = waiterRepository.findById(id)
                .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Waiter not found"));
        return modelMapper.map(waiter, WaiterDto.class);
    }

    public List<WaiterDto> getAllWaiters() {
        return waiterRepository.findAll().stream()
                .map(w -> modelMapper.map(w, WaiterDto.class))
                .collect(Collectors.toList());
    }

    public WaiterDto updateWaiter(Long id, WaiterDto dto) {
        Waiter waiter = waiterRepository.findById(id)
                .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Waiter not found"));
        waiter.setName(dto.getName());
        waiter = waiterRepository.save(waiter);
        return modelMapper.map(waiter, WaiterDto.class);
    }

    public void deleteWaiter(Long id) {
        if (!waiterRepository.existsById(id)) {
            throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Waiter not found");
        }
        waiterRepository.deleteById(id);
    }
}
