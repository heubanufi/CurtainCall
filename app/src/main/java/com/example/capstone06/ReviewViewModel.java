package com.example.capstone06;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ReviewViewModel extends ViewModel {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    // 저장할 데이터 필드
    private String performanceName;
    private String theater;
    private String date;
    private String showtime;
    private String seat;
    private float rating;
    private boolean story;
    private boolean eye;
    private boolean ear;
    private String with;



    public void setPerformanceName(String performanceName) {
        this.performanceName = performanceName;
    }

    public void setTheater(String theater) {
        this.theater = theater;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setShowtime(String showtime) {
        this.showtime = showtime;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setStory(boolean story) {
        this.story = story;
    }
    public void setEye(boolean eye) {
        this.eye = eye;
    }
    public void setEar(boolean ear) {
        this.ear = ear;
    }
    public void setWith(String with) {
        this.with = with;
    }

    public String getPerformanceName() {
        return performanceName;
    }
    public String getTheater() {
        return theater;
    }
    public String getDate() {
        return date;
    }
    public String getShowtime() {
        return showtime;
    }
    public String getSeat() {
        return seat;
    }


    FirebaseUser user = mAuth.getCurrentUser();

    public void saveReview() {
        // Review 객체를 Map<String, Object>으로 변환
        Map<String, Object> reviewData = new HashMap<>();
        reviewData.put("performanceName", performanceName);
        reviewData.put("theater", theater);
        reviewData.put("date", date);
        reviewData.put("showtime", showtime);
        reviewData.put("seat", seat);
        reviewData.put("rating", rating);
        reviewData.put("story", story);
        reviewData.put("eye", eye);
        reviewData.put("ear", ear);
        reviewData.put("with", with);
        reviewData.put("user", user.getUid());

        // Firestore에 데이터 저장
        db.collection("Review").add(reviewData)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        // 데이터가 성공적으로 저장된 경우의 처리
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // 데이터 저장 실패 시의 처리
                    }
                });
    }

}

