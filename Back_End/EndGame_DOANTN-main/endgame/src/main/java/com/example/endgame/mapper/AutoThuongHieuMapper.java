package com.example.endgame.mapper;

import com.example.endgame.dto.ThuongHieuDto;
import com.example.endgame.entity.ThuongHieu;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AutoThuongHieuMapper {
    AutoThuongHieuMapper MAPPER = Mappers.getMapper(AutoThuongHieuMapper.class);

    ThuongHieuDto mapToThuongHieuDto(ThuongHieu thuongHieu);

    ThuongHieu mapToThuongHieu(ThuongHieuDto thuongHieuDto);
}
