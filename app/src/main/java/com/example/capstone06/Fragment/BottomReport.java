package com.example.capstone06.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstone06.ItemDetail;
import com.example.capstone06.R;
import com.example.capstone06.Review1;
import com.example.capstone06.data.model.Review;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BottomReport extends Fragment {
    private String TAG = "프래그먼트";
    private View view;
    private RecyclerView recyclerView;
    private TicketItemAdapter adapter;
    private List<Review> reviewList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView");
        view = inflater.inflate(R.layout.bottom_report, container, false);

        ImageButton floatingButton = view.findViewById(R.id.fab);
        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Review1.java로 이동
                Intent intent = new Intent(getActivity(), Review1.class);
                startActivity(intent);
            }
        });

        recyclerView = view.findViewById(R.id.ticket_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        reviewList = new ArrayList<>();
        adapter = new TicketItemAdapter(reviewList);
        recyclerView.setAdapter(adapter);

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            String userUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

            CollectionReference userCollection = FirebaseFirestore.getInstance().collection("Review");

            Query query = userCollection.whereEqualTo("user", userUid);

            // 쿼리를 실행하여 문서 수를 얻습니다
            query.get().addOnSuccessListener(queryDocumentSnapshots -> {
                int count = queryDocumentSnapshots.size();
                TextView userCntTextView = view.findViewById(R.id.userCnt);
                userCntTextView.setText(String.valueOf(count));
            }).addOnFailureListener(e -> {
                // 네트워크 문제나 권한과 같은 오류를 처리하세요
            });

            // 쿼리를 실행하여 리뷰 데이터를 가져오며, with 필드를 기준으로 그룹화하고 가장 많은 수를 가져옵니다
            query.get().addOnSuccessListener(queryDocumentSnapshots -> {
                reviewList.clear();
                Map<String, Integer> withCounts = new HashMap<>();

                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                    Review review = document.toObject(Review.class);
                    reviewList.add(review);

                    String with = review.getWith();
                    withCounts.put(with, withCounts.getOrDefault(with, 0) + 1);
                }

                // withCounts에서 가장 많은 수를 가진 값을 찾습니다
                String mostFrequentWith = findMostFrequent(withCounts);

                // 가장 많은 수를 가진 with 값을 텍스트뷰에 설정
                TextView mostFrequentWithTextView = view.findViewById(R.id.mostFrequentWith);
                mostFrequentWithTextView.setText(mostFrequentWith);

                adapter.notifyDataSetChanged();
            }).addOnFailureListener(e -> {
                // 네트워크 문제나 권한과 같은 오류를 처리하세요
            });


            // 쿼리를 실행하여 리뷰 데이터를 가져오며, rating 필드를 기준으로 그룹화하고 가장 큰 rating 값을 가져옵니다
            query.get().addOnSuccessListener(queryDocumentSnapshots -> {
                reviewList.clear();
                double maxRating = Double.MIN_VALUE;
                String maxRatingPerformanceName = "";

                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                    Review review = document.toObject(Review.class);
                    reviewList.add(review);

                    double rating = review.getRating();
                    String performanceName = review.getPerformanceName();

                    if (rating > maxRating) {
                        maxRating = rating;
                        maxRatingPerformanceName = performanceName;
                    }
                }

                // 가장 큰 rating 값을 가진 performanceName을 텍스트뷰에 설정
                TextView maxRatingPerformanceTextView = view.findViewById(R.id.maxRatingPerformance);
                maxRatingPerformanceTextView.setText(maxRatingPerformanceName);

                adapter.notifyDataSetChanged();
            }).addOnFailureListener(e -> {
                // 네트워크 문제나 권한과 같은 오류를 처리하세요
            });

            // 쿼리를 실행하여 리뷰 데이터를 가져오며, theater 필드를 기준으로 그룹화하고 가장 많은 수를 가져옵니다
            query.get().addOnSuccessListener(queryDocumentSnapshots -> {
                reviewList.clear();
                Map<String, Integer> withCounts = new HashMap<>();

                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                    Review review = document.toObject(Review.class);
                    reviewList.add(review);

                    String theater = review.getTheater();
                    withCounts.put(theater, withCounts.getOrDefault(theater, 0) + 1);
                }

                // withCounts에서 가장 많은 수를 가진 값을 찾습니다
                String mostFrequentTheater = findMostFrequent(withCounts);

                // 가장 많은 수를 가진 with 값을 텍스트뷰에 설정
                TextView mostFrequentTheaterTextView = view.findViewById(R.id.mostFrequentTheater);
                mostFrequentTheaterTextView.setText(mostFrequentTheater);

                adapter.notifyDataSetChanged();
            }).addOnFailureListener(e -> {
                // 네트워크 문제나 권한과 같은 오류를 처리하세요
            });
    }

        return view;
    }

    private String findMostFrequent(Map<String, Integer> withCounts) {
        String mostFrequentWith = "";
        int maxCount = 0;

        for (Map.Entry<String, Integer> entry : withCounts.entrySet()) {
            if (entry.getValue() > maxCount) {
                mostFrequentWith = entry.getKey();
                maxCount = entry.getValue();
            }
        }

        return mostFrequentWith;
    }
}


