package com.example.capstone06;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.capstone06.data.model.Row;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

// import com.example.capstone06.data.model.Item;

public class ItemDetail extends AppCompatActivity {

    private FirebaseFirestore firestore;
    private RatingBar reviewStar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        // 인텐트에서 아이템 정보를 받아옴
        Intent intent = getIntent();
        Row selectedItem = (Row) intent.getParcelableExtra("selectedItem");

        // 나머지 정보도 필요에 따라 표시

        // TextView 객체를 참조
        TextView titleTextView = findViewById(R.id.titletxt);
        TextView addressTextView = findViewById(R.id.address);
        TextView dateTextView = findViewById(R.id.date);
        TextView ageTextView = findViewById(R.id.age);
        TextView priceTextView = findViewById(R.id.price);
        TextView actorTextView = findViewById(R.id.actor);
        ImageView mainImageView = findViewById(R.id.poster);

        // 아이템 정보를 TextView에 설정
        titleTextView.setText(selectedItem.getTitle());
        addressTextView.setText(selectedItem.getPlace());
        dateTextView.setText(selectedItem.getDate());
        ageTextView.setText(selectedItem.getUseTrgt());
        priceTextView.setText(selectedItem.getUseFee());
        actorTextView.setText(selectedItem.getPlayer());

        firestore = FirebaseFirestore.getInstance();
        reviewStar = findViewById(R.id.reviewStar); // RatingBar ID에 맞게 변경하세요

        String performanceName = "performanceName 값";
        String itemTitle = selectedItem.getTitle();

        firestore.collection("Review")
                .whereEqualTo("performanceName", performanceName)
                .whereEqualTo("titleTextView", itemTitle)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        float totalRating = 0f;
                        int numberOfReviews = 0;

                        for (DocumentSnapshot document : queryDocumentSnapshots) {
                            Double rating = document.getDouble("rating");
                            if (rating != null) {
                                totalRating += rating.floatValue();
                                numberOfReviews++;
                            }
                        }

                        float averageRating = totalRating / numberOfReviews;
                        reviewStar.setRating(averageRating); // RatingBar에 평균 평점 설정

                        Map<String, Integer> frequencyMap = new HashMap<>();
                        String performanceNameValue = "";


                        // 모든 문서에서 'with' 필드 값의 빈도수 계산
                        for (DocumentSnapshot document : queryDocumentSnapshots) {
                            String withValue = document.getString("with");
                            if (withValue != null) {
                                frequencyMap.put(withValue, frequencyMap.getOrDefault(withValue, 0) + 1);
                            }
                            performanceNameValue = document.getString("performanceName");
                        }

                        // 빈도수가 가장 높은 'with' 값 찾기
                        String mostCommonWithValue = "";
                        int maxFrequency = 0;

                        for (Map.Entry<String, Integer> entry : frequencyMap.entrySet()) {
                            if (entry.getValue() > maxFrequency) {
                                mostCommonWithValue = entry.getKey();
                                maxFrequency = entry.getValue();
                            }
                        }

                        // mostCommonWithValue를 TextView에 설정
                        TextView abcTextView = findViewById(R.id.abc); // 실제로 사용하는 TextView ID로 변경
                        abcTextView.setText(mostCommonWithValue);

                        // performanceNameValue를 efg TextView에 설정
                        TextView efgTextView = findViewById(R.id.efg); // efg TextView ID로 변경
                        efgTextView.setText(performanceNameValue);


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // 처리 실패 시 작업
                    }
                });

        // Glide를 사용하여 이미지를 로드하여 ImageView에 표시
        Glide.with(this)
                .load(selectedItem.getMainImg())
                .into(mainImageView);

        ImageButton floatingButton = findViewById(R.id.fab);
        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Review1.java로 이동
                Intent intent = new Intent(ItemDetail.this, Review1.class);
                intent.putExtra("titleText", selectedItem.getTitle());
                startActivity(intent);
            }
        });

    }
}
