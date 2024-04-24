package com.example.endgame.repository;

import com.example.endgame.dto.ThuongHieuDto;
import com.example.endgame.entity.ThuongHieu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ThuongHieuRepository extends JpaRepository<ThuongHieu, Long> {


    @Query(value = "SELECT * from end_game.thuong_hieu where ten = :ten and id <> :id", nativeQuery = true)
    ThuongHieu findByTenNot(@Param("ten") String ten,@Param("id") Long id);

    @Query(value = "SELECT * from end_game.thuong_hieu a join end_game.san_pham b on a.id = b.thuong_hieu_id where b.id = :idSP", nativeQuery = true)
    List<ThuongHieuDto> findAllThuongHieuBySanPham(@Param("idSP") Long idSP);

    List<ThuongHieu> findByTen(String ten);
}