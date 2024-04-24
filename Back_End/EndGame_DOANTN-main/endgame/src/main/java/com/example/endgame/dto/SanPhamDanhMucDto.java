package com.example.endgame.dto;

import com.example.endgame.entity.DanhMuc;
import com.example.endgame.entity.SanPham;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "Brand Dto model information")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class SanPhamDanhMucDto {
    private Long id;

    private SanPham sanPham;

    private DanhMuc danhMuc;

}
