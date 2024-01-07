package com.example.capstone06;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class Review2 extends AppCompatActivity {
    private ImageButton submitButton, prevBtn;
    private ReviewViewModel viewModel;
    private RadioGroup rgStory, rgEye, rgEar, rgWith;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review2);

        viewModel = new ViewModelProvider(this).get(ReviewViewModel.class);

        // Review1로부터 데이터를 받음
        Intent intent = getIntent();
        String performanceName = intent.getStringExtra("performanceName");
        String theater = intent.getStringExtra("theater");
        String date = intent.getStringExtra("date");
        String showtime = intent.getStringExtra("showtime");
        String seat = intent.getStringExtra("seat");

        submitButton = findViewById(R.id.completed);
        rgStory = findViewById(R.id.rgStory);
        rgEye = findViewById(R.id.rgEye);
        rgEar = findViewById(R.id.rgEar);
        rgWith = findViewById(R.id.rgWith);

        prevBtn = findViewById(R.id.previous);
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // rating bar
                viewModel.setRating(((RatingBar)findViewById(R.id.ratingBar)).getRating());

                viewModel.setPerformanceName(performanceName);
                viewModel.setTheater(theater);
                viewModel.setDate(date);
                viewModel.setShowtime(showtime);
                viewModel.setSeat(seat);

                // 스토리
                int selectedrgStory = rgStory.getCheckedRadioButtonId();
                if (selectedrgStory == R.id.likeStory) {
                    // "likeRadioButton"이 선택되면 true를 ViewModel에 저장
                    viewModel.setStory("스토리가 좋아요");
                } else if (selectedrgStory == R.id.dislikeStory) {
                    // "dislikeRadioButton"이 선택되면 false를 ViewModel에 저장
                    viewModel.setStory("스토리가 아쉬워요");
                }

                // 눈
                int selectedrgEye = rgEye.getCheckedRadioButtonId();
                if (selectedrgEye == R.id.likeEye) {
                    // "likeRadioButton"이 선택되면 true를 ViewModel에 저장
                    viewModel.setEye("눈이 즐거워요");
                } else if (selectedrgEye == R.id.dislikeEye) {
                    // "dislikeRadioButton"이 선택되면 false를 ViewModel에 저장
                    viewModel.setEye("눈이 아쉬워요");
                }

                // 귀
                int selectedrgEar = rgEar.getCheckedRadioButtonId();
                if (selectedrgEar == R.id.likeEar) {
                    // "likeRadioButton"이 선택되면 true를 ViewModel에 저장
                    viewModel.setEar("귀가 즐거워요");
                } else if (selectedrgEar == R.id.dislikeEar) {
                    // "dislikeRadioButton"이 선택되면 false를 ViewModel에 저장
                    viewModel.setEar("귀가 아쉬워요");
                }

                //나는
                int selectedrgWith = rgWith.getCheckedRadioButtonId();
                if (selectedrgWith == R.id.alone) {
                    viewModel.setWith("혼자서 관람");
                } else if (selectedrgWith == R.id.friend) {
                    viewModel.setWith("친구와 함께");
                }
                else if (selectedrgWith == R.id.family) {
                    viewModel.setWith("가족과 함께");
                }else if (selectedrgWith == R.id.couple) {
                    viewModel.setWith("연인과 함께");
                }

                // 후기를 저장
                viewModel.saveReview();

                // Review2을 종료하고 ItemDetail 페이지로 이동
                Intent intent = new Intent(Review2.this, ItemDetail.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish(); // Review2 종료
            }
        });

    }
}