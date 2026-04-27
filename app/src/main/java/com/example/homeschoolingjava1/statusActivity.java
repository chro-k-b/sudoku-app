package com.example.homeschoolingjava1;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.concurrent.Executors;

public class statusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_status);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        // Initialize with Easy status by default
        showEasyStatus(null);
    }

    public void goBack(View v){
        finish();
    }

    public void resetStatus(View v) {
        Executors.newSingleThreadExecutor().execute(() -> {
            AppDatabase.getInstance(this).gameHistoryDao().deleteAll();
            runOnUiThread(() -> {
                Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
                if (currentFragment instanceof EasyStatusFragment) {
                    showEasyStatus(null);
                } else if (currentFragment instanceof MediumStatusFragment) {
                    showMediumStatus(null);
                } else if (currentFragment instanceof HardStatusFragment) {
                    showHardStatus(null);
                }
            });
        });
    }

    FragmentManager fragManager = getSupportFragmentManager();

    public void showEasyStatus(View v){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        EasyStatusFragment easyStatusFragment = new EasyStatusFragment();
        transaction.replace(R.id.fragmentContainerView, easyStatusFragment);
        transaction.commit();
    }

    public void showMediumStatus(View v){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        MediumStatusFragment mediumStatusFragment = new MediumStatusFragment();
        transaction.replace(R.id.fragmentContainerView, mediumStatusFragment);
        transaction.commit();
    }

    public void showHardStatus(View v){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        HardStatusFragment hardStatusFragment = new HardStatusFragment();
        transaction.replace(R.id.fragmentContainerView, hardStatusFragment);
        transaction.commit();
    }
}
