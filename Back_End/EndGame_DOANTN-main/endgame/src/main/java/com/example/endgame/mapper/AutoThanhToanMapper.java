package com.example.endgame.mapper;

import com.example.endgame.dto.ThanhToanDto;
import com.example.endgame.entity.ThanhToan;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AutoThanhToanMapper {

    AutoThanhToanMapper MAPPER = Mappers.getMapper(AutoThanhToanMapper.class);

    ThanhToanDto mapToThanhToanDto(ThanhToan thanhToan);

    ThanhToan mapToThanhToan(ThanhToanDto thanhToanDto);
}
