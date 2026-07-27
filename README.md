#  yemekProje - Android Yemek Tarifi Uygulaması

`yemekProje`, kullanıcıların iştah açıcı yemek tariflerini keşfedebileceği, sevdikleri tarifleri favorilerine ekleyip kendi koleksiyonlarını oluşturabileceği modern bir Android mobil uygulamasıdır.

##  Öne Çıkan Özellikler

* ** Kullanıcı Kimlik Doğrulaması (Authentication):** Firebase Auth ile güvenli e-posta ve şifre tabanlı kayıt olma ve giriş yapma mimarisi.
* ** Dinamik Tarif Listeleme:** `RecyclerView` ve özel `RecipeAdapter` kullanılarak performanslı ve akıcı liste görünümü.
* ** Bulut Tabanlı Favori Sistemi:** Firestore NoSQL veritabanı entegrasyonu ile favoriye alınan tariflerin anlık (real-time) olarak kullanıcı hesabına senkronize edilmesi.
* ** Kullanıcı Dostu Arayüz (UI/UX):** Krem ve sıcak renk tonlarıyla tasarlanmış, sade ve şık fragment tabanlı navigasyon.

## 🛠️ Kullanılan Teknolojiler ve Kütüphaneler

* **Dil:** Java
* **Geliştirme Ortamı:** Android Studio
* **Arka Plan & Veritabanı:** 
  * Firebase Authentication
  * Cloud Firestore
* **Arayüz Bileşenleri:** Material Design, RecyclerView, Fragments, View Binding / Layout Inflater

  ## UYGULAMA EKRAN GÖRÜNTÜLERİ
### GİRİŞ EKRANI
<img width="410" height="812" alt="Ekran görüntüsü 2026-07-27 104841" src="https://github.com/user-attachments/assets/521a70a0-e4ea-4465-9f04-b55d97287d93" />

### KAYIT EKRANI
<img width="399" height="817" alt="Ekran görüntüsü 2026-07-27 104920" src="https://github.com/user-attachments/assets/a1f49b1e-4f74-4ffe-9b3c-b623e9e42291" />

### MENÜ
<img width="395" height="807" alt="Ekran görüntüsü 2026-07-27 105044" src="https://github.com/user-attachments/assets/82ff808a-7e5b-4245-a9b4-d881aa9266ec" />

### TARİFLER
<img width="443" height="810" alt="Ekran görüntüsü 2026-07-27 105051" src="https://github.com/user-attachments/assets/c0971f18-5948-4619-8d69-5ff84d561861" />

### TARİF İÇERİĞİ
<img width="442" height="811" alt="Ekran görüntüsü 2026-07-27 105058" src="https://github.com/user-attachments/assets/861ddf85-421e-4418-9b43-d9f2349afa59" />

### FAVORİLER
<img width="414" height="815" alt="Ekran görüntüsü 2026-07-27 105116" src="https://github.com/user-attachments/assets/c3e66f32-6ef0-4d37-a2e9-46e9e9b56b10" />

### TARİF EKLEME ALANI 
<img width="437" height="820" alt="Ekran görüntüsü 2026-07-27 105124" src="https://github.com/user-attachments/assets/41b9974d-cdf5-4040-9ceb-1cce1205f62d" />

### PROFİL
<img width="431" height="817" alt="Ekran görüntüsü 2026-07-27 105134" src="https://github.com/user-attachments/assets/b44ce6b0-86b5-48ea-b035-c8ce5e3634a0" />


## Proje Mimarisi & Veri Yapısı

Firestore NoSQL veritabanında kullanıcı bazlı favori takibi şu hiyerarşi ile tutulmaktadır:

```text
users (Collection)
 └── {userId} (Document)
      └── favorites (Sub-collection)
           └── {recipeName} (Document)


