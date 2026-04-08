package com.example.homeschoolingjava1;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HistoryActivity extends AppCompatActivity {

    private HistoryAdapter adapter;
    private AppDatabase db;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_history);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
//this is all just for testing history, this can be deleted until line 74
        db = AppDatabase.getInstance(this);
        RecyclerView recyclerView = findViewById(R.id.recyclerViewHistory);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new HistoryAdapter(this);
        recyclerView.setAdapter(adapter);

        findViewById(R.id.btnReset).setOnClickListener(v -> resetHistory());

        // Add dummy data for testing purposes
        addDummyData();

        loadHistory();
    }

    private void addDummyData() {
        executorService.execute(() -> {
            List<GameHistory> existing = db.gameHistoryDao().getAll();
            if (existing.isEmpty()) {
                db.gameHistoryDao().insert(new GameHistory("Easy", "04:20", "2023-11-01", 0, null));
                db.gameHistoryDao().insert(new GameHistory("Medium", "08:15", "2023-11-02", 2, null));
                db.gameHistoryDao().insert(new GameHistory("Hard", "15:40", "2023-11-03", 1, null));
                loadHistory();
            }
        });
    }

    private void loadHistory() {
        executorService.execute(() -> {
            List<GameHistory> historyList = db.gameHistoryDao().getAll();
            runOnUiThread(() -> adapter.setHistoryList(historyList));
        });
    }

    private void resetHistory() {
        executorService.execute(() -> {
            db.gameHistoryDao().deleteAll();
            loadHistory();
        });
    }
//until here can be deleted
    public void goBack(View v){
        finish();
    }
}