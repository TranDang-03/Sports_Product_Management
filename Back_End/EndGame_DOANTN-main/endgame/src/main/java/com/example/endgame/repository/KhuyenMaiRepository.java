package com.example.endgame.repository;

import com.example.endgame.entity.KhuyenMai;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KhuyenMaiRepository extends JpaRepository<KhuyenMai, Long> {
    KhuyenMai findByMa(String ma);
}