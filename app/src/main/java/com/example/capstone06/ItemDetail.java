package com.example.capstone06;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.capstone06.data.model.Row;

// import com.example.capstone06.data.model.Item;

public class ItemDetail extends AppCompatActivity {

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

        String titleText = titleTextView.getText().toString();

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
                intent.putExtra("titleText", titleText);
                startActivity(intent);
            }
        });

    }
}
