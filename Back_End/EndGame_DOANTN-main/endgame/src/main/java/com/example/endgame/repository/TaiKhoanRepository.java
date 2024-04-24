package com.example.endgame.repository;

import com.example.endgame.entity.TaiKhoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaiKhoanRepository extends JpaRepository<TaiKhoan, String> {

    @Query("SELECT chucVu FROM TaiKhoan WHERE uid = :id")
    int findChucVuById(@Param("id") String id);

    TaiKhoan findTaiKhoanByUid(String uid);

    List<TaiKhoan> findByChucVu(int cv);
}