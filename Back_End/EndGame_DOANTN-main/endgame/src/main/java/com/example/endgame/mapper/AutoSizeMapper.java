package com.example.endgame.mapper;

import com.example.endgame.entity.KichThuoc;
import com.example.endgame.dto.KichThuocDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AutoSizeMapper {
    AutoSizeMapper MAPPER = Mappers.getMapper(AutoSizeMapper.class);
    KichThuocDto mapToKichThuocDto(KichThuoc kichThuoc);

    KichThuoc mapToKichThuoc(KichThuocDto kichThuocDto);
}
