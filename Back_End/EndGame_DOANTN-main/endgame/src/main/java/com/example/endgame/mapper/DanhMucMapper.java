package com.example.endgame.mapper;

import com.example.endgame.dto.DanhMucDto;
import com.example.endgame.entity.DanhMuc;

public class DanhMucMapper {
    public DanhMucDto mapToDanhMucDto(DanhMuc danhMuc) {
        DanhMucDto danhMucDto = new DanhMucDto(
                danhMuc.getId(),
                danhMuc.getTen(),
                danhMuc.getTrangThai()
        );
        return danhMucDto;
    }

    public DanhMuc mapToDanhMuc(DanhMucDto danhMucDto) {
        DanhMuc danhMuc = new DanhMuc(
                danhMucDto.getId(),
                danhMucDto.getTen(),
                danhMucDto.getTrangThai()
        );
        return danhMuc;
    }
}
