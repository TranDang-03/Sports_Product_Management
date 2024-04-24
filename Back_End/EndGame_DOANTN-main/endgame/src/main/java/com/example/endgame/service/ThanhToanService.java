package com.example.endgame.service;

import com.example.endgame.dto.ThanhToanDto;

import java.util.List;

public interface ThanhToanService {
    ThanhToanDto createPayment(ThanhToanDto thanhToanDto);

    List<ThanhToanDto>getAllThanhToan();
    ThanhToanDto getById(Long id);
}
