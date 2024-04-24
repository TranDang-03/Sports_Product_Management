package com.example.endgame.service.serviceImpl;


import com.example.endgame.entity.TaiKhoan;
import com.example.endgame.repository.TaiKhoanRepository;
import com.example.endgame.service.TaiKhoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaiKhoanServiceImpl implements TaiKhoanService {
    @Autowired
    private TaiKhoanRepository taiKhoanRepo;

    @Override
    public Optional<TaiKhoan> findById(String s) {
        return taiKhoanRepo.findById(s);
    }

    @Override
    public boolean checkChucVu(List<Integer> values, String id) {
        int chucVu = this.taiKhoanRepo.findChucVuById(id);
        if (values.contains(chucVu)) {
            return true;
        } else {
            return false;
        }
    }
}
