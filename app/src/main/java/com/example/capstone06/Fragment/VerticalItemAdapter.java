package com.example.capstone06.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.util.Consumer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.capstone06.data.model.Row;
import com.example.capstone06.databinding.ItemVerticalBinding;

public class VerticalItemAdapter extends ListAdapter<Row, VerticalItemAdapter.MusicalViewHolder> {
    private Consumer<Row> onItemClickListener;

    public VerticalItemAdapter() {
        super(new DiffUtil.ItemCallback<Row>() {
            @Override
            public boolean areItemsTheSame(@NonNull Row oldItem, @NonNull Row newItem) {
                return TextUtils.equals(oldItem.getTitle(), newItem.getTitle()) &&
                        TextUtils.equals(oldItem.getPlace(), newItem.getPlace()) &&
                        TextUtils.equals(oldItem.getDate(), newItem.getDate());
            }

            @Override
            public boolean areContentsTheSame(@NonNull Row oldItem, @NonNull Row newItem) {
                return true;
            }
        });
    }

    @NonNull
    @Override
    public MusicalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemVerticalBinding binding = ItemVerticalBinding.inflate(inflater, parent, false);
        return new MusicalViewHolder(binding);
    }

    public void setOnItemClickListener(Consumer<Row> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull MusicalViewHolder holder, int position) {
        Row item = getItem(position);
        ItemVerticalBinding binding = holder.binding;

        binding.getRoot().setOnClickListener(view -> {
            if (onItemClickListener != null) {
                onItemClickListener.accept(item);
            }
        });

        Glide.with(binding.imageView)
                .load(item.getMainImg())
                .into(binding.imageView);

        binding.titleTextView.setText(item.getTitle());
        binding.placeTextView.setText(item.getPlace());
        binding.dateTextView.setText(item.getDate());
    }

    static class MusicalViewHolder extends RecyclerView.ViewHolder {
        public final ItemVerticalBinding binding;

        public MusicalViewHolder(ItemVerticalBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
