package com.example.homeschoolingjava1;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ToggleButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

public class SettingsActivity extends AppCompatActivity {

    private MaterialButton btnEasy, btnMedium, btnHard;
    private ToggleButton timerButton;
    int difficulty = 1;
    boolean timerEnabled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnEasy = findViewById(R.id.btnEasy);
        btnMedium = findViewById(R.id.btnMedium);
        btnHard = findViewById(R.id.btnHard);
        timerButton = findViewById(R.id.timerButton);

        View.OnClickListener difficultyClickListener = v -> {
            // Uncheck all
            btnEasy.setChecked(false);
            btnMedium.setChecked(false);
            btnHard.setChecked(false);

            // Check the clicked one
            ((MaterialButton) v).setChecked(true);
        };

        btnEasy.setOnClickListener(difficultyClickListener);
        btnMedium.setOnClickListener(difficultyClickListener);
        btnHard.setOnClickListener(difficultyClickListener);

        btnEasy.setOnClickListener(v -> {
            difficulty = 1;
        });

        btnMedium.setOnClickListener(v -> {
            difficulty = 2;
        });

        btnHard.setOnClickListener(v -> {
            difficulty = 3;
        });

        timerButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            timerEnabled = isChecked;
        });
    }

    public void goBack(View v){
        SharedPreferences prefs = getSharedPreferences("SudokuSettings", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putInt("difficulty", difficulty);
        editor.putBoolean("timerEnabled", timerEnabled);

        editor.apply();

        finish();
    }
}