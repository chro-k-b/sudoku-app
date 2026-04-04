package com.example.homeschoolingjava1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.homeschoolingjava1.R;

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

//    @Override
//    public void onClick(View v) {
//        int id = v.getId();
//        if (id == R.id.B1) {
//            Toast.makeText(this, "B1 is clicked", Toast.LENGTH_SHORT).show();
//        } else if (id == R.id.B2) {
//            Toast.makeText(this, "B2 is clicked", Toast.LENGTH_SHORT).show();
//        } else if (id == R.id.B3) {
//            Toast.makeText(this, "B3 is clicked", Toast.LENGTH_SHORT).show();
//        }
//    }
}