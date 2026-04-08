============================================================
DATABASE SETUP SCRIPT - JAVAPERSISTENCE
============================================================

1. (Opsional) Hapus database lama
------------------------------------------------------------
DROP DATABASE IF EXISTS latihan;


2. Buat database
------------------------------------------------------------
CREATE DATABASE IF NOT EXISTS latihan;


3. Gunakan database
------------------------------------------------------------
USE latihan;


4. Hapus tabel lama (biar fresh)
------------------------------------------------------------
DROP TABLE IF EXISTS karyawan;


5. Buat tabel (SUDAH FIX)
------------------------------------------------------------
CREATE TABLE karyawan (
    nip VARCHAR(10) PRIMARY KEY COMMENT 'Nomor Induk Pegawai',
    nm_kar VARCHAR(100) NOT NULL COMMENT 'Nama Karyawan',
    tem_lhr VARCHAR(50) NOT NULL COMMENT 'Tempat Lahir',
    tgl_lhr DATE NOT NULL COMMENT 'Tanggal Lahir',
    jabatan VARCHAR(50) NOT NULL COMMENT 'Jabatan'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


6. Insert data
------------------------------------------------------------
INSERT INTO karyawan (nip, nm_kar, tem_lhr, tgl_lhr, jabatan) VALUES
('K001', 'Budi Santoso', 'Jakarta', '1990-05-15', 'Manager'),
('K002', 'Siti Rahayu', 'Bandung', '1992-08-22', 'Staff IT'),
('K003', 'Ahmad Wijaya', 'Surabaya', '1988-03-10', 'Staff HRD');


7. Cek struktur tabel
------------------------------------------------------------
DESCRIBE karyawan;


8. Lihat isi data
------------------------------------------------------------
SELECT * FROM karyawan;


9. Hitung jumlah data
------------------------------------------------------------
SELECT COUNT(*) AS total_records FROM karyawan;


============================================================
CATATAN PENTING
============================================================
- Format DATE wajib: 'YYYY-MM-DD'
- Jangan pakai ';;' cukup ';'
- Selalu jalankan: USE latihan; sebelum query

