# Quiz App — Kotlin Native Android Application

Aplikasi kuis Android modern dan responsif yang dibangun menggunakan **Kotlin**, **Jetpack Compose (Material 3)**, **Clean Architecture**, **MVVM / MVI State Flow**, dan **Hilt Dependency Injection**. Dibuat untuk memenuhi seluruh kriteria technical test Android Developer di **PT. Tigace Inspirasi Indonesia**.

[![Kotlin](https://img.shields.io/badge/Kotlin-2.0.21-purple.svg)](https://kotlinlang.org)
[![AGP](https://img.shields.io/badge/AGP-8.7.2-green.svg)](https://developer.android.com/studio/releases/gradle-plugin)
[![Compose](https://img.shields.io/badge/Jetpack%20Compose-Material%203-blue.svg)](https://developer.android.com/jetpack/compose)
[![Tests](https://img.shields.io/badge/Unit%20Tests-27%20Passed-success.svg)](https://developer.android.com/training/testing)

---

## Daftar Isi
1. [Deskripsi & Fitur Utama](#1-deskripsi--fitur-utama)
2. [Cara Menjalankan Project](#2-cara-menjalankan-project)
3. [Arsitektur & Struktur Project](#3-arsitektur--struktur-project)
4. [UI Framework & Pola Presentasi](#4-ui-framework--pola-presentasi)
5. [Cara Kerja Question Generator](#5-cara-kerja-question-generator)
6. [Diferensiasi Tingkat Kesulitan (Difficulty)](#6-diferensiasi-tingkat-kesulitan-difficulty)
7. [Library & Rationale Keputusan Teknis](#7-library--rationale-keputusan-teknis)
8. [Automated Unit Testing](#8-automated-unit-testing)

---

## 1. Deskripsi & Fitur Utama

Aplikasi Quiz App menyajikan alur permainan kuis trivia yang interaktif, bersih, dan bebas dari kompleksitas berlebih (*avoids over-engineering*).

### Alur Aplikasi:
1. **Home Screen**: Menampilkan judul aplikasi, ringkasan rekor skor terbaik per tingkat kesulitan, dan tombol untuk memulai kuis.
2. **Select Difficulty Screen**: Pengguna memilih tingkat kesulitan yang diinginkan (Easy, Medium, atau Hard).
3. **Quiz Screen**: Sesi kuis terdiri dari 10 pertanyaan acak dengan batas waktu 15 detik per pertanyaan, dilengkapi HUD informasi nomor soal, progress bar, dan skor langsung.
4. **Result Screen**: Menampilkan hasil evaluasi akhir (skor, jumlah benar/salah, persentase akurasi), status rekor baru, serta tombol untuk bermain kembali (*Main Lagi*) atau kembali ke halaman utama (*Kembali ke Beranda*).

### Fitur Kunci:
- **3 Tingkat Kesulitan**: Easy, Medium, dan Hard dengan materi soal bergradasi.
- **Sesi 10 Pertanyaan Acak**: Setiap sesi mengambil 10 soal secara acak dari curated question bank, dengan opsi jawaban (A, B, C, D) yang ikut diacak secara dinamis tanpa mengubah validitas kunci jawaban.
- **Countdown Timer 15 Detik**: Setiap pertanyaan memiliki batas waktu 15 detik. Jika waktu habis, sistem otomatis menandai salah dan beralih ke soal berikutnya.
- **Live HUD (Heads-Up Display)**: Nomor soal (X/10), linear progress bar indikator, skor langsung (+10 per jawaban benar), dan visual timer dengan peringatan warna saat waktu tersisa <= 5 detik.
- **Immediate Answer Feedback**: Animasi transisi 600ms dengan visual highlight saat user memilih opsi untuk memberikan umpan balik instan sebelum lanjut ke soal berikutnya.
- **Penyimpanan Best Score Lokal**: Rekor skor tertinggi per tingkat kesulitan disimpan secara lokal di perangkat menggunakan Android SharedPreferences dan bertahan saat aplikasi ditutup/dibuka kembali.
- **Result & Replay Flow**: Layar evaluasi lengkap menampilkan skor akhir, jumlah benar/salah, persentase akurasi, status Rekor Baru jika melampaui rekor lama, serta tombol Main Lagi (restart level dengan soal baru) dan Kembali ke Beranda (update rekor di halaman utama).

---

## 2. Cara Menjalankan Project

### Prasyarat
- **JDK**: Versi 17 atau lebih tinggi (JDK 17 / 21 direkomendasikan).
- **Android SDK**: `compileSdk = 35`, `minSdk = 24`, `targetSdk = 35`.
- **Android Studio**: Android Studio Ladybug (2024.2.1) atau Koala (2024.1.1) ke atas.

### A. Menjalankan via Android Studio
1. Clone project ini lalu buka menggunakan Android Studio.
2. Tunggu proses **Gradle Sync** selesai.
3. Di panel **Build Variants** (kiri bawah), pastikan varian `devDebug` aktif (secara default aktif).
4. Pilih emulator Android atau perangkat fisik (USB Debugging aktif).
5. Klik tombol **Run** (atau tekan `Shift + F10`).

### B. Menjalankan via Terminal / Command Line (Gradle Wrapper)
Buka terminal di root project:

* **Menjalankan Seluruh Unit Test (27 Tests)**:
  ```bash
  # Linux / macOS / Git Bash
  ./gradlew testDevDebugUnitTest

  # Windows (CMD / PowerShell)
  .\gradlew.bat testDevDebugUnitTest
  ```

* **Build APK Debug**:
  ```bash
  ./gradlew assembleDevDebug
  ```
  File APK hasil build berada di:
  ```text
  app/build/outputs/apk/dev/debug/app-dev-debug.apk
  ```

* **Install Langsung ke Device/Emulator**:
  ```bash
  ./gradlew installDevDebug
  ```

---

## 3. Arsitektur & Struktur Project

Project dibangun menggunakan prinsip **Clean Architecture** dan pola **MVVM (Model-View-ViewModel)**. Struktur kode dipisahkan secara tegas agar bisnis logic independen dari framework UI maupun framework persistensi.

### Pembagian Layer:
1. **UI Layer (Presentation)**:
   - **Screen & Route**: Menggunakan Jetpack Compose (Material 3). Memisahkan Stateful Route (injeksi ViewModel & observasi lifecycle) dan Stateless Screen (murni rendering UI & event callback).
   - **ViewModel**: Mengelola UI state secara reaktif via StateFlow dan menangani countdown timer 15 detik menggunakan coroutine viewModelScope.
2. **Domain Layer**:
   - **Model**: Entitas data murni seperti Question, Difficulty, BestScores, dan QuizSummary.
   - **Repository Implementation**: QuestionRepositoryImpl (algoritma pengacakan soal dan opsi) dan ScoreRepositoryImpl (logika evaluasi rekor baru).
3. **Data Layer**:
   - **Repository Interfaces**: Mendefinisikan kontrak abstraksi QuestionRepository dan ScoreRepository.
   - **Data Sources**: QuestionLocalDataSource (bank soal in-memory) dan ScorePreferences (persistensi rekor menggunakan native Android SharedPreferences).

### Struktur Direktori Project
```text
app/src/main/java/com/example/kotlintemplate/
├── core/
│   └── common/
│       └── UiState.kt                         # Generic State Wrapper
├── data/
│   ├── datasource/
│   │   ├── QuestionLocalDataSource.kt         # In-memory bank soal (48+ soal terkurasi)
│   │   └── ScorePreferences.kt                # SharedPreferences Best Score persistensi
│   └── repository/
│       ├── QuestionRepository.kt              # Interface kontrak soal
│       └── ScoreRepository.kt                 # Interface kontrak skor
├── domain/                                    # Model & Repository Implementation Saja
│   ├── model/
│   │   ├── BestScores.kt                      # Data class agregat rekor 3 difficulty
│   │   ├── Difficulty.kt                      # Enum EASY, MEDIUM, HARD
│   │   ├── Question.kt                        # Entity Question & Options
│   │   └── QuizSummary.kt                     # Entity Quiz Result Summary
│   └── repository/
│       ├── QuestionRepositoryImpl.kt          # Shuffling algorithm & question selector
│       └── ScoreRepositoryImpl.kt             # Adapter persistensi skor
├── di/
│   ├── RepositoryModule.kt                    # Hilt binding interface -> implementation
│   └── StorageModule.kt                       # Hilt provide SharedPreferences
├── ui/
│   ├── feature/
│   │   ├── home/                              # Home feature (Contract, Screen, Route, VM)
│   │   ├── difficulty/                        # Difficulty feature (Contract, Screen, Route, VM)
│   │   ├── quiz/                              # Quiz feature (Contract, Screen, Route, VM)
│   │   └── result/                            # Result feature (Contract, Screen, Route, VM)
│   ├── navigation/
│   │   ├── Routes.kt                          # Route definitions & parameter helpers
│   │   └── AppNavGraph.kt                     # Jetpack Navigation Compose graph
│   └── theme/                                 # Material 3 Color, Type, Theme
└── MainActivity.kt                            # Single Activity entry point
```

---

## 4. UI Framework & Pola Presentasi

Aplikasi menggunakan **Jetpack Compose** dengan desain **Material 3**. 

### Pemisahan Pola: Stateful Route vs Stateless Screen
Setiap fitur di dalam modul UI memisahkan komponen Stateful dan Stateless secara konsisten:
1. **Stateless Screen** (`*Screen.kt`):
   - Menerima state data class murni (misal: `state: QuizUiState`) dan lambda event callback (misal: `onOptionSelected: (Int) -> Unit`).
   - Tidak memanggil `ViewModel` atau dependency injection framework.
   - 100% aman untuk Compose `@Preview` dan memudahkan pengujian UI secara terisolasi.
2. **Stateful Route** (`*Route.kt`):
   - Menginjeksi ViewModel via `hiltViewModel()`.
   - Mengumpulkan data stream secara lifecycle-aware via `collectAsStateWithLifecycle()`.
   - Menghubungkan interaksi navigasi (`onNavigateToQuiz`, `onBackClick`).

### Unidirectional Data Flow (UDF)
- User melakukan aksi klik -> Route meneruskan ke ViewModel via public method.
- ViewModel memperbarui `MutableStateFlow<UiState>`.
- UI mengamati immutable `StateFlow<UiState>` dan melakukan recomposition secara efisien hanya pada komponen yang nilainya berubah.

---

## 5. Cara Kerja Question Generator

Question generator dirancang untuk memberikan variasi soal yang selalu dinamis pada setiap sesi permainan tanpa terjadi bias urutan jawaban.

### Tahapan Algoritma:
1. **Filtering**: Mengambil seluruh bank soal dari QuestionLocalDataSource (total 48 butir) lalu memfilter soal yang sesuai dengan Difficulty yang dipilih (Easy, Medium, atau Hard).
2. **Pool Shuffling**: Mengacak urutan koleksi soal yang telah difilter menggunakan fungsi .shuffled().
3. **Selection**: Mengambil tepat 10 butir soal pertama menggunakan fungsi .take(10).
4. **Mekanisme Zero Index Drift (Per Soal)**:
   - Mencatat teks jawaban benar asli berdasarkan indeks awal: correctAnswerText = options[correctAnswerIndex].
   - Mengacak urutan 4 pilihan jawaban: shuffledOptions = options.shuffled().
   - Menghitung ulang indeks baru dari teks jawaban benar: newCorrectIndex = shuffledOptions.indexOf(correctAnswerText).
   - Membuat salinan objek pertanyaan baru: question.copy(options = shuffledOptions, correctAnswerIndex = newCorrectIndex).

### Implementasi Kode (`QuestionRepositoryImpl.kt`):
```kotlin
override fun getQuestions(difficulty: Difficulty, count: Int): List<Question> {
    val filtered = dataSource.getAllQuestions().filter { it.difficulty == difficulty }
    val selected = filtered.shuffled().take(count)

    return selected.map { question ->
        val correctAnswerText = question.options[question.correctAnswerIndex]
        val shuffledOptions = question.options.shuffled()
        val newCorrectIndex = shuffledOptions.indexOf(correctAnswerText)

        question.copy(
            options = shuffledOptions,
            correctAnswerIndex = newCorrectIndex
        )
    }
}
```

### Keunggulan Mekanisme Ini:
1. **Zero Index Drift**: Kunci jawaban tetap 100% valid dan sinkron walaupun urutan pilihan A, B, C, D diacak total pada setiap kali soal ditampilkan.
2. **High Reusability & Variety**: Bank soal menyediakan 16 soal unik per kategori, menghasilkan ribuan kombinasi susunan soal berbeda untuk setiap sesi kuis.

---

## 6. Diferensiasi Tingkat Kesulitan (Difficulty)

Tingkat kesulitan dibedakan secara konten kurasi domain pertanyaan yang mencakup **Pengetahuan Umum, Sains, dan Sejarah Dunia**:

| Tingkat Kesulitan | Karakteristik Soal | Domain Topik | Contoh Pertanyaan |
| :--- | :--- | :--- | :--- |
| **Easy** | Fakta populer & pengetahuan dasar sehari-hari | Geografi dasar, biologi dasar, tata surya, keajaiban dunia | *"Which planet is known as the Red Planet?"*<br>*(Earth, Mars, Jupiter, Venus)* |
| **Medium** | Pengetahuan sains & peristiwa sejarah penting dunia | Organela sel, atmosfer, sejarah modern (PD II, Perang Dingin), penemuan antibiotik | *"In which year did World War II officially end?"*<br>*(1943, 1944, 1945, 1948)* |
| **Hard** | Sains lanjutan, sejarah peradaban kuno, dan astronomi mendalam | Fisika partikel (Higgs boson), peradaban kuno (Dinasti Qin, Babilonia), traktat bersejarah (Westphalia 1648, Tordesillas 1494), astrofisika | *"Which fundamental subatomic particle was confirmed in 2012 by experiments at CERN's Large Hadron Collider?"*<br>*(Tau neutrino, Top quark, Higgs boson, Gluon)* |

---

## 7. Library & Rationale Keputusan Teknis

Pengembangan mengadopsi prinsip **Ponytail (Senior Pragmatism & YAGNI - You Aren't Gonna Need It)**: memilih teknologi paling efektif untuk menyelesaikan masalah tanpa menambah overhead arsitektural yang tidak dibutuhkan.

| Library / Tool | Versi | Peran | Alasan Pemilihan & Trade-off |
| :--- | :--- | :--- | :--- |
| **Hilt (Dagger)** | `2.51.1` | Dependency Injection | Standar industri Android untuk decoupled architecture. Memudahkan mocking dan dependency injection pada unit test ViewModel dan UseCase. |
| **Jetpack Compose + Material 3** | BOM `2024.04.01` | Deklaratif UI Framework | Menghilangkan kompleksitas XML layout, ViewBinding, dan fragment lifecycle. Render UI reaktif, deklaratif, dan modular. |
| **Navigation Compose** | `2.8.4` | In-app Routing & Backstack | Navigasi deklaratif single-activity. Memudahkan transmisi argumen navigasi (`difficulty`, `score`, `correctCount`) dan pengelolaan backstack (`popUpTo`). |
| **Kotlin Coroutines & Flow** | `1.9.0` | Asynchronous & State Management | Native Kotlin concurrency. Digunakan untuk countdown timer (looping `delay(1000)` di `viewModelScope`) yang otomatis dibatalkan saat ViewModel di-*clear*, mencegah memory leak. |
| **Android SharedPreferences** | Native SDK | Local Storage Best Score | **Pragmatisme YAGNI**: Untuk kebutuhan menyimpan 3 integer rekor skor (`easy`, `medium`, `hard`), `SharedPreferences` bawaan Android SDK memiliki nol overhead SQLite/Room database, nol schema migration complexity, dan performa I/O instan. |
| **JUnit 4 & Coroutines Test** | `4.13.2` / `1.9.0` | Unit Testing Framework | Cepat, handal, dan dapat dijalankan langsung di JVM tanpa memerlukan emulator Android yang berat. |

---

## 8. Automated Unit Testing

Seluruh logika bisnis inti, repositori data, perhitungan timer, algoritma acak, dan pengelolaan skor diuji secara otomatis menggunakan unit test tanpa ketergantungan pada UI rendering.

### Ringkasan Hasil Pengujian (100% Pass Rate):
```text
BUILD SUCCESSFUL in 41s
31 actionable tasks: 31 executed
27 tests, 0 failures, 100% successful
```

Total **27 Unit Tests** di seluruh lapisan aplikasi:

| Test Suite Class | Tests | Fokus yang Diuji |
| :--- | :---: | :--- |
| `QuestionRepositoryTest` | 5 | Pemfilteran difficulty, batasan tepat 10 butir, pengacakan soal, dan sinkronisasi indeks jawaban benar pasca-shuffle pilihan |
| `ScoreRepositoryTest` | 3 | Logika penyimpanan skor baru jika lebih tinggi, pengabaian skor lebih rendah, dan pembacaan rekor 3 difficulty |
| `QuizViewModelTest` | 5 | Inisialisasi & timer loop, skor bertambah saat jawaban benar, skor tidak bertambah saat salah, transisi timeout 15 detik, dan penyelesaian soal ke-10 |
| `ResultViewModelTest` | 3 | Deteksi rekor baru (`isNewBestScore`), penanganan skor sama/lebih rendah, dan parsing navigation arguments |
| `SelectDifficultyViewModelTest`| 2 | Inisialisasi data rekor dan fungsi refresh state |
| `HomeViewModelTest` | 2 | Pengambilan best score saat Home terbuka dan pembaruan state reaktif |
| `DifficultyTest` | 5 | Mapping enum `fromKey()` dengan safe fallback ke `EASY`, display name, dan key integrity |
| `RoutesTest` | 1 | Format pola URL route navigasi dan helper generator parameter |
| `ExampleUnitTest` | 1 | Sanity check environment |
