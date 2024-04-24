package com.example.endgame.controller;

import com.example.endgame.entity.HoaDon;
import com.example.endgame.entity.HoaDonChiTiet;
import com.example.endgame.entity.SanPhamChiTiet;
import com.example.endgame.repository.HoaDonChiTietRepository;
import com.example.endgame.repository.HoaDonRepository;
import com.example.endgame.repository.SanPhamChiTietRepository;
import com.example.endgame.service.HoaDonChiTietService;
import com.example.endgame.service.HoaDonService;
import com.example.endgame.service.SanPhamChiTietService;
import com.example.endgame.dto.HoaDonChiTietRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/admin/hoa-don-chi-tiet")
@CrossOrigin(origins = "http://localhost:3000")
public class HoaDonChiTietController {


    @Autowired
    public HoaDonChiTietService hoaDonChiTietService;
    @Autowired
    public HoaDonService hoaDonService;

    @Autowired
    public SanPhamChiTietService sanPhamChiTietService;
    @Autowired
    public HoaDonRepository hoaDonRepository;
    @Autowired
    public HoaDonChiTietRepository hoaDonChiTietRepository;
    @Autowired
    public SanPhamChiTietRepository sanPhamChiTietRepository;

    @GetMapping
    ResponseEntity<?> getAll(@RequestParam(value = "idHD", required = false) Optional<String> idHD) {
        return ResponseEntity.ok(this.hoaDonChiTietService.getAll(idHD.orElse(null)));
    }


    @PostMapping("/{idhd}")
    ResponseEntity<?> create(@Valid @RequestBody HoaDonChiTiet hoaDonChiTietRequest, @PathVariable("idhd") Long id) {
        System.out.println("Nhận về " + hoaDonChiTietRequest.toString());
        HoaDon hd = hoaDonRepository.findById(id).get();
        HoaDonChiTiet hdct = hoaDonChiTietRepository.findBySanPhamChiTiet_IdAndHoaDon_id(hoaDonChiTietRequest.getSanPhamChiTiet().getId(), id);
        SanPhamChiTiet spct = sanPhamChiTietRepository.findById(hoaDonChiTietRequest.getSanPhamChiTiet().getId()).get();
        if (hoaDonChiTietRequest.getSoLuong() > hoaDonChiTietRequest.getSanPhamChiTiet().getSoLuong()) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        if (spct.getSoLuong() - hoaDonChiTietRequest.getSoLuong() < 0) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        if (hdct != null) {
            System.out.println("Đã tồn tại cập nhật số lượng");

            this.sanPhamChiTietRepository.updateSoLuongById(hoaDonChiTietRequest.getSanPhamChiTiet().getId(), -hoaDonChiTietRequest.getSoLuong());

            hdct.setHoaDon(hd);
            hdct.setSoLuong(hdct.getSoLuong() + hoaDonChiTietRequest.getSoLuong());

            return ResponseEntity.ok(this.hoaDonChiTietService.save(hdct));
        }
        this.sanPhamChiTietRepository.updateSoLuong2ById(hoaDonChiTietRequest.getSanPhamChiTiet().getId(), hoaDonChiTietRequest.getSanPhamChiTiet().getSoLuong() - hoaDonChiTietRequest.getSoLuong());
        System.out.println("Đã cập nhật số lượng");
        hoaDonChiTietRequest.setHoaDon(hd);
        return ResponseEntity.ok(this.hoaDonChiTietService.save(hoaDonChiTietRequest));
    }

