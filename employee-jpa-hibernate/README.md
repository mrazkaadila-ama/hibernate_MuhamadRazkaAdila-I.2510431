# HERLAN ALAN SUHERMAN_I.2510319
- credit : [aliimndev](https://github.com/aliimndev)

# JavaPersistence - Employee Management Application

Aplikasi desktop CRUD (Create, Read, Update, Delete) untuk manajemen data karyawan menggunakan **Java 21 LTS**, **Hibernate ORM**, dan **MySQL Database**.
## 🧮 hasil
<img width="1920" height="1080" alt="Screenshot 2026-04-07 113955" src="https://github.com/user-attachments/assets/5ef57484-433f-4e26-bcf3-23a51675e2a2" />

## 🔧 Prerequisites

Sebelum menjalankan aplikasi, pastikan sudah install:

| Komponen | Versi | 
|----------|-------|
| Java JDK | 21 LTS |
| Maven | 3.9.x |
| MySQL Server | 8.0+ |

```bash
# Verifikasi instalasi
java -version
mvn -version
mysql --version

# Start MySQL (Linux)
sudo systemctl start mysql
```

---

## 📊 Database Setup

### 1. Login ke MySQL
```bash
mysql -u root -p
```

### 2. Create Database & Table
```sql
CREATE DATABASE IF NOT EXISTS latihan;
USE latihan;

CREATE TABLE karyawan (
    nip VARCHAR(10) PRIMARY KEY,
    nm_kar VARCHAR(100) NOT NULL,
    tem_lhr VARCHAR(50) NOT NULL,
    tgl_lhr DATE NOT NULL,
    jabatan VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO karyawan VALUES
('K001', 'Budi Santoso', 'Jakarta', '1990-05-15', 'Manager'),
('K002', 'Siti Rahayu', 'Bandung', '1992-08-22', 'Staff IT'),
('K003', 'Ahmad Wijaya', 'Surabaya', '1988-03-10', 'Staff HRD');
```

**Atau gunakan script siap pakai:**
```bash
mysql -u root -p latihan < setup-database.sql
```

---

## ⚙️ Configuration

Edit file: `src/main/resources/META-INF/persistence.xml`

```xml
<property name="jakarta.persistence.jdbc.url" 
          value="jdbc:mysql://localhost:3306/latihan"/>
<property name="jakarta.persistence.jdbc.user" value="root"/>
<property name="jakarta.persistence.jdbc.password" value="YOUR_PASSWORD"/>
<property name="jakarta.persistence.jdbc.driver" 
          value="com.mysql.cj.jdbc.Driver"/>
```

⚠️ **Ganti `YOUR_PASSWORD` dengan password MySQL Anda!**

---

## 🚀 How to Run

```bash
# Build & Compile
mvn clean compile

# Run Application
mvn exec:java
```

Aplikasi akan launch dengan GUI window.

---

## 📱 Features & Usage

| Operasi | Langkah |
|---------|---------|
| **CREATE** | Isi form → Click ➕ INSERT |
| **READ** | Auto-load saat start, double-click tabel untuk load ke form |
| **SEARCH** | Ketik NIP → Press ENTER |
| **UPDATE** | Load data → Edit field → Click ✏️ UPDATE |
| **DELETE** | Load data → Click 🗑️ DELETE → Confirm |
| **REFRESH** | Click 🔄 REFRESH untuk reload semua data |
| **CLEAR** | Click ❌ CLEAR untuk clear form |

---

## 📁 Project Structure

```
JavaPersistence/
├── src/main/java/javapersistence/
│   ├── Main.java                 # Entry point
│   ├── FormKaryawan.java         # UI Form & Table
│   ├── ControllerKaryawan.java   # Database Access
│   └── Karyawan.java             # Entity Model
├── src/main/resources/META-INF/
│   └── persistence.xml           # Hibernate Config
├── src/test/java/javapersistence/
│   └── AppTest.java              # Unit Tests
├── pom.xml                       # Maven Dependencies
└── setup-database.sql            # Database Script
```

---

## 🐛 Troubleshooting

| Error | Solusi |
|-------|--------|
| "Communications link failure" | `sudo systemctl start mysql` |
| "Unknown database 'latihan'" | Jalankan SQL script dari bagian Database Setup |
| "Data tidak ditemukan" | Pastikan data sudah ada di database |
| "Duplicate entry for PRIMARY KEY" | Gunakan NIP yang unik |
| "Form validation failed" | Pastikan semua field (*) terisi |
| "Cannot locate persistence units" | `mvn clean compile` |

**Untuk bantuan lengkap:** lihat [FIX-INSERT-ERROR.md](FIX-INSERT-ERROR.md)

---

## 📝 Notes

- ✅ **Zero compiler warnings** - Clean build
- ✅ **All tests passing** - 100% test pass rate  
- ✅ **Modern UI** - Professional design dengan JSplitPane & status bar
- ✅ **Proper error handling** - User-friendly dialogs & console logging

**Status:** Educational/Learning Purpose (tidak untuk production)

---

**Happy Coding! 🚀**
