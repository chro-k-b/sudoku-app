package com.example.homeschoolingjava1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class GameActivity extends AppCompatActivity {

    private View menuOverlay;
    private LinearLayout sideMenu;
    private boolean isMenuOpen = false;
    private int menuWidth;
    private TextView difficultyTextView;
    private TextView timerTextView;
    private TextView timerTextView1;
    private CountDownTimer timer;
    long secondsElapsed = 0;

    private android.widget.Button[][] cells = new android.widget.Button[9][9];

    private int[][] solutionBoard = new int[9][9];
    private int[][] playerBoard = new int[9][9];

    private int selectedRow = -1;
    private int selectedCol = -1;

    private int difficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game);

        menuOverlay = findViewById(R.id.menuOverlay);
        sideMenu = findViewById(R.id.sideMenu);
        menuWidth = (int) (280 * getResources().getDisplayMetrics().density);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //getting the setting preferences
        SharedPreferences prefs = getSharedPreferences("SudokuSettings", MODE_PRIVATE);

        difficulty = prefs.getInt("difficulty", 1); // default easy
        boolean timerEnabled = prefs.getBoolean("timerEnabled", false);

        difficultyTextView = findViewById(R.id.difficultyTextView1);
        switch(difficulty){
            case(1):
                difficultyTextView.setText("easy");
                break;
            case(2):
                difficultyTextView.setText("medium");
                break;
            case(3):
                difficultyTextView.setText("hard");
                break;
        }

        timerTextView = findViewById(R.id.timerTextView);
        timerTextView1 = findViewById(R.id.timerTextView1);

        if(timerEnabled){
            timerTextView.setText("time");
            startTimer();
        }

        // Set click listener for the back button to open menu
        findViewById(R.id.imgBack1).setOnClickListener(v -> openMenu());

        // Set click listener for overlay to close menu
        menuOverlay.setOnClickListener(v -> closeMenu());

        // Handle back button press
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (isMenuOpen) {
                    closeMenu();
                } else {
                    setEnabled(false);
                    getOnBackPressedDispatcher().onBackPressed();
                }
            }
        });

        //THe side menu button, needs to work properly, these are just place holders
        findViewById(R.id.btnNewGame).setOnClickListener(v -> {
            // New Game Logic
            closeMenu();
        });

        findViewById(R.id.btnRestartMenu).setOnClickListener(v -> {
            // Restart Logic
            closeMenu();
        });


    }

    private void startTimer() {
        timer = new CountDownTimer(3600000, 1000) {
            public void onTick(long millisUntilFinished) {
                secondsElapsed++;

                int minutes = (int) (secondsElapsed / 60);
                int seconds = (int) (secondsElapsed % 60);

                timerTextView1.setText(String.format("%02d:%02d", minutes, seconds));
            }

            public void onFinish() {}
        }.start();
    }

    private void initBoard() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {

                String buttonID = "b" + (row + col + 1);
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());

                cells[row][col] = findViewById(resID);

                int finalRow = row;
                int finalCol = col;

                cells[row][col].setOnClickListener(v -> {
                    selectedRow = finalRow;
                    selectedCol = finalCol;
                });
            }
        }
    }

    private void openMenu() {
        isMenuOpen = true;
        menuOverlay.setVisibility(View.VISIBLE);
        menuOverlay.animate().alpha(1).setDuration(300).start();
        sideMenu.animate().translationX(0).setDuration(300).start();
    }

    private void closeMenu() {
        isMenuOpen = false;
        menuOverlay.animate().alpha(0).setDuration(300).withEndAction(() -> {
            menuOverlay.setVisibility(View.GONE);
        }).start();
        sideMenu.animate().translationX(-menuWidth).setDuration(300).start();
    }

    public void goBack(View v){
        if (timer != null) {
            timer.cancel();
        }
        finish();
    }

}