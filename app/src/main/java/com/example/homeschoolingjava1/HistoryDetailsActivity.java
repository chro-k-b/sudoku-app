package com.example.homeschoolingjava1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class HistoryDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_details);

        ImageView btnBack = findViewById(R.id.btnBack);
        ImageView imgFullPreview = findViewById(R.id.imgFullPreview);
        TextView detailDifficulty = findViewById(R.id.detailDifficulty);
        TextView detailTimer = findViewById(R.id.detailTimer);
        TextView detailMistakes = findViewById(R.id.detailMistakes);
        TextView detailDate = findViewById(R.id.detailDate);

        btnBack.setOnClickListener(v -> finish());

        // Receive data
        String difficulty = getIntent().getStringExtra("difficulty");
        String timer = getIntent().getStringExtra("timer");
        int mistakes = getIntent().getIntExtra("mistakes", 0);
        String date = getIntent().getStringExtra("date");
        String imagePath = getIntent().getStringExtra("imagePath");

        // Display data
        detailDifficulty.setText("Difficulty: " + difficulty);
        detailTimer.setText("Time Spent: " + timer);
        detailMistakes.setText("Mistakes: " + mistakes);
        detailDate.setText("Played on: " + date);

        if (imagePath != null) {
            File imgFile = new File(imagePath);
            if (imgFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                imgFullPreview.setImageBitmap(myBitmap);
            }
        }
    }
}