package com.example.endgame.service;

import com.example.endgame.entity.TaiKhoan;

import java.util.List;
import java.util.Optional;

public interface TaiKhoanService {
    Optional<TaiKhoan> findById(String s);

    boolean checkChucVu(List<Integer> values, String id);
}