    @PostMapping("/s")
    ResponseEntity<?> creates(List<HoaDonChiTietRequest> hoaDonChiTietRequests) {
        List<HoaDonChiTiet> hoaDonChiTietSave = new ArrayList<>();
        for (HoaDonChiTietRequest hoaDonChiTietRequest : hoaDonChiTietRequests) {
            HoaDon hoaDon;
            if (hoaDonService.existsById(hoaDonChiTietRequest.getIdHD())) {
                hoaDon = this.hoaDonService.findById(hoaDonChiTietRequest.getIdHD()).get();
            } else {
                return ResponseEntity.ok("hoa don khong ton tai : " + hoaDonChiTietRequest.getIdHD());
            }
            SanPhamChiTiet sanPhamChiTiet;
            if (sanPhamChiTietService.existsById(hoaDonChiTietRequest.getIdSP())) {
                sanPhamChiTiet = this.sanPhamChiTietService.findById(hoaDonChiTietRequest.getIdSP()).get();
            } else {
                return ResponseEntity.ok("san pham khong ton tai : " + hoaDonChiTietRequest.getIdSP());
            }
            HoaDonChiTiet hoaDonChiTiet;
            List<HoaDonChiTiet> hoaDonChiTiets = hoaDonChiTietService.get(hoaDonChiTietRequest.getIdHD(), hoaDonChiTietRequest.getIdSP());
            if (hoaDonChiTiets.size() > 0) {
                hoaDonChiTiet = hoaDonChiTiets.get(0);
                hoaDonChiTiet.setSoLuong(hoaDonChiTietRequest.getSoLuong() + hoaDonChiTiet.getSoLuong());
            } else {
                hoaDonChiTiet = new HoaDonChiTiet();
                hoaDonChiTiet.setHoaDon(hoaDon);
                hoaDonChiTiet.setSanPhamChiTiet(sanPhamChiTiet);
                hoaDonChiTiet.setSoLuong(hoaDonChiTietRequest.getSoLuong());
                hoaDonChiTiet.setGiaSanPham(sanPhamChiTiet.getGiaBan());
            }
            hoaDonChiTietSave.add(hoaDonChiTiet);
        }

        return ResponseEntity.ok(this.hoaDonChiTietService.saveAll(hoaDonChiTietSave));
    }


    @DeleteMapping()
    ResponseEntity<?> delete(@RequestParam("id") Long id) {
        if (hoaDonChiTietService.existsById(id)) {
            HoaDonChiTiet hoaDonChiTiet = this.hoaDonChiTietService.findById(id).get();
            if (hoaDonChiTiet.getHoaDon().getTrangThai() == 0) {
                this.sanPhamChiTietRepository.updateSoLuongById(hoaDonChiTiet.getSanPhamChiTiet().getId(), hoaDonChiTiet.getSoLuong());
                hoaDonChiTietService.deleteById(hoaDonChiTiet.getId());
                return ResponseEntity.ok("thanh cong");
            } else {
                return ResponseEntity.ok("hoa don co trang thai khac 0");
            }
        } else {
            return ResponseEntity.ok("id khong ton tai");
        }

    }

    @PutMapping("up")
    ResponseEntity<?> up(@RequestParam("id") Long id) {
        if (hoaDonChiTietRepository.findById(id).isPresent()) {
            HoaDonChiTiet hoaDonChiTiet = this.hoaDonChiTietRepository.findById(id).get();
            if (hoaDonChiTiet.getSanPhamChiTiet().getSoLuong() - 1 >= 0) {
                this.sanPhamChiTietRepository.updateSoLuongById(hoaDonChiTiet.getSanPhamChiTiet().getId(), -1);
                hoaDonChiTiet.setSoLuong(hoaDonChiTiet.getSoLuong()+1);
                hoaDonChiTietRepository.save(hoaDonChiTiet);
                return ResponseEntity.ok("thanh cong");
            } else {
                return ResponseEntity.ok("Số lượng sản phẩm không đủ");
            }
        } else {
            return ResponseEntity.ok("id khong ton tai");
        }

    } @PutMapping("down")
    ResponseEntity<?> down(@RequestParam("id") Long id) {
        if (hoaDonChiTietRepository.findById(id).isPresent()) {
            HoaDonChiTiet hoaDonChiTiet = this.hoaDonChiTietRepository.findById(id).get();
            if (hoaDonChiTiet.getSoLuong() - 1 >= 0) {
                this.sanPhamChiTietRepository.updateSoLuongById(hoaDonChiTiet.getSanPhamChiTiet().getId(), 1);
                hoaDonChiTiet.setSoLuong(hoaDonChiTiet.getSoLuong()-1);
                hoaDonChiTietRepository.save(hoaDonChiTiet);
                return ResponseEntity.ok("thanh cong");
            } else {
                return ResponseEntity.ok("Số lượng sản phẩm không đủ");
            }
        } else {
            return ResponseEntity.ok("id khong ton tai");
        }

    }

}