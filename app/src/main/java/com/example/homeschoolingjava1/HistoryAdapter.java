package com.example.homeschoolingjava1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private List<GameHistory> historyList = new ArrayList<>();
    private final Context context;

    public HistoryAdapter(Context context) {
        this.context = context;
    }

    public void setHistoryList(List<GameHistory> historyList) {
        this.historyList = historyList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_card, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        GameHistory history = historyList.get(position);
        holder.txtDifficulty.setText("Difficulty: " + history.difficulty);
        holder.txtTimer.setText("Time: " + history.timer);
        holder.txtMistakes.setText("Mistakes: " + history.mistakes);
        holder.txtDate.setText("Date: " + history.date);

        if (history.imagePath != null) {
            File imgFile = new File(history.imagePath);
            if (imgFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                holder.imgPreview.setImageBitmap(myBitmap);
            }
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, HistoryDetailsActivity.class);
            intent.putExtra("difficulty", history.difficulty);
            intent.putExtra("timer", history.timer);
            intent.putExtra("mistakes", history.mistakes);
            intent.putExtra("date", history.date);
            intent.putExtra("imagePath", history.imagePath);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    static class HistoryViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPreview;
        TextView txtDifficulty, txtTimer, txtMistakes, txtDate;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPreview = itemView.findViewById(R.id.imgPreview);
            txtDifficulty = itemView.findViewById(R.id.txtDifficulty);
            txtTimer = itemView.findViewById(R.id.txtTimer);
            txtMistakes = itemView.findViewById(R.id.txtMistakes);
            txtDate = itemView.findViewById(R.id.txtDate);
        }
    }
}