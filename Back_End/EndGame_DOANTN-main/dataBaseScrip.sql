-- drop database end_game  
-- create database end_game
-- use end_game

CREATE TABLE tai_khoan (
  UID CHAR(255) CHARACTER SET UTF8MB4 NOT NULL PRIMARY KEY, -- tạo bằng fire base và lưu vào khi tạo tài khoản thành công 
  email CHAR(255) CHARACTER SET UTF8MB4 NOT NULL, -- Email đã qua xác thực sẽ được lưu vào đây
  sdt CHAR(15) CHARACTER SET UTF8MB4 NOT NULL, -- sđt lưu ở đây 
  trang_thai BIT NOT NULL default 1 ,  -- 1 là mặc định là đang hoạt động, 0 là đang không hoạt động
  chuc_vu int NOT NULL default 1  -- 1 là khách . 0 là admin , 2 là nhân viên
);

CREATE TABLE thanh_toan (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  ten CHAR(255) CHARACTER SET UTF8MB4 NOT NULL,  -- tên loại thanh toán ví dụ momo, COD, chuyển khoản
  trang_thai CHAR(255) CHARACTER SET UTF8MB4 NOT NULL -- để vầy cho nhiều trạng thái 
);

CREATE TABLE kich_thuoc (
  id BIGINT PRIMARY KEY AUTO_INCREMENT, 
  gia_tri decimal(3,1) NOT NULL,  -- đây là size ví dụ size 31 sẽ có gia_tri là 31 để decima để có thể lưu giá trị 31.5 và tối đa là size 999.9
  trang_thai BIT NOT NULL default 1 -- 1 là mặc định là đang hoạt động, 0 là đang không hoạt động
);

CREATE TABLE danh_muc (  -- danh mục sản phẩm như là giày đá bóng giày chạy bộ .v.v.. chủ yếu là  để tìm kiếm và lọc
  id BIGINT PRIMARY KEY AUTO_INCREMENT, 
  ten CHAR(255) CHARACTER SET UTF8MB4 NOT NULL,   -- tên danh mục 
  trang_thai BIT NOT NULL default 1 -- 1 là mặc định là đang hoạt động, 0 là đang không hoạt động
);

CREATE TABLE mau_sac (
  id BIGINT PRIMARY KEY AUTO_INCREMENT, 
  gia_tri CHAR(50) NOT NULL,  -- giá trị ở đây sẽ là mã RGB hoặc HEX để có thể hiển thị lên một màu nhất định 
  ten CHAR(255) CHARACTER SET UTF8MB4 NOT NULL, -- tên màu ví dụ nâu đất, đỏ cam nhưng ở dạng ngôn ngữ người
  trang_thai BIT NOT NULL default 1
);





CREATE TABLE thuong_hieu ( -- thương hiệu của giày ví dụ adidas nike bitit's 
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  ten CHAR(255) CHARACTER SET UTF8MB4 NOT NULL,
  trang_thai BIT NOT NULL
);



CREATE TABLE san_pham (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  ngay_tao DATE NOT NULL, -- bao giờ tạo thì thêm là ngày hiện tại và không bao giờ được thay đổi
  ten_san_pham CHAR(255) CHARACTER SET UTF8MB4 NOT NULL,
  ma_san_pham CHAR(255) CHARACTER SET UTF8MB4  NULL, -- có thể không có để cho các  sản phẩm có mã từ nhà sản xuất nếu có thì tên hiển thị sẽ là tên sản phẩm + mã sản phẩm
  trang_thai INT NOT NULL default 1,	-- để có thể có nhiều trạng thái hơn 
  ngay_cap_nhat DATETIME,  -- bao giờ sửa thì sẽ cập nhật là ngày hiện tại
  thuong_hieu_id BIGINT,
  FOREIGN KEY (thuong_hieu_id) REFERENCES thuong_hieu(id),
  mo_ta TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci 
);

CREATE TABLE san_pham_danh_muc (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  san_pham_id BIGINT,
  danh_muc_id BIGINT,
  FOREIGN KEY (san_pham_id) REFERENCES san_pham(id),
  FOREIGN KEY (danh_muc_id) REFERENCES danh_muc(id)
);

