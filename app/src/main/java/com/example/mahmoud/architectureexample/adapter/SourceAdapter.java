package com.example.mahmoud.architectureexample.adapter;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mahmoud.architectureexample.R;
import com.example.mahmoud.architectureexample.model.Source;

public class SourceAdapter extends ListAdapter<Source, SourceAdapter.SourceHolder> {
    private OnItemClickListener listener;

    public SourceAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Source> DIFF_CALLBACK = new DiffUtil.ItemCallback<Source>() {
        @Override
        public boolean areItemsTheSame(Source oldItem, Source newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(Source oldItem, Source newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) &&
                    oldItem.getUrl().equals(newItem.getUrl());
        }
    };

    @NonNull
    @Override
    public SourceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.source_item, parent, false);
        return new SourceHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SourceHolder holder, int position) {
        Source currentSource = getItem(position);
        holder.textViewTitle.setText(currentSource.getTitle());
        holder.textViewLink.setText(currentSource.getUrl());
    }

    public Source getSourceAt(int position) {
        return getItem(position);
    }

    class SourceHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewLink;

        public SourceHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewLink = itemView.findViewById(R.id.text_view_link);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Source source);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}