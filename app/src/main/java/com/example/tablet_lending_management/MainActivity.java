package com.example.tablet_lending_management;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {


    private Button LendTablet,AdminView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Added Dark mode to follow system preferences
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        LendTablet = findViewById(R.id.btn_LendTablet);
        AdminView = findViewById(R.id.btn_AdminView);

        LendTablet.setOnClickListener(v ->
        {
            Intent intent = new Intent(MainActivity.this,com.example.tablet_lending_management.LendTabletOverview.class);

            startActivity(intent);
        });

        AdminView.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,AdminLogin.class);

            startActivity(intent);
        });

    }
}