package com.example.endgame.service;

import com.example.endgame.dto.KichThuocDto;

import java.util.List;

public interface KichThuocService {
    KichThuocDto createSize(KichThuocDto kichThuocDto, String uid);

    KichThuocDto getById(Long id);

    List<KichThuocDto> getAllSize();

    List<KichThuocDto> getAllAvailableSize();

    KichThuocDto updateSize(KichThuocDto updatedSize, Long id, String uid);

    void deleteSize(Long id, String uid);
}
