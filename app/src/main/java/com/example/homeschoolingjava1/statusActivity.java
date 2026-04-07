package com.example.homeschoolingjava1;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class statusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_status);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            //there was some extra padding in the bottom i removed specifically from this activity cuz it looks ugly
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });
    }
    //this method is here so we can close setting intent when we finish
    //it does nothing other than closing the setting intent
    //it needs to also save the current settings when it closes
    public void goBack(View v){
        finish();
    }


    FragmentManager fragManager = getSupportFragmentManager();

    public void showEasyStatus(View v){
        FragmentTransaction transaction = fragManager.beginTransaction();
        EasyStatusFragment easyStatusFragment = new EasyStatusFragment();
        transaction.replace(R.id.fragmentContainerView, easyStatusFragment);
        transaction.commit();
    }

    public void showMediumStatus(View v){
        FragmentTransaction transaction = fragManager.beginTransaction();
        MediumStatusFragment mediumStatusFragment = new MediumStatusFragment();
        transaction.replace(R.id.fragmentContainerView, mediumStatusFragment);
        transaction.commit();
    }

    public void showHardStatus(View v){
        FragmentTransaction transaction = fragManager.beginTransaction();
        HardStatusFragment hardStatusFragment = new HardStatusFragment();
        transaction.replace(R.id.fragmentContainerView, hardStatusFragment);
        transaction.commit();
    }

}