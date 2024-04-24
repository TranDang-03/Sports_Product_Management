package com.example.endgame.repository;

import com.example.endgame.entity.SanPhamDanhMuc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SanPhamDanhMucRepository extends JpaRepository<SanPhamDanhMuc, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM SanPhamDanhMuc sd WHERE sd.sanPham.id = :sanPhamId")
    void deleteBySanPhamId(@Param("sanPhamId") Long sanPhamId);

    @Query("SELECT sd FROM SanPhamDanhMuc sd WHERE sd.sanPham.id = :sanPhamId")
    List<SanPhamDanhMuc> findBySanPhamId(@Param("sanPhamId") Long sanPhamId);

}