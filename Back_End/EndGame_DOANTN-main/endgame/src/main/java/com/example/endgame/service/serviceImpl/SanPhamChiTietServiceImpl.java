package com.example.endgame.service.serviceImpl;

import com.example.endgame.bean.ChiTietSanPhamCreate;
import com.example.endgame.entity.GioHang;
import com.example.endgame.entity.MauSac;
import com.example.endgame.entity.SanPham;
import com.example.endgame.entity.SanPhamChiTiet;
import com.example.endgame.repository.GioHangRepository;
import com.example.endgame.repository.HoaDonChiTietRepository;
import com.example.endgame.repository.HoaDonRepository;
import com.example.endgame.repository.SanPhamChiTietRepository;
import com.example.endgame.service.SanPhamChiTietService;
import com.example.endgame.service.TaiKhoanService;
import com.example.endgame.until.Constant;
import com.example.endgame.validate.CTSPValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SanPhamChiTietServiceImpl implements SanPhamChiTietService {

    @Autowired
    SanPhamChiTietRepository spctRepo;
    @Autowired
    TaiKhoanService taiKhoanService;
    @Autowired
    CTSPValidate ctspValidate;
    @Autowired
    HoaDonChiTietRepository hoaDonChiTietRepository;
     @Autowired
     GioHangRepository gioHangRepository;


    @Override
    public Optional<SanPhamChiTiet> findById(Long aLong) {
        return spctRepo.findById(aLong);
    }

    @Override
    public boolean existsById(Long aLong) {
        return spctRepo.existsById(aLong);
    }

    @Override
    public ResponseEntity<?> create(ChiTietSanPhamCreate chiTietSanPhamCreate) {
        // Check chuc vu theo user id
        String userId = chiTietSanPhamCreate.getUserId();
        boolean isCheckChucVu = this.taiKhoanService.checkChucVu(Arrays.asList(Constant.CHUC_VU_ADMIN, Constant.CHUC_VU_NHAN_VIEN), userId);
        if (!isCheckChucVu) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Không được phép truy cập");
        }

        // Validate check trùng và check rỗng giá trị
        // Tạo list error
        List<Map<String, Object>> mapErrors = new ArrayList<>();
        for (int i = 0; i < chiTietSanPhamCreate.getSanPhamChiTiets().size(); i++) {
            SanPhamChiTiet spct = chiTietSanPhamCreate.getSanPhamChiTiets().get(i);
            Map<String, Object> error = null;
            // Validate
            error = this.ctspValidate.validateSoLuong(error, String.valueOf(spct.getSoLuong()));
            error = this.ctspValidate.validateBigDecimail(error, String.valueOf(spct.getGiaNhap()), "giaNhap");
            error = this.ctspValidate.validateBigDecimail(error, String.valueOf(spct.getGiaBan()), "giaBan");
            error = this.ctspValidate.validateMauSac(error, spct.getMauSac().getId(), "mauSac.id");
            error = this.ctspValidate.validateSanPham(error, spct.getSanPham().getId(), "sanPham.id");
            error = this.ctspValidate.validateKichThuoc(error, spct.getKichThuoc().getId(), "kichThuoc.id");
            error = this.ctspValidate.validate3khoaPhu(error,spct.getMauSac().getId(), spct.getSanPham().getId(), spct.getKichThuoc().getId(), "MS-SP-KT");


            if (error != null) {
                error.put("index", i);
                mapErrors.add(error);
            }
            // Nếu có lỗi thì add lỗi vào list error của theo phần tử
        }

        // Check list error
        // Nếu có lỗi thì trả về cho FE + status 500
        if (mapErrors.size() > 0) {
            return ResponseEntity.internalServerError().body(mapErrors);
        }

        // Nếu không có lỗi thì save
        return ResponseEntity.ok(spctRepo.saveAll(chiTietSanPhamCreate.getSanPhamChiTiets()));
    }

    @Override
    public ResponseEntity<List<SanPhamChiTiet>> findBySanPhamIdAndTrangThai(Long idSP, Integer trangThai) {
        return ResponseEntity.ok(spctRepo.findBySanPhamIdAndTrangThai(idSP, trangThai));
    }

    @Override
    public ResponseEntity<?> findGiaBanMinMaxByIDSP(Long idSP) {
        Map<String,Double> map = new HashMap<>();
        map.put("min", spctRepo.findGiaBanMinByIDSP(idSP));
        map.put("max", spctRepo.findGiaBanMaxByIDSP(idSP));
        return ResponseEntity.ok(map);
    }


    @Override
    public List<MauSac> findAllMSByIDSP(Long idSP) {
        return spctRepo.findAllMSByIDSP(idSP);
    }


    @Override
    public List<SanPham> findAllSPByIDMS(Long idMS) {
        return spctRepo.findAllSPByIDMS(idMS);
    }


    @Override
    public List<MauSac> findAllSPByIDKT(Long idMS) {
        return spctRepo.findAllSPByIDKT(idMS);
    }

    @Override
    public boolean updateSoLuongById(Long idSP, Integer soluong) {
        return spctRepo.updateSoLuongById(idSP, soluong)>0;
    }

    @Override
    public ResponseEntity<List<SanPhamChiTiet>> findBySanPhamIdAndMauSacId(Long idSP, Long idMS) {
        return ResponseEntity.ok(spctRepo.findBySanPhamIdAndMauSacId(idSP, idMS));
    }

    @Override
    public ResponseEntity<?> update(ChiTietSanPhamCreate chiTietSanPhamCreate) {
        // Check chuc vu theo user id
        String userId = chiTietSanPhamCreate.getUserId();
        boolean isCheckChucVu = this.taiKhoanService.checkChucVu(Arrays.asList(Constant.CHUC_VU_ADMIN, Constant.CHUC_VU_NHAN_VIEN), userId);
        if (!isCheckChucVu) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Không được phép truy cập");
        }

        // Validate check trùng và check rỗng giá trị
        // Tạo list error
        List<Map<String, Object>> mapErrors = new ArrayList<>();
        for (int i = 0; i < chiTietSanPhamCreate.getSanPhamChiTiets().size(); i++) {
            SanPhamChiTiet spct = chiTietSanPhamCreate.getSanPhamChiTiets().get(i);
            Map<String, Object> error = null;
            // Validate
            error = this.ctspValidate.validateSoLuong(error, String.valueOf(spct.getSoLuong()));
            error = this.ctspValidate.validateBigDecimail(error, String.valueOf(spct.getGiaNhap()), "giaNhap");
            error = this.ctspValidate.validateBigDecimail(error, String.valueOf(spct.getGiaBan()), "giaBan");
            error = this.ctspValidate.validateMauSac(error, spct.getMauSac().getId(), "mauSac.id");
            error = this.ctspValidate.validateSanPham(error, spct.getSanPham().getId(), "sanPham.id");
            error = this.ctspValidate.validateKichThuoc(error, spct.getKichThuoc().getId(), "kichThuoc.id");
//            error = this.ctspValidate.validate3khoaPhu(error,spct.getMauSac().getId(), spct.getSanPham().getId(), spct.getKichThuoc().getId(), "MS-SP-KT");


            if (error != null) {
                error.put("index", i);
                mapErrors.add(error);
            }
            // Nếu có lỗi thì add lỗi vào list error của theo phần tử
        }

        // Check list error
        // Nếu có lỗi thì trả về cho FE + status 500
        if (mapErrors.size() > 0) {
            return ResponseEntity.internalServerError().body(mapErrors);
        }

        // Nếu không có lỗi thì save
        return ResponseEntity.ok(spctRepo.saveAll(chiTietSanPhamCreate.getSanPhamChiTiets()));
    }

    @Override
    public void removeById(Long id){
        List<GioHang> gh =this.gioHangRepository.findBySanPhamChiTietId(id);

        if(!gh.isEmpty()){
            for (GioHang g: gh
                 ) {
                this.gioHangRepository.deleteById(g.getId());
            }

        }
        if(!this.hoaDonChiTietRepository.findBySanPhamChiTiet_Id(id).isEmpty()){
            SanPhamChiTiet sanPhamChiTiet = spctRepo.findById(id).get();
            sanPhamChiTiet.setTrangThai(0);
            spctRepo.save(sanPhamChiTiet);
            return;
        }
        spctRepo.deleteById(id);
    }

    @Override
    public ResponseEntity<List<SanPhamChiTiet>> getAll(){
        return  ResponseEntity.ok(spctRepo.findAll());
    }
}
