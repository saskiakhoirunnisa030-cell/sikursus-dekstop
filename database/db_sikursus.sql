-- ============================================================
-- SIKURSUS Desktop - Pertemuan 11 (XAMPP / phpMyAdmin)
-- Hanya menambah tabel users + 2 akun demo.
-- Tabel peserta dan data lama TIDAK disentuh.
-- Cara pakai: phpMyAdmin -> tab SQL -> tempel -> Go.
-- Aman dijalankan berulang.
-- ============================================================

CREATE DATABASE IF NOT EXISTS db_sikursus
  CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE db_sikursus;

CREATE TABLE IF NOT EXISTS users (
  id_user       INT AUTO_INCREMENT PRIMARY KEY,
  username      VARCHAR(50)  NOT NULL UNIQUE,
  password_hash VARCHAR(255) NOT NULL,
  role          ENUM('ADMIN','OPERATOR') NOT NULL DEFAULT 'OPERATOR',
  aktif         BOOLEAN NOT NULL DEFAULT TRUE
);

-- Akun demo (password disimpan sebagai hash PBKDF2, bukan teks biasa)
--   admin    / Admin@123    -> ADMIN
--   operator / Operator@123 -> OPERATOR
INSERT IGNORE INTO users (username, password_hash, role, aktif) VALUES
('admin',
 '120000:cvtXo9svPq4Kn6wGZqQCHg==:PHshCNiLKx2DshS9sWVhxBEEm6q8Rtffch/h3OjlccM=',
 'ADMIN', 1),
('operator',
 '120000:jiTqDEkPqkcuiJ9Tzx90LA==:tSNd5Wu2jalKdy7vc6Nba5QFLm3vPRGsvd8vDW43o4c=',
 'OPERATOR', 1);

-- Cek hasil
SELECT id_user, username, role, aktif FROM users;
