package com.example.endgame.mapper;

import com.example.endgame.dto.ThanhToanDto;
import com.example.endgame.entity.ThanhToan;

public class ThanhToanMapper {
    public ThanhToanDto mapToThanhToanDto(ThanhToan thanhToan) {
        ThanhToanDto thanhToanDto = new ThanhToanDto(
                thanhToan.getId(),
                thanhToan.getTen()
        );
        return thanhToanDto;
    }

    public ThanhToan mapToThanhToan(ThanhToanDto thanhToanDto) {
        ThanhToan thanhToan = new ThanhToan(
                thanhToanDto.getId(),
                thanhToanDto.getTen()
        );
        return thanhToan;
    }

}
