package com.example.endgame.bean;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChiTietSanPhamCreate {
    private List<com.example.endgame.entity.SanPhamChiTiet> sanPhamChiTiets;
    private String userId;
}
