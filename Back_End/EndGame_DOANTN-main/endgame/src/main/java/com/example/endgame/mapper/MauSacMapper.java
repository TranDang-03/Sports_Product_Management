package com.example.endgame.mapper;

import com.example.endgame.entity.MauSac;
import com.example.endgame.dto.MauSacDto;

public class MauSacMapper {
    public MauSacDto mapToMauSacDto(MauSac mauSac) {
        MauSacDto mauSacDto = new MauSacDto(
                mauSac.getId(),
                mauSac.getGiaTri(),
                mauSac.getTen(),
                mauSac.getTrangThai()
        );
        return mauSacDto;
    }

    public MauSac mapToMauSac(MauSacDto mauSacDto) {
        MauSac mauSac = new MauSac(
                mauSacDto.getId(),
                mauSacDto.getGiaTri(),
                mauSacDto.getTen(),
                mauSacDto.getTrangThai()
        );
        return mauSac;
    }
}
