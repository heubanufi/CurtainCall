package com.example.capstone06.Fragment;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.util.Consumer;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.capstone06.data.model.Row;
import com.example.capstone06.databinding.ItemHorizontalBinding;

import java.util.List;

public class HorizontalItemAdapter extends RecyclerView.Adapter<HorizontalItemAdapter.MusicalViewHolder> {
    private final List<Row> items;
    private Consumer<Row> onItemClickListener;

    public HorizontalItemAdapter(List<Row> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public MusicalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemHorizontalBinding binding = ItemHorizontalBinding.inflate(inflater, parent, false);
        return new MusicalViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicalViewHolder holder, int position) {
        Row item = items.get(position);
        ItemHorizontalBinding binding = holder.binding;

        binding.getRoot().setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.accept(item);
            }
        });

        Glide.with(binding.imageView)
                .load(item.getMainImg())
                .into(binding.imageView);

        binding.titleTextView.setText(item.getTitle());
        binding.placeTextView.setText(item.getPlace());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setOnItemClickListener(Consumer<Row> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    static class MusicalViewHolder extends RecyclerView.ViewHolder {
        public final ItemHorizontalBinding binding;

        public MusicalViewHolder(ItemHorizontalBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
