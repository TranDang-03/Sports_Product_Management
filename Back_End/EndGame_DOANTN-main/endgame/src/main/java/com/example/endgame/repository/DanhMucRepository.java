package com.example.endgame.repository;

import com.example.endgame.entity.DanhMuc;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DanhMucRepository extends JpaRepository<DanhMuc, Long> {
    DanhMuc findByTen(String ten);
}