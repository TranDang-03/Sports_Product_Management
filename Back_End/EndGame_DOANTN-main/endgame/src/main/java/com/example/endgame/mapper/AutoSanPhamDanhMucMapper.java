package com.example.endgame.mapper;

import com.example.endgame.dto.SanPhamDanhMucDto;
import com.example.endgame.entity.SanPhamDanhMuc;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AutoSanPhamDanhMucMapper {
    AutoSanPhamDanhMucMapper MAPPER = Mappers.getMapper(AutoSanPhamDanhMucMapper.class);

    SanPhamDanhMucDto mapToSanPhamDanhMucDto(SanPhamDanhMuc sanPhamDanhMuc);

    SanPhamDanhMuc mapToSanPhamDanhMuc(SanPhamDanhMucDto sanPhamDanhMucDto);
}
