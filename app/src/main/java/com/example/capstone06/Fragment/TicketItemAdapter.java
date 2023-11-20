package com.example.capstone06.Fragment;

import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.capstone06.R;
import com.example.capstone06.data.model.Review;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class TicketItemAdapter extends RecyclerView.Adapter<TicketItemAdapter.MyViewHolder> {

    private TextView title, theater, date, seat, time;

    public void fetchFirestoreData() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Review")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Review> reviewList = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Review review = document.toObject(Review.class);
                            reviewList.add(review);
                        }
                        updateData(reviewList); // 어댑터 내에서 데이터 업데이트
                    } else {
                        // 데이터 가져오기 실패 처리
                    }
                });
    }

    private List<Review> dataModelList; // MyDataModel은 Firebase 데이터 모델에 맞게 정의해야 합니다.

    public TicketItemAdapter(List<Review> dataModelList) {
        this.dataModelList = dataModelList;
    }

    public void updateData(List<Review> newData) {
        dataModelList.clear();
        dataModelList.addAll(newData);
        notifyDataSetChanged(); // 어댑터에 데이터가 변경되었음을 알림
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ticket, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Review dataModel = dataModelList.get(position);

        // 데이터를 뷰홀더의 뷰에 설정
        holder.titleTextView.setText(dataModel.getPerformanceName());
        holder.placeTextView.setText(dataModel.getTheater());
        holder.dateTextView.setText(dataModel.getDate());
        holder.timeTextView.setText(dataModel.getShowtime());
        holder.seatTextView.setText(dataModel.getSeat());

        // 이미지 설정 (이미지 URL 또는 리소스 사용)
        // 예: holder.imageView.setImageURI(dataModel.getImageUrl());
    }

    @Override
    public int getItemCount() {
        return dataModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView, placeTextView, dateTextView, timeTextView, seatTextView;
        //public ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.title_text_view);
            placeTextView = itemView.findViewById(R.id.place_text_view);
            dateTextView = itemView.findViewById(R.id.date_text_view);
            timeTextView = itemView.findViewById(R.id.time_text_view);
            seatTextView = itemView.findViewById(R.id.seat_text_view);
            //imageView = itemView.findViewById(R.id.image_view);
        }
    }
}
