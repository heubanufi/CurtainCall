package com.example.capstone06.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.capstone06.ItemDetail;
import com.example.capstone06.R;
import com.example.capstone06.data.model.CulturalEventResponse;
import com.example.capstone06.data.model.Row;
import com.example.capstone06.data.repository.CulturalEventRepository;
import com.example.capstone06.databinding.BottomSearchBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class BottomSearch extends Fragment {
    private BottomSearchBinding binding;
    private final List<Row> rows = new ArrayList<>();
    private final VerticalItemAdapter adapter = new VerticalItemAdapter();
    private Disposable disposable;
    private String selectedGenre = "전체"; // 초기 선택을 "전체"로 설정
    private CheckBox checkBoxOption1; // 무료 체크박스
    private CheckBox checkBoxOption2; // 유료 체크박스
    private boolean isFreeChecked = false;
    private boolean isPaidChecked = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = BottomSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // CheckBox 초기화
        checkBoxOption1 = view.findViewById(R.id.checkBoxOption1); // "무료" 체크박스
        checkBoxOption2 = view.findViewById(R.id.checkBoxOption2); // "유료" 체크박스

        // CheckBox 리스너 설정
        checkBoxOption1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            isFreeChecked = isChecked;
            performSearch(binding.search.getText().toString()); // 체크박스 상태가 변경될 때 목록을 다시 필터링
        });

        checkBoxOption2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            isPaidChecked = isChecked;
            performSearch(binding.search.getText().toString()); // 체크박스 상태가 변경될 때 목록을 다시 필터링
        });

        disposable = CulturalEventRepository.instance.fetch()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(events -> {
                    if (isDetached()) return;

                    rows.clear();
                    rows.addAll(events.first);
                    rows.addAll(events.second);

                    adapter.submitList(new ArrayList<>(rows));
                });

        // 검색어 입력 이벤트 처리
        binding.search.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch(v.getText().toString());
                return true;
            }
            return false;
        });

        // 검색어 입력 이벤트 처리
        binding.search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 입력 텍스트가 변경되기 전에 수행되는 작업
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 텍스트가 변경될 때마다 수행할 작업
                performSearch(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // 입력 텍스트가 변경된 후에 수행되는 작업
            }
        });


        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(
                requireContext(), R.array.Genre_options, android.R.layout.simple_spinner_item
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerGenre.setAdapter(spinnerAdapter);

        // 스피너 아이템 선택 이벤트 처리
        binding.spinnerGenre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedGenre = parentView.getItemAtPosition(position).toString();
                filterDataByGenre(selectedGenre);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here, or you can define default behavior
            }
        });


        adapter.setOnItemClickListener(row -> {
            Intent intent = new Intent(requireContext(), ItemDetail.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("selectedItem", row);
            startActivity(intent);
        });

        binding.recyclerView.setAdapter(adapter);
    }


    private void performSearch(String query) {
        if (TextUtils.isEmpty(query) && !isFreeChecked && !isPaidChecked) {
            // 검색어가 비어 있고 모든 체크박스가 해제된 경우, 전체 목록을 표시
            updateListView(rows);
        } else {
            // 검색어가 비어 있거나 체크박스 중 하나 이상이 선택된 경우, 필터링하여 검색 결과 표시
            List<Row> searchResults = filterItemsByTitleAndFreeStatus(query, isFreeChecked, isPaidChecked);
            updateListView(searchResults);
        }
    }


    // 제목과 무료 상태에 따라 항목 필터링
    private List<Row> filterItemsByTitleAndFreeStatus(String query, boolean isFree, boolean isPaid) {
        return rows.stream()
                .filter(item -> item.getTitle().contains(query) && ((isFree && "무료".equals(item.getIsFree())) || (isPaid && "유료".equals(item.getIsFree()))))
                .collect(Collectors.toList());
    }

    // 무료 상태에 따라 항목 필터링
    private List<Row> filterItemsByFreeStatus(boolean isFree, boolean isPaid) {
        return rows.stream()
                .filter(item -> (isFree && "무료".equals(item.getIsFree())) || (isPaid && "유료".equals(item.getIsFree())))
                .collect(Collectors.toList());
    }


    // item.title에 검색어를 포함하는 아이템을 필터링
    private List<Row> filterItemsByTitle(String query) {
        return rows.stream()
                .filter(item -> item.getTitle().contains(query))
                .collect(Collectors.toList());
    }

    // ListView를 업데이트하여 결과 표시
    private void updateListView(List<Row> items) {
        adapter.submitList(new ArrayList<>(items));
    }

    private void filterDataByGenre(String selectedGenre) {
        List<Row> filteredEvents;
        if ("모두".equals(selectedGenre)) {
            // "모두"를 선택한 경우, 전체 목록을 표시
            updateListView(rows);
        }
        if (selectedGenre.equals("연극")) {
            filteredEvents = rows.stream()
                    .filter(item -> "연극".equals(item.getCodename()))
                    .collect(Collectors.toList());
        } else if (selectedGenre.equals("뮤지컬/오페라")) {
            filteredEvents = rows.stream()
                    .filter(item -> "뮤지컬/오페라".equals(item.getCodename()))
                    .collect(Collectors.toList());
        } else {
            // Handle other genres if needed
            // 다른 장르 처리 필요하면 여기에 추가
            filteredEvents = new ArrayList<>(rows);
        }
        updateListView(filteredEvents);
    }


    @Override
    public void onDestroy() {
        if (disposable != null) {
            disposable.dispose();
        }

        super.onDestroy();
    }
}
