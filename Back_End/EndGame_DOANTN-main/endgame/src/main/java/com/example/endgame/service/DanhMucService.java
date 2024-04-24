package com.example.endgame.service;

import com.example.endgame.dto.DanhMucDto;

import java.util.List;

public interface DanhMucService {
    List<DanhMucDto> getAllDanhMuc();

    DanhMucDto createDanhMuc(DanhMucDto danhMucDto);

    DanhMucDto getById(Long id);

    DanhMucDto updateDanhMuc(DanhMucDto danhMucDto, Long id);

    void deleteDanhMuc (Long id);
}
