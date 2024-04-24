package com.example.endgame.repository;

import com.example.endgame.entity.GioHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface GioHangRepository extends JpaRepository<GioHang, Long> {
    List<GioHang> findByTaiKhoanUid(String uid);

    GioHang findByTaiKhoanUidAndSanPhamChiTietId(String uid, Long id);
    List<GioHang> findBySanPhamChiTietId( Long id);


}