CREATE TABLE san_Pham_chi_tiet ( -- đây là sản phẩm sẽ thêm vào hóa đơn và giỏ hàng
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  so_luong INT NOT NULL default 0, 
  trang_thai INT NOT NULL,
  mau_sac_id BIGINT,
  san_pham_id BIGINT,
  kich_thuoc_id BIGINT,
  gia_nhap DECIMAL(20, 2) NOT NULL , 
  gia_ban DECIMAL(20, 2) NOT NULL, 
  ngay_cap_nhat DATETIME,
  FOREIGN KEY (mau_sac_id) REFERENCES mau_sac(id),
  FOREIGN KEY (san_pham_id) REFERENCES san_pham(id),
  FOREIGN KEY (kich_thuoc_id) REFERENCES kich_thuoc(id)
);

CREATE TABLE hoa_don (
 id BIGINT PRIMARY KEY AUTO_INCREMENT,
 ngay_tao DATETIME NOT NULL,
 ghi_chu CHAR(255) CHARACTER SET UTF8MB4 NULL,
 ten_nguoi_nhan CHAR(70) CHARACTER SET UTF8MB4 NULL,
 ngay_cap_nhat DATETIME ,
 trang_thai INT NOT NULL default 0, -- mặc định là 0 nghĩa là vừa tạo mới sau đó chuyển trạng thái ,1 đã xác nhận,2 là đang giao,3 là giao thành công ,   4 là đã thanh toán , 5 là hoàn tất ,6 hủy (12 h đêm tất cả hóa đơn có trạng thái là 0 sẽ xóa )
 loai_hoa_don Bit Not Null default 1 , -- 0 là offline 1 là online
 dia_chi_cu_the TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
 phi_van_chuyen DECIMAL(20, 2) NULL default 0, 
 sdt_nhan_hang CHAR(15) CHARACTER SET UTF8MB4  NULL,
 tai_khoan_id CHAR(255) CHARACTER SET UTF8MB4,

FOREIGN KEY (tai_khoan_id) REFERENCES tai_khoan(UID)

);

CREATE TABLE san_pham_trong_gio_hang (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  ngay_tao DATE NOT NULL,
  trang_thai INT NOT NULL,
  tai_khoan_id CHAR(255) CHARACTER SET UTF8MB4 NOT NULL,
	san_pham_chi_tiet_id BIGINT NOT NULL,
  so_luong INT NOT NULL,
FOREIGN KEY (san_pham_chi_tiet_id) REFERENCES san_pham_chi_tiet(id),
  FOREIGN KEY (tai_khoan_id) REFERENCES tai_khoan(UID)
);

CREATE TABLE hoa_don_chi_tiet (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  ghi_chu CHAR(255) CHARACTER SET UTF8MB4,
  so_luong INT NOT NULL,
  hoa_don_id BIGINT,
  gia_san_Pham DECIMAL(20, 2) NOT NULL ,  -- tránh việc sau này giá cả của sản phẩm ở bảng  sản phẩm thay đổi thì giá tiền trong hóa đơn thay đổi 
  san_pham_chi_tiet_id BIGINT,
  FOREIGN KEY (hoa_don_id) REFERENCES hoa_don(id),
  FOREIGN KEY (san_pham_chi_tiet_id) REFERENCES san_pham_chi_tiet(id)
);




Create table thanh_toan_hoa_don(
 id BIGINT PRIMARY KEY AUTO_INCREMENT,
  hoa_don_id BIGINT ,
  thanh_toan_id BIGINT,
  so_tien  DECIMAL(20, 2) NOT NULL,
 FOREIGN KEY (hoa_don_id) REFERENCES hoa_don(id),
 FOREIGN KEY ( thanh_toan_id) REFERENCES  thanh_toan(id)
);



CREATE TABLE khuyen_mai (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  ten VARCHAR(255) NOT NULL,
  ma VARCHAR(255) NOT NULL,
  mo_ta VARCHAR(255) CHARACTER SET UTF8MB4,
  hinh_anh TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  bat_dau DATE NOT NULL,
  ket_thuc DATE NOT NULL,
  giam_gia_tru_thang  DECIMAL(20, 2) NOT NULL,
  giam_gia_phan_tram SMALLINT NOT NULL

);

ALTER TABLE khuyen_mai ADD CONSTRAINT price_range CHECK ( giam_gia_phan_tram >= 0 AND  giam_gia_phan_tram <= 100);

