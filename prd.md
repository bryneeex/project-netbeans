# PRODUCT REQUIREMENT DOCUMENT (PRD)

**Nama Proyek:** Aplikasi Penggajian Karyawan (Sistem Informasi PBO-PPL)  
**Platform:** Java Desktop (Swing / NetBeans IDE)  
**Database:** Relational Database (MySQL / sejenisnya)  

---

## 1. Ringkasan Produk (Product Summary)
Aplikasi ini adalah sistem informasi manajemen internal untuk mengelola data karyawan, golongan, lembur, serta memproses transaksi penggajian secara digital. Aplikasi dilengkapi dengan hak akses (login) untuk menjaga keamanan data perusahaan.

---

## 2. Alur Pengguna & Fitur Utama (User Features)

### 2.1. Modul Autentikasi (Halaman Login)
* [cite_start]**Komponen UI:** * Input `Username` [cite: 1]
    * [cite_start]Input `Password` [cite: 2]
    * [cite_start]Tombol `Login` [cite: 3] [cite_start]dan `Exit` [cite: 4]
* [cite_start]**Ketentuan Teknis:** Semua tombol wajib menggunakan gambar dengan format `.png`[cite: 6]. [cite_start]Aset gambar dimasukkan melalui klik kanan pada button > *Properties* > tab *Properties* > komponen *icon* > klik titik 3 > *External Image*[cite: 6, 7].

### 2.2. Halaman Menu Utama (Dashboard)
Halaman ini menggunakan *interface* berbasis tombol besar berlatar belakang biru untuk mengarahkan pengguna ke 4 formulir utama:
1. [cite_start]**Form Karyawan:** Manajemen data personal pegawai[cite: 8].
2. [cite_start]**Form Golongan:** Manajemen struktur gaji & tunjangan[cite: 9].
3. [cite_start]**Form Lembur:** Pencatatan jam kerja lembur[cite: 10].
4. [cite_start]**Form Penggajian:** Kalkulasi dan daftar gaji karyawan[cite: 11].

---

## 3. Spesifikasi Fungsional Formulir (Form Specifications)

[cite_start]Setiap formulir manajemen data (Karyawan, Golongan, Lembur, Penggajian) wajib menyediakan tabel (**JTable**) di bagian bawah untuk menampilkan data aktual serta memiliki 5 tombol aksi utama: **Save, Reset, Update, Delete, dan Exit**[cite: 24, 25, 42, 48, 60, 61].

### 3.1. Form Data Karyawan
* [cite_start]**Fungsi:** Mengelola informasi identitas dan profil karyawan[cite: 18].
* **Komponen & Tipe Data Input:**
    * [cite_start]`Id Karyawan` (Text Field / Primary Key) [cite: 12]
    * [cite_start]`Nama` (Text Field) [cite: 13]
    * `Id. Golongan` (**Combo Box** - Berelasi dengan data Golongan) [cite: 19, 28]
    * [cite_start]`Jenis Kelamin` (**Radio Button**: Laki-laki / Perempuan) [cite: 14, 20, 21]
    * [cite_start]`Tempat` (Text Field) [cite: 14]
    * [cite_start]`Tanggal Lahir` (**JDateChooser / JCalendar**) [cite: 15, 29]
    * [cite_start]`Status` (**Radio Button**: Menikah / Tidak Menikah) [cite: 16, 22, 23, 32]
    * [cite_start]`Alamat` (Text Field / Text Area) [cite: 17]

### 3.2. Form Data Golongan
* [cite_start]**Fungsi:** Mengatur skema penggajian dan tunjangan berdasarkan tingkatan golongan[cite: 41].
* **Komponen & Tipe Data Input:**
    * `Id. Golongan` (Text Field / Primary Key) [cite: 33]
    * [cite_start]`Nama Golongan` (Text Field) [cite: 34]
    * [cite_start]`Gaji Pokok` (Number/Currency) [cite: 35]
    * [cite_start]`Tunjangan Istri` (Number/Currency) [cite: 36]
    * [cite_start]`Jumlah Anak` (Number/Integer) [cite: 37]
    * [cite_start]`Tunjangan Anak` (Number/Currency) [cite: 38]
    * [cite_start]`Transport` (Number/Currency) [cite: 39]
    * [cite_start]`Uang Makan` (Number/Currency) [cite: 40]

### 3.3. Form Data Lembur
* [cite_start]**Fungsi:** Mencatat performa dan log lembur karyawan[cite: 47].
* **Komponen & Tipe Data Input:**
    * `Id. Lembur` (Text Field / Primary Key) [cite: 43]
    * [cite_start]`Id Karyawan` (Text Field / Combo Box relasi Karyawan) [cite: 44]
    * [cite_start]`Tanggal Lembur` (JDateChooser / Date) [cite: 45]
    * [cite_start]`Jumlah` (Number/Integer - Menghitung jam lembur) [cite: 46]

### 3.4. Form Daftar Gaji Karyawan (Penggajian)
* [cite_start]**Fungsi:** Memproses kalkulasi seluruh komponen pendapatan menjadi total gaji bersih[cite: 56].
* **Komponen & Tipe Data Input:**
    * `Id. Gaji` (Text Field / Primary Key) [cite: 49]
    * [cite_start]`Tanggal Gaji` (**JDateChooser**) [cite: 50, 63]
    * `Id. [cite_start]Karyawan` (**Combo Box** - Mengambil referensi data Karyawan) [cite: 51, 63]
    * [cite_start]`Nama Karyawan` (Read-only Text Field / Otomatis muncul) [cite: 52]
    * [cite_start]`Golongan` (Read-only Text Field / Otomatis muncul) [cite: 53]
    * [cite_start]`Jumlah Gaji` (Kalkulasi Otomatis / Output) [cite: 54]
    * [cite_start]`Jumlah Lembur` (Output akumulasi lembur dari form lembur) [cite: 55]
    * [cite_start]`Potongan` (Number/Currency - Input manual / bersyarat) [cite: 57]
    * [cite_start]`Total Gaji` (Formula: `Jumlah Gaji` + `Jumlah Lembur` - `Potongan`) [cite: 58]
    * [cite_start]`Tanggal` (Date) [cite: 59]

---

## 4. Aturan Non-Fungsional & Teknis (Technical Requirements)

### 4.1. Manajemen Library Pihak Ketiga (Third-Party Library)
* [cite_start]Proyek ini diwajibkan menggunakan library eksternal `jcalendar.jar` atau ekstraksi dari arsip JCalendar untuk menangani input tanggal[cite: 29, 30].
* **Prosedur Integrasi di NetBeans:**
    1. [cite_start]Klik kanan pada project, pilih **Properties** > **Libraries** > **Compile**, lalu tambahkan file JAR tersebut[cite: 30].
    2. [cite_start]Untuk memunculkan komponen di palet desain: Pilih menu **Tools** > **Palette** > **Swing/AWT Components** > **Add from JAR**[cite: 31].
    3. [cite_start]Pilih file JAR JCalendar, lalu tempatkan komponen **JDateChooser** ke kategori palet yang diinginkan[cite: 31].

### 4.2. Panduan Desain Antarmuka (UI/UX)
* **Warna Tema:** Seluruh jendela formulir menggunakan warna latar belakang biru solid.
* **Validasi Input:** Field numerik (seperti nominal uang atau jumlah anak) tidak boleh menerima input karakter berupa teks/huruf untuk menghindari terjadinya error atau *exception crash* pada program database.