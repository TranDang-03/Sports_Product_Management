package com.example.endgame.mapper;

import com.example.endgame.dto.DanhMucDto;
import com.example.endgame.entity.DanhMuc;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AutoDanhMucMapper {
    AutoDanhMucMapper MAPPER = Mappers.getMapper(AutoDanhMucMapper.class);

    DanhMucDto mapToDanhMucDto(DanhMuc danhMuc);

    DanhMuc mapToDanhMuc(DanhMucDto danhMucDto);
}
