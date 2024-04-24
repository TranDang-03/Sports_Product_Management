package com.example.endgame.service;

import com.example.endgame.dto.SanPhamDanhMucDto;

import java.util.List;

public interface SanPhamDanhMucService {
    List<SanPhamDanhMucDto> getAllSPDMByDM (Long idDM);

    SanPhamDanhMucDto updateSPDM (Long idSPDM, Long idSP, Long idDM);

    void deleteSanPhamDanhMuc(Long idSP, Long idDM);

    List<SanPhamDanhMucDto> getAllSPDMBySP (Long idSP);

    SanPhamDanhMucDto createSPDM (Long idSP, Long idDM);

}