CREATE TABLE hoa_don_khuyen_mai (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  hoa_don_id BIGINT,
  khuyen_mai_id BIGINT,
  FOREIGN KEY (hoa_don_id) REFERENCES hoa_don(id),
  FOREIGN KEY (khuyen_mai_id) REFERENCES khuyen_mai(id)
);

-- data để test
-- Chèn dữ liệu mẫu vào bảng tai_khoan

-- Chèn dữ liệu mẫu vào bảng kich_thuoc
INSERT INTO kich_thuoc (gia_tri, trang_thai)
VALUES
    (31.0, 1),
    (35.5, 1),
    (40.0, 1);

-- Chèn dữ liệu mẫu vào bảng danh_muc
INSERT INTO danh_muc (ten, trang_thai)
VALUES
    ('Giày đá bóng', 1),
    ('Giày chạy bộ', 1),
    ('giày thể thao', 1);

-- Chèn dữ liệu mẫu vào bảng mau_sac
INSERT INTO mau_sac (gia_tri, ten, trang_thai)
VALUES
    ('#FF5733', 'Đỏ cam', 1),
    ('#008000', 'Xanh lá', 1),
    ('#0000FF', 'Xanh dương', 1);

-- Chèn dữ liệu mẫu vào bảng thanh_toan
INSERT INTO thanh_toan (ten, trang_thai)
VALUES
    ('Momo'),
    ('COD'),
    ('Chuyển khoản');

-- Chèn dữ liệu mẫu vào bảng thuong_hieu
INSERT INTO thuong_hieu (ten, trang_thai)
VALUES
    ('Adidas', 1),
    ('Nike', 1),
    ('Biti''s', 1);

-- Chèn dữ liệu mẫu vào bảng san_pham

-- Chèn dữ liệu mẫu vào bảng san_Pham_chi_tiet
INSERT INTO san_Pham_chi_tiet (so_luong, trang_thai, mau_sac_id, san_pham_id, kich_thuoc_id, gia_nhap, gia_ban, ngay_cap_nhat)
VALUES
    (100, 1, 1, 1, 1, 5000.0, 100000.0, CURRENT_TIMESTAMP),
    (150, 1, 2, 2, 2, 6000.0, 120000.0, CURRENT_TIMESTAMP),
    (200, 1, 3, 3, 3, 4500.0, 90000.0, CURRENT_TIMESTAMP);

-- Chèn dữ liệu mẫu vào bảng san_pham_danh_muc
INSERT INTO san_pham_danh_muc (san_pham_id, danh_muc_id)
VALUES
    (1, 1),
    (2, 2),
    (3, 3);

ALTER TABLE san_pham
MODIFY COLUMN mo_ta VARCHAR(1000);

ALTER TABLE san_pham
ADD FULLTEXT (ten_san_pham, ma_san_pham,mo_ta);




ALTER TABLE thanh_toan
DROP COLUMN trang_thai;


ALTER TABLE tai_khoan
DROP COLUMN sdt;

ALTER TABLE san_pham_trong_gio_hang 
DROP COLUMN trang_thai;


ALTER TABLE khuyen_mai
DROP COLUMN hinh_anh;

ALTER TABLE tai_khoan
ADD COLUMN ten CHAR(255) CHARACTER SET UTF8MB4;

INSERT INTO san_pham (ngay_tao, ten_san_pham, trang_thai,ma_san_pham, thuong_hieu_id, mo_ta)
VALUES
    (CURRENT_DATE, 'Adidas balls1', 1, 1,1, 'Giày đá bóng chuyên nghiệp'),
    (CURRENT_DATE, 'Nike RUnner', 1, 2, 2,'Giày chạy bộ thoải mái'),
    (CURRENT_DATE, 'Biti''s hunter', 1, 3,3, 'Thể thao thời trang');
    
    
INSERT INTO tai_khoan (UID, email, sdt, trang_thai, chuc_vu,ten)
VALUES
    ('EfuHkOuiVGSs63Cj5r6IBLcxL2s2', 'thucthuc44axy@gmail.com', '1234567890', 1, 1,"duy"),
    ('Yvdb7NYFmlhheRZONCI1m1YVbM42', 'user2@gmail.com', '9876543210', 1, 1,"bach"),
    ('SJxUnFFgkbVrtinPdtuahd7I9rt2', 'admin1@gmail.com', '5555555555', 1, 0,"viet");

SELECT * FROM  san_pham
WHERE MATCH(ten_san_pham, ma_san_pham,mo_ta) AGAINST ('chuyên nghiệp');