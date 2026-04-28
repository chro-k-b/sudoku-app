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
    boolean timerEnabled = true;

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

        SharedPreferences prefs = getSharedPreferences("SudokuSettings", MODE_PRIVATE);

        difficulty = prefs.getInt("difficulty", 1);
        boolean isTimerOn = prefs.getBoolean("timerEnabled", true);

        // reset all buttons
        btnEasy.setChecked(false);
        btnMedium.setChecked(false);
        btnHard.setChecked(false);

        if (difficulty == 1) {
            btnEasy.setChecked(true);
        } else if (difficulty == 2) {
            btnMedium.setChecked(true);
        } else {
            btnHard.setChecked(true);
        }

        timerButton.setChecked(isTimerOn);


        View.OnClickListener difficultyClickListener = v -> {
            // Uncheck all
            btnEasy.setChecked(false);
            btnMedium.setChecked(false);
            btnHard.setChecked(false);

            // Check the clicked one
            MaterialButton clicked = (MaterialButton) v;
            clicked.setChecked(true);

            if (v.getId() == R.id.btnEasy) {
                difficulty = 1;
            } else if (v.getId() == R.id.btnMedium) {
                difficulty = 2;
            } else if (v.getId() == R.id.btnHard) {
                difficulty = 3;
            }
        };

        btnEasy.setOnClickListener(difficultyClickListener);
        btnMedium.setOnClickListener(difficultyClickListener);
        btnHard.setOnClickListener(difficultyClickListener);

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