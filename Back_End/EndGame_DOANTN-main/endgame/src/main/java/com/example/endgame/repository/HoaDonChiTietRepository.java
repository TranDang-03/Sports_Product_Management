package com.example.endgame.repository;

import com.example.endgame.entity.HoaDonChiTiet;
import com.example.endgame.entity.KichThuoc;
import com.example.endgame.entity.MauSac;
import com.example.endgame.entity.SanPhamChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public interface HoaDonChiTietRepository extends JpaRepository<HoaDonChiTiet, Long> {
    @Query("SELECT hd from HoaDonChiTiet hd where :idHD is null or :idHD = '' or hd.hoaDon.id = :idHD")
    public List<HoaDonChiTiet> getAll(String idHD);

    @Query("SELECT hd from HoaDonChiTiet hd where (:idHD is null or :idHD = '' or hd.hoaDon.id = :idHD) and hd.id = :idHDCT")
    public List<HoaDonChiTiet> get(Long idHD, Long idHDCT);

    List<HoaDonChiTiet> findBySanPhamChiTiet_Id(Long id);
    public List<HoaDonChiTiet> findAllByHoaDon_Id(Long idHD);

    //bán chạy nhất
    @Query("SELECT hdct.sanPhamChiTiet.sanPham.id AS id, hdct.sanPhamChiTiet.sanPham.tenSanPham AS tenSanPham, SUM(hdct.soLuong) AS soLuongBanRa " +
            "FROM HoaDonChiTiet hdct " +
            "WHERE  hdct.hoaDon.trangThai = 5" +
            "GROUP BY hdct.sanPhamChiTiet.sanPham.id " +
            "ORDER BY soLuongBanRa DESC")
    List<Object[]> findTop10SellingProducts();

    //bán ế nhất
    @Query("SELECT hdct.sanPhamChiTiet.sanPham.id AS id, hdct.sanPhamChiTiet.sanPham.tenSanPham AS tenSanPham, SUM(hdct.soLuong) AS soLuongBanRa " +
            "FROM HoaDonChiTiet hdct " +
            "WHERE  hdct.hoaDon.trangThai = 5" +
            "GROUP BY hdct.sanPhamChiTiet.sanPham.id " +
            "ORDER BY soLuongBanRa ASC ")
    List<Object[]> findTop10NotSellingProducts();

    //số sản phẩm đã bán
    @Query("SELECT SUM(hdct.soLuong) " +
            "FROM HoaDonChiTiet hdct " +
            "WHERE hdct.hoaDon.trangThai = 5 ")
    Long getTotalQuantitySold();
    //số sản phẩm đã huy
    @Query("SELECT SUM(hdct.soLuong) " +
            "FROM HoaDonChiTiet hdct " +
            "WHERE hdct.hoaDon.trangThai = 6 ")
    Long getTotalQuantityCancel();
    // sản phẩm ất ơ
    @Query("SELECT SUM(hdct.soLuong) " +
            "FROM HoaDonChiTiet hdct " +
            "WHERE hdct.hoaDon.trangThai = 0 or hdct.hoaDon.trangThai = 1 or hdct.hoaDon.trangThai = 2 or hdct.hoaDon.trangThai = 3 or hdct.hoaDon.trangThai = 4 or hdct.hoaDon.trangThai = 7 ")
    Long getTotalQuantityOther();

    // sản phẩm thông minh

    @Query("SELECT hdct.hoaDon.trangThai AS trangThai, " +
            "SUM(hdct.soLuong) AS soLuongHoaDon " +
            "FROM HoaDonChiTiet hdct " +
            "GROUP BY hdct.hoaDon.trangThai")
    List<Object[]> getQuantityByStatus();

    //tổng sản phẩm đã bán
    @Query("SELECT COALESCE(SUM(hdct.soLuong), 0) FROM HoaDonChiTiet hdct " +
            "WHERE hdct.sanPhamChiTiet.sanPham.id = :sanPhamId and  hdct.hoaDon.trangThai = 5")
    Integer findTongSoLuongDaBan(@Param("sanPhamId") Long sanPhamId);

    // trị giá sản phẩm
    @Query("SELECT COALESCE(SUM(hdct.soLuong), 0) AS soLuongDaBan, " +
            "COALESCE(SUM(hdct.giaSanPham * hdct.soLuong), 0) AS tongTienDaThuVe " +
            "FROM HoaDonChiTiet hdct " +
            "WHERE hdct.sanPhamChiTiet.sanPham.id = :sanPhamId and  hdct.hoaDon.trangThai = 5")
    Object[] findThongTinBanHang(@Param("sanPhamId") Long sanPhamId);

    //màu bán chạy

    @Query("SELECT sct.sanPhamChiTiet.mauSac , SUM(sct.soLuong) FROM HoaDonChiTiet sct " +
            "where sct.sanPhamChiTiet.sanPham.id = :sanPhamId  and sct.hoaDon.trangThai = 5 "+
            "GROUP BY sct.sanPhamChiTiet.mauSac " +
            "ORDER BY SUM(sct.soLuong) DESC")
    Object[] findMauSacBanChayNhat(@Param("sanPhamId") Long sanPhamId);
    //màu bán chạy

    @Query("SELECT sct.sanPhamChiTiet.kichThuoc,  SUM(sct.soLuong) FROM HoaDonChiTiet sct " +
            "where sct.sanPhamChiTiet.sanPham.id = :sanPhamId  and sct.hoaDon.trangThai = 5"+
            "GROUP BY sct.sanPhamChiTiet.kichThuoc " +
            "ORDER BY SUM(sct.soLuong) DESC")
    Object[] findKichThuocBanChayNhat(@Param("sanPhamId") Long sanPhamId);
    // không biết x3


    @Query("SELECT hdct.sanPhamChiTiet, SUM(hdct.soLuong) as soLuongBanRa " +
            "FROM HoaDonChiTiet hdct " +
            "WHERE hdct.sanPhamChiTiet.sanPham.id = :sanPhamId " +
            "AND hdct.hoaDon.trangThai = 5 " +
            "GROUP BY hdct.sanPhamChiTiet")
    List<Object[]> findxsa(@Param("sanPhamId") Long sanPhamId);

    HoaDonChiTiet findBySanPhamChiTiet_IdAndHoaDon_id (Long idSp , Long idHd);
    @Transactional
    @Modifying
    @Query("DELETE FROM HoaDonChiTiet h WHERE h.hoaDon.id = :hd")
    void deletex(@Param("hd") Long hd);

}