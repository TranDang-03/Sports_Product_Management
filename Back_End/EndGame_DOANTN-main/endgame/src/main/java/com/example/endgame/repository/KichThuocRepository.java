package com.example.endgame.repository;

import com.example.endgame.entity.KichThuoc;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface KichThuocRepository extends JpaRepository<KichThuoc, Long> {
    KichThuoc findByGiaTri(BigDecimal gt);
}