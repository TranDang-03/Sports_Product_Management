package com.example.endgame.repository;

import com.example.endgame.entity.MauSac;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MauSacRepository extends JpaRepository<MauSac, Long> {

    MauSac findByGiaTriAndTen(String giaTri, String ten);
}