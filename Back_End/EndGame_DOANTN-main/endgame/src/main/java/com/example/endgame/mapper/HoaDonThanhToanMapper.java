package com.example.endgame.mapper;

import com.example.endgame.dto.HoaDonThanhToanDto;
import com.example.endgame.entity.ThanhToanHoaDon;

public class HoaDonThanhToanMapper {

    public HoaDonThanhToanDto mapToHoaDonThanhToanDto(ThanhToanHoaDon thanhToanHoaDon) {
        HoaDonThanhToanDto hoaDonThanhToanDto = new HoaDonThanhToanDto(
                thanhToanHoaDon.getId(),
                thanhToanHoaDon.getHoaDon(),
                thanhToanHoaDon.getThanhToan(),
                thanhToanHoaDon.getSoTien()
        );
        return hoaDonThanhToanDto;
    }


    public ThanhToanHoaDon mapToHoaDonThanhToan(HoaDonThanhToanDto hoaDonThanhToanDto) {
        ThanhToanHoaDon thanhToanHoaDon = new ThanhToanHoaDon(
                hoaDonThanhToanDto.getId(),
                hoaDonThanhToanDto.getHoaDon(),
                hoaDonThanhToanDto.getThanhToan(),
                hoaDonThanhToanDto.getSoTien()
        );
        return thanhToanHoaDon;
    }
}
