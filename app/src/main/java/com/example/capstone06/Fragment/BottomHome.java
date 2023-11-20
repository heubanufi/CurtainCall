package com.example.capstone06.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.capstone06.ItemDetail;
import com.example.capstone06.data.repository.CulturalEventRepository;
import com.example.capstone06.databinding.BottomHomeBinding;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;

public class BottomHome extends Fragment {
    private BottomHomeBinding binding;
    private Disposable disposable;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = BottomHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fetchData();
    }

    private void fetchData() {
        disposable = CulturalEventRepository.instance
                .fetch()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(events -> {
                    if (isDetached()) return;

                    HorizontalItemAdapter ongoingAdapter = new HorizontalItemAdapter(events.first);
                    ongoingAdapter.setOnItemClickListener(row -> {
                        Intent intent = new Intent(requireContext(), ItemDetail.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("selectedItem", row);
                        startActivity(intent);
                    });

                    HorizontalItemAdapter upcomingAdapter = new HorizontalItemAdapter(events.second);
                    upcomingAdapter.setOnItemClickListener(row -> {
                        Intent intent = new Intent(requireContext(), ItemDetail.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("selectedItem", row);
                        startActivity(intent);
                    });

                    binding.ongoingRecyclerView.setAdapter(ongoingAdapter);
                    binding.upcomingRecyclerView.setAdapter(upcomingAdapter);
                });
    }

    @Override
    public void onDestroy() {
        if (disposable != null) {
            disposable.dispose();
        }

        super.onDestroy();
    }
}


