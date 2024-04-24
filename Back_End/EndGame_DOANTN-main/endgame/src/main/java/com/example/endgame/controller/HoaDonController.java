package com.example.endgame.controller;

import com.example.endgame.entity.*;
import com.example.endgame.repository.GioHangRepository;
import com.example.endgame.repository.HoaDonChiTietRepository;
import com.example.endgame.repository.HoaDonRepository;
import com.example.endgame.repository.SanPhamChiTietRepository;
import com.example.endgame.service.EmailService;
import com.example.endgame.service.HoaDonService;
import com.example.endgame.service.TaiKhoanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/hoa-don")
@CrossOrigin(origins = "http://localhost:3000")
public class HoaDonController {


    @Autowired
    public HoaDonService hoaDonService;
    @Autowired
    public HoaDonRepository hoaDonRepository;


    @Autowired
    public TaiKhoanService taiKhoanService;
    @Autowired
    public SanPhamChiTietRepository sanPhamChiTietRepository;


    @Autowired
    public GioHangRepository gioHangRepository;
    @Autowired
    public EmailService emailService;

    @Autowired
    public HoaDonChiTietRepository hoaDonChiTietRepository;

    @GetMapping
    ResponseEntity<?> getAll(@RequestParam("idKH") String idKH) {
        return ResponseEntity.ok(this.hoaDonService.getAll(idKH));
    }


    @PostMapping("/{randomCode}")
    ResponseEntity<?> create(@Valid @RequestBody HoaDon hoaDon, BindingResult bindingResult, @PathVariable("randomCode") String randomCode) {
        Map<String, String> errors = new HashMap<>();
        if (bindingResult.hasErrors()) {
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }
        TaiKhoan taiKhoan = taiKhoanService.findById(hoaDon.getTaiKhoan().getUid()).orElse(null);
        if (taiKhoan == null) {
            return ResponseEntity.ok("UID khong ton tai");
        }
        hoaDon.setGhiChu(hoaDon.getGhiChu() + "*" + randomCode + "*");
        HoaDon hoaDonReturn = this.hoaDonService.save(hoaDon);

        // code đoạn này non tay

        List<GioHang> gioHangList = this.gioHangRepository.findByTaiKhoanUid(hoaDon.getTaiKhoan().getUid());
        System.out.println(gioHangList.size());

        for (GioHang gioHang : gioHangList) {
            SanPhamChiTiet sanPhamChiTietTemp = gioHang.getSanPhamChiTiet();

            // Kiểm tra null hoặc số lượng
            if (sanPhamChiTietTemp == null || sanPhamChiTietTemp.getSoLuong() - gioHang.getSoLuong() < 0) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }

            // Cập nhật số lượng trong kho
            sanPhamChiTietTemp.setSoLuong(sanPhamChiTietTemp.getSoLuong() - gioHang.getSoLuong());

            // Tạo và cấu hình chi tiết hóa đơn
            HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
            hoaDonChiTiet.setHoaDon(hoaDonReturn);
            hoaDonChiTiet.setSanPhamChiTiet(sanPhamChiTietTemp);
            hoaDonChiTiet.setSoLuong(gioHang.getSoLuong());
            hoaDonChiTiet.setGiaSanPham(sanPhamChiTietTemp.getGiaBan());
            hoaDonChiTiet.setGhiChu("Không có ghi chú");

            // Lưu các thay đổi vào cơ sở dữ liệu
            this.sanPhamChiTietRepository.save(sanPhamChiTietTemp);
            this.hoaDonChiTietRepository.save(hoaDonChiTiet);
            this.gioHangRepository.delete(gioHang);


        }
        System.out.println(hoaDonReturn.toString());
        System.out.println(hoaDonReturn.getTaiKhoan().toString());
    //    sendEmailAsync(hoaDon.getTaiKhoan().getEmail(), "Hóa đơn tạo thành công", "Đơn hàng của bạn đã được tạo thành công, cảm ơn bạn đã ủng hộ shop");
        return ResponseEntity.ok(hoaDonReturn);
    }
    @Async
    public void sendEmailAsync(String to, String subject, String body) {
        emailService.sendEmail(to, subject, body);
    }
    @PostMapping("/blank")
    ResponseEntity<?> create() {
        HoaDon hoaDon = new HoaDon();
        hoaDon.setTrangThai(0);
        hoaDon.setGhiChu("Không có ghi chú");

        hoaDon.setNgayTao(LocalDate.now());
        hoaDon.setTenNguoiNhan("khách lẻ");

        hoaDon.setLoaiHoaDon(false);

        return ResponseEntity.ok(this.hoaDonService.save(hoaDon));
    }

    @PutMapping
    ResponseEntity<?> update(@RequestBody HoaDon hoaDon) {
        ;
        hoaDon.setNgayCapNhat(Instant.now());
        System.out.println("Đã nhập cập nhật hóa đơn " + hoaDon);
        HoaDon hd = this.hoaDonRepository.findById(hoaDon.getId()).get();
        hd.setNgayCapNhat(hoaDon.getNgayCapNhat());
        hd.setTenNguoiNhan(hoaDon.getTenNguoiNhan());
        hd.setSdtNhanHang(hoaDon.getSdtNhanHang());
        hd.setPhiVanChuyen(hoaDon.getPhiVanChuyen());
        hd.setDiaChiCuThe(hoaDon.getDiaChiCuThe());
        hd.setGhiChu(hoaDon.getGhiChu());
        hd.setTrangThai(hoaDon.getTrangThai());
        hd.setTaiKhoan(taiKhoanService.findById(hoaDon.getTaiKhoan().getUid()).get());
        return ResponseEntity.ok(this.hoaDonRepository.save(hd));

    }

    @GetMapping("/soluongtheothang/{uid}")
    public ResponseEntity<List<Object[]>> getSoLuongDonHangTheoThang(@PathVariable String uid) {
        List<Object[]> result = hoaDonService.getSoLuongDonHangTheoThang(uid);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/soluongOffline/{uid}")
    public ResponseEntity<List<Object[]>> getSoLuongDonHangOfflineTheoThang(@PathVariable String uid) {
        List<Object[]> result = hoaDonService.getSoLuongDonHangOflineTheoThang(uid);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/blankOffline/")
    public ResponseEntity<?> getSoLuongDonHangOffline() {
        List<HoaDon> result = hoaDonRepository.findByLoaiHoaDonAndTrangThai(false, 0);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("deleteOfflione/{id}")
    public void delete(@PathVariable("id") Long id) {
        List<HoaDonChiTiet> hdcts = this.hoaDonChiTietRepository.findAllByHoaDon_Id(id);
        for (HoaDonChiTiet hd : hdcts
        ) {
            this.sanPhamChiTietRepository.updateSoLuongById(hd.getSanPhamChiTiet().getId(), hd.getSoLuong());
        }
        this.hoaDonChiTietRepository.deletex(id);
        this.hoaDonRepository.deleteById(id);
    }

}