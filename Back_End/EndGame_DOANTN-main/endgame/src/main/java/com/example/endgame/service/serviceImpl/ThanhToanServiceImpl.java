package com.example.endgame.service.serviceImpl;

import com.example.endgame.repository.ThanhToanRepository;
import com.example.endgame.service.ThanhToanService;
import com.example.endgame.dto.ThanhToanDto;
import com.example.endgame.entity.ThanhToan;
import com.example.endgame.exception.ResourceNotFoundException;
import com.example.endgame.mapper.AutoThanhToanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ThanhToanServiceImpl implements ThanhToanService {
    @Autowired
    private ThanhToanRepository repository;

    @Override
    public ThanhToanDto createPayment(ThanhToanDto thanhToanDto) {
        ThanhToan thanhToan = AutoThanhToanMapper.MAPPER.mapToThanhToan(thanhToanDto);
        repository.save(thanhToan);
        return AutoThanhToanMapper.MAPPER.mapToThanhToanDto(thanhToan);
    }



    @Override
    public List<ThanhToanDto> getAllThanhToan() {
        return repository.findAll().stream()
                                   .map(AutoThanhToanMapper.MAPPER :: mapToThanhToanDto)

                                   .collect(Collectors.toList());
    }

    @Override
    public ThanhToanDto getById(Long id) {
        return repository.findById(id)
                         .map(AutoThanhToanMapper.MAPPER :: mapToThanhToanDto)
                         .orElseThrow(
                () -> new ResourceNotFoundException("Thanh toan", "id", id)
        );
    }
}
