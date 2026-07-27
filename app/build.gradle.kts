plugins {
    alias(libs.plugins.android.application)
    // KRİTİK: Buradaki yorum satırını kaldırdık ve aktif ettik!
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.yemekproje"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.yemekproje"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

// Türkçe karakter desteği için derleme ayarları
tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.recyclerview:recyclerview:1.3.2")

    // --- FIREBASE ENTEGRASYONU ---
    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.google.firebase:firebase-storage") // Fotoğraf yükleme için ŞART
    
    // Resim yükleme ve gösterme için Glide kütüphanesi
    implementation("com.github.bumptech.glide:glide:4.16.0")
}