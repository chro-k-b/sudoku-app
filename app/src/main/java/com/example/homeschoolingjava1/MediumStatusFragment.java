package com.example.homeschoolingjava1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MediumStatusFragment extends Fragment {

    private TextView gamesWonNumber, averageTimeNumber, bestTimeNumber, noMistakeNumber, averageMistakeNumber;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public MediumStatusFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_medium_status, container, false);

        gamesWonNumber = view.findViewById(R.id.gamesWonNumber);
        averageTimeNumber = view.findViewById(R.id.averageTimeNumber);
        bestTimeNumber = view.findViewById(R.id.bestTimeNumber);
        noMistakeNumber = view.findViewById(R.id.noMistakeNumber);
        averageMistakeNumber = view.findViewById(R.id.averageMistakeNumber);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        executorService.execute(() -> {
            if (!isAdded()) return;
            AppDatabase db = AppDatabase.getInstance(requireContext());
            List<GameHistory> history = db.gameHistoryDao().getByDifficulty("Medium");

            int gamesWon = history.size();
            int totalSeconds = 0;
            int bestSeconds = Integer.MAX_VALUE;
            int noMistakesCount = 0;
            int totalMistakes = 0;

            for (GameHistory game : history) {
                int seconds = parseTimeToSeconds(game.timer);
                totalSeconds += seconds;
                if (seconds < bestSeconds) bestSeconds = seconds;
                if (game.mistakes == 0) noMistakesCount++;
                totalMistakes += game.mistakes;
            }

            final String wonStr = String.valueOf(gamesWon);
            final String avgTimeStr = gamesWon > 0 ? formatSeconds(totalSeconds / gamesWon) : "00:00";
            final String bestTimeStr = (gamesWon > 0 && bestSeconds != Integer.MAX_VALUE) ? formatSeconds(bestSeconds) : "00:00";
            final String noMistakeStr = String.valueOf(noMistakesCount);
            final String avgMistakeStr = gamesWon > 0 ? String.format("%.1f", (float) totalMistakes / gamesWon) : "0.0";

            if (isAdded()) {
                requireActivity().runOnUiThread(() -> {
                    gamesWonNumber.setText(wonStr);
                    averageTimeNumber.setText(avgTimeStr);
                    bestTimeNumber.setText(bestTimeStr);
                    noMistakeNumber.setText(noMistakeStr);
                    averageMistakeNumber.setText(avgMistakeStr);
                });
            }
        });
    }

    private int parseTimeToSeconds(String timer) {
        try {
            String[] parts = timer.split(":");
            return Integer.parseInt(parts[0]) * 60 + Integer.parseInt(parts[1]);
        } catch (Exception e) {
            return 0;
        }
    }

    private String formatSeconds(int totalSeconds) {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}