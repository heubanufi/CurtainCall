package com.example.capstone06;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import androidx.appcompat.app.AppCompatActivity;

public class Comment extends AppCompatActivity {

    private Button buttonClose;
    private Button buttonSubmit;
    private RatingBar ratingBar;
    private EditText editTextComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        buttonClose = findViewById(R.id.buttonClose);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        ratingBar = findViewById(R.id.ratingBar);
        editTextComment = findViewById(R.id.editTextComment);

        // X 버튼 클릭 시 창 닫기
        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // V 버튼 클릭 시 별점과 코멘트 저장 후 창 닫기
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 별점 저장
                float rating = ratingBar.getRating();
                // 코멘트 저장
                String comment = editTextComment.getText().toString();

                // TODO: 파이어베이스에 별점과 코멘트를 저장하는 로직을 추가하세요.

                // 창 닫기
                finish();
            }
        });
    }
}