package com.example.homeschoolingjava1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
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

        initBoard();
        generateSolution();
        generatePuzzle();
        displayBoard(playerBoard);
        initNumberButtons();
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
        for (int i = 0; i < 81; i++) {

            String buttonID = "b" + (i + 1);
            int resID = getResources().getIdentifier(buttonID, "id", getPackageName());

            // BOX index (0 → 8)
            int box = i / 9;

            // position inside box (0 → 8)
            int pos = i % 9;

            // convert to row/col
            int row = (box / 3) * 3 + (pos / 3);
            int col = (box % 3) * 3 + (pos % 3);

            cells[row][col] = findViewById(resID);

            int finalRow = row;
            int finalCol = col;

            cells[row][col].setOnClickListener(v -> {
                selectedRow = finalRow;
                selectedCol = finalCol;
            });
        }
    }

    private boolean fillBoard(int[][] board) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {

                if (board[row][col] == 0) {

                    java.util.List<Integer> numbers = new java.util.ArrayList<>();
                    for (int i = 1; i <= 9; i++) numbers.add(i);
                    java.util.Collections.shuffle(numbers);

                    for (int num : numbers) {
                        if (isSafe(board, row, col, num)) {
                            board[row][col] = num;

                            if (fillBoard(board)) return true;

                            board[row][col] = 0; // backtrack
                        }
                    }

                    return false;
                }
            }
        }
        return true;
    }

    private boolean isSafe(int[][] board, int row, int col, int num) {

        for (int i = 0; i < 9; i++) {
            if (board[row][i] == num) return false;
            if (board[i][col] == num) return false;
        }

        int startRow = row - row % 3;
        int startCol = col - col % 3;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[startRow + i][startCol + j] == num) return false;
            }
        }

        return true;
    }

    private void generateSolution() {
        solutionBoard = new int[9][9];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                solutionBoard[i][j] = 0;
            }
        }

        fillBoard(solutionBoard);
    }

    private void copyBoard(int[][] from, int[][] to) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                to[i][j] = from[i][j];
            }
        }
    }

    private int countSolutions(int[][] board) {
        return solveAndCount(board, 0);
    }

    private int solveAndCount(int[][] board, int count) {
        if (count > 1) return count; // stop early if more than 1 solution

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {

                if (board[row][col] == 0) {

                    for (int num = 1; num <= 9; num++) {
                        if (isSafe(board, row, col, num)) {

                            board[row][col] = num;
                            count = solveAndCount(board, count);
                            board[row][col] = 0;

                            if (count > 1) return count;
                        }
                    }

                    return count;
                }
            }
        }

        return count + 1; // found a solution
    }

    private void generatePuzzle() {

        playerBoard = new int[9][9];
        copyBoard(solutionBoard, playerBoard);

        int removals;
        if (difficulty == 1) removals = 30;      // easy
        else if (difficulty == 2) removals = 40; // medium
        else removals = 50;                      // hard

        java.util.Random rand = new java.util.Random();

        while (removals > 0) {
            int row = rand.nextInt(9);
            int col = rand.nextInt(9);

            if (playerBoard[row][col] == 0) continue;

            int backup = playerBoard[row][col];
            playerBoard[row][col] = 0;

            int[][] temp = new int[9][9];
            copyBoard(playerBoard, temp);

            if (countSolutions(temp) != 1) {
                playerBoard[row][col] = backup; // restore
            } else {
                removals--;
            }
        }
    }

    private void displayBoard(int[][] board) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {

                if (board[row][col] != 0) {
                    cells[row][col].setText(String.valueOf(board[row][col]));
                    cells[row][col].setEnabled(false); // lock original
                } else {
                    cells[row][col].setText("");
                    cells[row][col].setEnabled(true);
                }
            }
        }
    }

    private void initNumberButtons() {
        for (int i = 1; i <= 9; i++) {
            int resID = getResources().getIdentifier("n" + i, "id", getPackageName());
            int number = i;

            findViewById(resID).setOnClickListener(v -> {
                if (selectedRow != -1 && selectedCol != -1) {
                    handleInput(number);
                }
            });
        }
    }

    private void handleInput(int number) {

        if (!cells[selectedRow][selectedCol].isEnabled()) return;

        playerBoard[selectedRow][selectedCol] = number;

        cells[selectedRow][selectedCol].setText(String.valueOf(number));

        if (solutionBoard[selectedRow][selectedCol] == number) {
            cells[selectedRow][selectedCol].setTextColor(getResources().getColor(R.color.light_blue2));

        } else {
            // wrong
            if (difficulty == 1) {
                cells[selectedRow][selectedCol].setTextColor(getResources().getColor(R.color.red));
            } else {
                cells[selectedRow][selectedCol].setTextColor(getResources().getColor(R.color.light_blue2));
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