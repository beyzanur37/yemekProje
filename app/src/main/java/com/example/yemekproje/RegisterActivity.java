package com.example.yemekproje;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText regName, regSurname, regEmail, regPassword, regPasswordConfirm;
    private Button btnDoRegister;
    private TextView txtBackToLogin;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // XML bileşenleri bağlanıyor
        regName = findViewById(R.id.regName);
        regSurname = findViewById(R.id.regSurname);
        regEmail = findViewById(R.id.regEmail);
        regPassword = findViewById(R.id.regPassword);
        regPasswordConfirm = findViewById(R.id.regPasswordConfirm);
        btnDoRegister = findViewById(R.id.btnDoRegister);
        txtBackToLogin = findViewById(R.id.txtBackToLogin);

        // Kayıt Ol Butonu Mantığı
        btnDoRegister.setOnClickListener(v -> {
            String name = regName.getText().toString().trim();
            String surname = regSurname.getText().toString().trim();
            String email = regEmail.getText().toString().trim();
            String password = regPassword.getText().toString().trim();
            String confirm = regPasswordConfirm.getText().toString().trim();

            if (name.isEmpty() || surname.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Lütfen tüm alanları doldurun!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(confirm)) {
                Toast.makeText(this, "Şifreler eşleşmiyor!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Firebase'de yeni kullanıcı oluşturma
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            String userId = mAuth.getCurrentUser().getUid();
                            
                            // Kullanıcı bilgilerini Firestore'a kaydetme
                            Map<String, Object> user = new HashMap<>();
                            user.put("name", name);
                            user.put("surname", surname);
                            user.put("email", email);
                            
                            db.collection("users").document(userId)
                                    .set(user)
                                    .addOnSuccessListener(aVoid -> {
                                        Toast.makeText(this, "Kayıt Başarılı! Şimdi Giriş Yapın.", Toast.LENGTH_LONG).show();
                                        finish();
                                    });
                        } else {
                            Toast.makeText(this, "Kayıt Hatası: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        });

        // "Zaten hesabım var" diyorsa Login'e geri dönme
        txtBackToLogin.setOnClickListener(v -> finish());
    }
}