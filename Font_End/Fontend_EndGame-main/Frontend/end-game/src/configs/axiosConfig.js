import axios from "axios";

const Token = "55a67c4b2f20fb67598709c539fbaec4fe59819a";
// Tạo một instance Axios với cấu hình cụ thể cho yêu cầu API
const instance = axios.create({
  baseURL:
    "https://cors-anywhere.herokuapp.com/https://services.giaohangtietkiem.vn", // Thay đổi URL của bạn
  headers: {
    "Content-Type": "application/json",
  },
});

instance.defaults.headers.common["Authorization"] = `Bearer ${Token}`;
// Hàm thực hiện yêu cầu GET
export async function fetchData(endpoint) {
  try {
    const response = await instance.get(endpoint);
    return response.data;
  } catch (error) {
    throw error;
  }
}

// Hàm thực hiện yêu cầu POST
export async function postData(endpoint, data) {
  try {
    const response = await instance.post(endpoint, data);
    return response.data;
  } catch (error) {
    throw error;
  }
}

// Thêm các hàm khác cho các yêu cầu khác (PUT, DELETE, v.v.) nếu cần
