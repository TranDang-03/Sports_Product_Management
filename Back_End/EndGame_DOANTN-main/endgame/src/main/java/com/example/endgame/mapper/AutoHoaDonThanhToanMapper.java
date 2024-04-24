package com.example.endgame.mapper;

import com.example.endgame.dto.HoaDonThanhToanDto;
import com.example.endgame.entity.ThanhToanHoaDon;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AutoHoaDonThanhToanMapper {
    AutoHoaDonThanhToanMapper MAPPER = Mappers.getMapper(AutoHoaDonThanhToanMapper.class);

    HoaDonThanhToanDto mapToHoaDonThanhToanDto(ThanhToanHoaDon thanhToanHoaDon);

    ThanhToanHoaDon mapToHoaDonThanhToan(HoaDonThanhToanDto hoaDonThanhToanDto);
}
