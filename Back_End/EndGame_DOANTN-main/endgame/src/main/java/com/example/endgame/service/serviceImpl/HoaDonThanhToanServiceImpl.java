package com.example.endgame.service.serviceImpl;

import com.example.endgame.repository.HoaDonRepository;
import com.example.endgame.repository.ThanhToanHoaDonRepository;
import com.example.endgame.repository.ThanhToanRepository;
import com.example.endgame.service.HoaDonThanhToanService;
import com.example.endgame.dto.HoaDonThanhToanDto;
import com.example.endgame.entity.ThanhToanHoaDon;
import com.example.endgame.exception.ResourceNotFoundException;
import com.example.endgame.mapper.AutoHoaDonThanhToanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HoaDonThanhToanServiceImpl implements HoaDonThanhToanService {
    @Autowired
    private ThanhToanHoaDonRepository repository;
    @Autowired
    private HoaDonRepository hoaDonRepository;
    @Autowired
    private ThanhToanRepository thanhToanRepository;

    @Override
    public HoaDonThanhToanDto createPaymentBill(HoaDonThanhToanDto hoaDonThanhToanDto) {
        ThanhToanHoaDon hoaDon = AutoHoaDonThanhToanMapper.MAPPER.mapToHoaDonThanhToan(hoaDonThanhToanDto);
        repository.save(hoaDon);
        return AutoHoaDonThanhToanMapper.MAPPER.mapToHoaDonThanhToanDto(hoaDon);
    }

    @Override
    public HoaDonThanhToanDto editPaymentBill(HoaDonThanhToanDto hoaDonThanhToanDto, Long id) {
        ThanhToanHoaDon thanhToanHoaDon = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Hoa don thanh toan", "id", id)
        );
        thanhToanHoaDon.setHoaDon(hoaDonThanhToanDto.getHoaDon());
        thanhToanHoaDon.setSoTien(hoaDonThanhToanDto.getSoTien());
        thanhToanHoaDon.setThanhToan(hoaDonThanhToanDto.getThanhToan());
        repository.save(thanhToanHoaDon);
        return AutoHoaDonThanhToanMapper.MAPPER.mapToHoaDonThanhToanDto(thanhToanHoaDon);
    }

    @Override
    public void deletePaymentBill(Long id) {
        ThanhToanHoaDon thanhToanHoaDon= repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Hoa don thanh toan", "id", id)
        );
        repository.delete(thanhToanHoaDon);
    }

    @Override
    public List<HoaDonThanhToanDto> getAllByHoaDon(Long idHoaDon) {
        return repository.getAllByHoaDon(idHoaDon).stream()
                                                  .map(AutoHoaDonThanhToanMapper.MAPPER :: mapToHoaDonThanhToanDto)
                                                  .collect(Collectors.toList());
    }

    @Override
    public List<HoaDonThanhToanDto> getAllByThanhToan(Long idThanhToan) {
        return repository.getAllByThanhToan(idThanhToan).stream()
                                                        .map(AutoHoaDonThanhToanMapper.MAPPER :: mapToHoaDonThanhToanDto)
                                                        .collect(Collectors.toList());
    }
}
