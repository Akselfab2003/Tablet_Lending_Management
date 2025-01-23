package com.example.tablet_lending_management;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tablet_lending_management.DAL.UserDao;
import com.example.tablet_lending_management.Models.AppDatabase;
import com.example.tablet_lending_management.Models.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

public class LendTabletOverview extends AppCompatActivity {

    private Button CreateUser,CreateLoan;
    private AppDatabase db;
    private ExecutorService executorService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lend_tablet_overview);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = AppDatabase.getInstance(this);
        executorService = Executors.newSingleThreadExecutor();

           CreateUser = findViewById(R.id.btn_Lend_overview_create_user);
           CreateLoan = findViewById(R.id.btn_Lend_overview_create_loan);

           CreateUser.setOnClickListener(v -> {
               Intent createUser = new Intent(this, CreateUser.class);
               startActivity(createUser);
               EnableLendTabletButton();
           });

           CreateLoan.setOnClickListener(v -> {
               Intent lendTablet = new Intent(LendTabletOverview.this, LendTablet.class);
               startActivity(lendTablet);
           });
    }

    private void EnableLendTabletButton(){
        CreateUser.setEnabled(false);
        CreateLoan.setEnabled(true);
    }

}