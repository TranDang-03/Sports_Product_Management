package com.example.endgame.mapper;

import com.example.endgame.dto.ThuongHieuDto;
import com.example.endgame.entity.ThuongHieu;

public class ThuongHieuMapper {
    public ThuongHieuDto mapToThuongHieuDto(ThuongHieu thuongHieu) {
        ThuongHieuDto thuongHieuDto = new ThuongHieuDto(
                thuongHieu.getId(),
                thuongHieu.getTen(),
                thuongHieu.getTrangThai()
        );
        return thuongHieuDto;
    }

    public ThuongHieu mapToThuongHieu(ThuongHieuDto thuongHieuDto) {
        ThuongHieu thuongHieu = new ThuongHieu(
                thuongHieuDto.getId(),
                thuongHieuDto.getTen(),
                thuongHieuDto.getTrangThai()
        );
        return thuongHieu;
    }
}
