# 🍲 yemekProje - Android Yemek Tarifi Uygulaması

`yemekProje`, kullanıcıların iştah açıcı yemek tariflerini keşfedebileceği, sevdikleri tarifleri favorilerine ekleyip kendi koleksiyonlarını oluşturabileceği modern bir Android mobil uygulamasıdır.

## 🚀 Öne Çıkan Özellikler

* **🔐 Kullanıcı Kimlik Doğrulaması (Authentication):** Firebase Auth ile güvenli e-posta ve şifre tabanlı kayıt olma ve giriş yapma mimarisi.
* **📱 Dinamik Tarif Listeleme:** `RecyclerView` ve özel `RecipeAdapter` kullanılarak performanslı ve akıcı liste görünümü.
* **❤️ Bulut Tabanlı Favori Sistemi:** Firestore NoSQL veritabanı entegrasyonu ile favoriye alınan tariflerin anlık (real-time) olarak kullanıcı hesabına senkronize edilmesi.
* **🖼️ Kullanıcı Dostu Arayüz (UI/UX):** Krem ve sıcak renk tonlarıyla tasarlanmış, sade ve şık fragment tabanlı navigasyon.

## 🛠️ Kullanılan Teknolojiler ve Kütüphaneler

* **Dil:** Java
* **Geliştirme Ortamı:** Android Studio
* **Arka Plan & Veritabanı:** 
  * Firebase Authentication
  * Cloud Firestore
* **Arayüz Bileşenleri:** Material Design, RecyclerView, Fragments, View Binding / Layout Inflater

## 📂 Proje Mimarisi & Veri Yapısı

Firestore NoSQL veritabanında kullanıcı bazlı favori takibi şu hiyerarşi ile tutulmaktadır:

```text
users (Collection)
 └── {userId} (Document)
      └── favorites (Sub-collection)
           └── {recipeName} (Document)
GİRİŞ EKRANI
<img width="410" height="812" alt="Ekran görüntüsü 2026-07-27 104841" src="https://github.com/user-attachments/assets/1c0facaf-04d3-405f-9dcd-70703d47925f" />
