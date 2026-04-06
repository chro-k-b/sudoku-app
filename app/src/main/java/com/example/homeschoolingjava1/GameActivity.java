package com.example.homeschoolingjava1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

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
        finish();
    }

    public void openSettings(View v){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void openMainMenu(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}