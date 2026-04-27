package com.example.homeschoolingjava1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button B1, B2, B3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ===== Hide the status bar =====
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        );

        // Hide ActionBar if it exists
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        setContentView(R.layout.activity_main);
        initBit();
    }
    public void initBit(){
        B1 = findViewById(R.id.B1);
        B2 = findViewById(R.id.B2);
        B3 = findViewById(R.id.B3);

    }

    public void openSettings(View v){
        Intent i = new Intent(this, SettingsActivity.class);
        startActivity(i);
    }
    public void openStatus(View v){
        Intent b = new Intent(this, statusActivity.class);
        startActivity(b);
    }

    public void openGame(View v){
        Intent i = new Intent(this, GameActivity.class);
        startActivity(i);
    }

    public void openHistory(View v){
        Intent i = new Intent(this, HistoryActivity.class);
        startActivity(i);
    }

    public void openYoutube(View v){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=dQw4w9WgXcQ"));
        startActivity(intent);
    }
    public void openFacebook(View v){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/chiro.kamaran"));
        startActivity(intent);
    }
    public void openInstagram(View v){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/rivan_iraq/"));
        startActivity(intent);
    }
}
