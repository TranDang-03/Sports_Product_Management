package com.example.endgame.mapper;

import com.example.endgame.dto.SanPhamDanhMucDto;
import com.example.endgame.entity.SanPhamDanhMuc;

public class SanPhamDanhMucMapper {
    
    public SanPhamDanhMucDto mapToSanPhamDanhMucDto(SanPhamDanhMuc sanPhamDanhMuc) {
        SanPhamDanhMucDto sanPhamDanhMucDto = new SanPhamDanhMucDto(
                sanPhamDanhMuc.getId(),
                sanPhamDanhMuc.getSanPham(),
                sanPhamDanhMuc.getDanhMuc()
        );
        return sanPhamDanhMucDto;
    }

    public SanPhamDanhMuc mapToSanPhamDanhMuc(SanPhamDanhMucDto sanPhamDanhMucDto) {
        SanPhamDanhMuc sanPhamDanhMuc = new SanPhamDanhMuc(
                sanPhamDanhMucDto.getId(),
                sanPhamDanhMucDto.getSanPham(),
                sanPhamDanhMucDto.getDanhMuc()
        );
        return sanPhamDanhMuc;
    }
    
}
