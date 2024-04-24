package com.example.endgame.mapper;

import com.example.endgame.entity.MauSac;
import com.example.endgame.dto.MauSacDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AutoColorsMapper {
    AutoColorsMapper MAPPER = Mappers.getMapper(AutoColorsMapper.class);

    MauSacDto mapToMauSacDto(MauSac mauSac);

    MauSac mapToMauSac(MauSacDto mauSacDto);
}
