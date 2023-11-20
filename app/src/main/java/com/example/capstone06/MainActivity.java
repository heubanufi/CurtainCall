package com.example.capstone06;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.capstone06.Fragment.BottomHome;
import com.example.capstone06.Fragment.BottomMypage;
import com.example.capstone06.Fragment.BottomReport;
import com.example.capstone06.Fragment.BottomSearch;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView; //바텀 네비게이션 뷰

    private TextView textViewResult;
    private ArrayAdapter<String> adapter;
    private ListView listView;
    Fragment bottom_home, bottom_search, bottom_report, bottom_mypage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottom_home = new BottomHome();
        bottom_search = new BottomSearch();
        bottom_report = new BottomReport();
        bottom_mypage = new BottomMypage();

        textViewResult = findViewById(R.id.textViewResult);
        //listView = findViewById(R.id.musicalList);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // AsyncTask를 실행하여 네트워크 요청을 수행합니다.
        // new NetworkTask().execute();

        // 초기 플래그먼트 설정
        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, bottom_home).commitAllowingStateLoss();

        // 리스너 등록
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //Log.i(TAG, "바텀 네비게이션 클릭");
                if (item.getItemId() == R.id.home) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, new BottomHome()).commit();
                } else if (item.getItemId() == R.id.search) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, bottom_search).commit();
                } else if (item.getItemId() == R.id.report) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, bottom_report).commit();
                } else if (item.getItemId() == R.id.mypage) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, bottom_mypage).commit();
                }
                return true;
            }
        });


    }


}