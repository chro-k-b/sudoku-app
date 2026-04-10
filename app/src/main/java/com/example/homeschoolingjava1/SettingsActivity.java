package com.example.homeschoolingjava1;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

public class SettingsActivity extends AppCompatActivity {

    private MaterialButton btnEasy, btnMedium, btnHard;

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
    }

    public void goBack(View v){
        finish();
    }
}