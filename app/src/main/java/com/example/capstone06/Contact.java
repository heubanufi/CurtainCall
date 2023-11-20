package com.example.capstone06;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.capstone06.Fragment.BottomMypage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Contact extends AppCompatActivity {

    private EditText emailEditText;
    private EditText messageEditText;
    private Button saveButton;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        emailEditText = findViewById(R.id.contact_email);
        messageEditText = findViewById(R.id.contact_txt);
        saveButton = findViewById(R.id.contact_check);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveContactData();
            }
        });
    }

    private void saveContactData() {
        String email = emailEditText.getText().toString().trim();
        String message = messageEditText.getText().toString().trim();

        if (email.isEmpty() || message.isEmpty()) {
            // 필수 필드가 비어있는 경우 처리
            return;
        }

        // 현재 로그인한 사용자 가져오기
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            // ContactUS 컬렉션에 데이터 저장
            Map<String, Object> contactData = new HashMap<>();
            contactData.put("email", email);
            contactData.put("message", message);
            contactData.put("user", user.getUid()); // 사용자 UID 또는 다른 사용자 관련 정보를 저장할 수 있습니다.
            contactData.put("time",FieldValue.serverTimestamp());

            // Firestore에 데이터 추가
            db.collection("ContactUS")
                    .add(contactData)
                    .addOnSuccessListener(documentReference -> {
                        // 데이터가 성공적으로 저장된 경우
                        emailEditText.setText("");
                        messageEditText.setText("");

                        // 토스트 메시지 표시
                        Toast.makeText(Contact.this, "전송되었습니다", Toast.LENGTH_SHORT).show();

                        String documentId = documentReference.getId();

                        // 서버 타임스탬프를 추가하여 문서 업데이트
                        db.collection("ContactUS").document(documentId)
                                .update("timestamp", FieldValue.serverTimestamp())
                                .addOnSuccessListener(aVoid -> {
                                    // 타임스탬프 업데이트가 성공한 경우
                                })
                                .addOnFailureListener(e -> {
                                    // 타임스탬프 업데이트 중 오류 발생 시 처리
                                });

                        // BottomMypage.java로 이동
                        Intent intent = new Intent(Contact.this, BottomMypage.class);
                        startActivity(intent);
                    })
                    .addOnFailureListener(e -> {
                        // 데이터 저장 중 오류 발생 시 처리
                    });
        }
    }
}
