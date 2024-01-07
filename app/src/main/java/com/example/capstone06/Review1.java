package com.example.capstone06;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;

import com.example.capstone06.data.model.Row;


public class Review1 extends AppCompatActivity {

    private ImageButton nextBtn, closeBtn;
    private ReviewViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review1);

        viewModel = new ViewModelProvider(this).get(ReviewViewModel.class);

        Intent intent = getIntent();
        String titleText = intent.getStringExtra("titleText");

        TextView textView = findViewById(R.id.userTitle);
        textView.setText(titleText);


        nextBtn = findViewById(R.id.next);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 사용자 입력을 ViewModel에 저장
                viewModel.setPerformanceName(((TextView)findViewById(R.id.userTitle)).getText().toString());
                viewModel.setTheater(((EditText)findViewById(R.id.userPlace)).getText().toString());
                viewModel.setDate(((EditText)findViewById(R.id.userDate)).getText().toString());
                viewModel.setShowtime(((EditText)findViewById(R.id.userTime)).getText().toString());
                viewModel.setSeat(((EditText)findViewById(R.id.userSeat)).getText().toString());

                // Review2 화면으로 데이터 전달을 위한 Intent 생성
                Intent review2Intent = new Intent(Review1.this, Review2.class);
                review2Intent.putExtra("performanceName", viewModel.getPerformanceName());
                review2Intent.putExtra("theater", viewModel.getTheater());
                review2Intent.putExtra("date", viewModel.getDate());
                review2Intent.putExtra("showtime", viewModel.getShowtime());
                review2Intent.putExtra("seat", viewModel.getSeat());

                // Review2 화면으로 이동
                startActivity(review2Intent);

//                // 다음 화면으로 이동
//                try {
//                    startActivity(new Intent(Review1.this, Review2.class));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }

            }
        });

        closeBtn = findViewById(R.id.close);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}