package com.example.endgame.mapper;

import com.example.endgame.entity.SanPham;
import com.example.endgame.dto.SanPhamDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AutoProductMapper {
    AutoProductMapper MAPPER = Mappers.getMapper(AutoProductMapper.class);

    SanPhamDto mapToSanPhamDto(SanPham sanPham);

    SanPham mapToSanPham(SanPhamDto sanPhamDto);

}
