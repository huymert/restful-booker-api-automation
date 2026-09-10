# Restful-Booker API Automation Framework

> **Module 2: API Automation Testing - Capstone Project (Team 2 - Rikkei Edu)**

---

## 👥 Thành viên phụ trách
* **Lê Thị Hải Hà**: Cấu hình Base API Rest Assured, Auth Token (`/auth`), Tạo mới booking (`POST`), Lấy thông tin chi tiết (`GET`), Request Chaining.
* **Chi**: Cập nhật booking (`PUT/PATCH`), Xóa booking (`DELETE`), Negative Scenarios, Cấu hình GitLab CI/CD & Allure Report.
* **Mentor / Product Owner**: Thầy Vũ Lê Minh

---

## 🌐 Hệ thống API kiểm thử
* **Hệ thống API**: Restful-Booker
* **Base URL**: https://restful-booker.herokuapp.com
* **Tài liệu API**: https://restful-booker.herokuapp.com/apidoc
* **Công nghệ**: Java 17, Rest Assured, TestNG, JSON Schema Validator, Maven, Allure Report, GitLab CI.

---

## 📋 8 Test Scenarios API (Restful-Booker)
1. `[API-SC-01]` **[High]** `POST /auth`: Tạo Auth Token thành công với credentials hợp lệ.
2. `[API-SC-02]` **[Low]** `POST /auth`: Tạo token thất bại khi sai credentials (Negative test).
3. `[API-SC-03]` **[High]** `POST /booking`: Tạo mới booking phòng thành công, nhận về `bookingid`.
4. `[API-SC-04]` **[High]** `GET /booking/{id}`: Lấy chi tiết booking theo ID vừa tạo, validate schema.
5. `[API-SC-05]` **[High]** `PUT /booking/{id}`: Cập nhật thông tin booking (yêu cầu Auth Token).
6. `[API-SC-06]` **[Medium]** `PATCH /booking/{id}`: Cập nhật một phần thông tin booking (chỉ đổi firstname).
7. `[API-SC-07]` **[High]** `DELETE /booking/{id}`: Xóa booking (yêu cầu Auth Token) trả về HTTP 201 Created.
8. `[API-SC-08]` **[Medium]** `GET /booking/{id_đã_xóa}`: Kiểm tra trả về HTTP 404 Not Found (Negative test).

---

## 🌿 Git Workflow
* **Nhánh chính**: `main` (Production/Demo), `develop` (Tích hợp phát triển).
* **Nhánh tính năng**: `feature/api-auth-ha`, `feature/api-crud-chi`.

---

## 🛠️ Hướng dẫn chạy Test
```bash
# Chạy test API
mvn clean test

# Xem Allure Report
mvn allure:serve
```
