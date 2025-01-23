package com.example.tablet_lending_management;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tablet_lending_management.DAL.UserDao;
import com.example.tablet_lending_management.Models.AppDatabase;
import com.example.tablet_lending_management.Models.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

public class CreateUser extends AppCompatActivity {

    private EditText userName,userEmail,userPhone;
    private Button CreateUser;

    private AppDatabase db;
    private ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        db = AppDatabase.getInstance(this);

       userName  = findViewById(R.id.input_user_name);
       userEmail = findViewById(R.id.input_user_email);
       userPhone = findViewById(R.id.input_phone_number);

       CreateUser = findViewById(R.id.button_create_user);

       executorService = Executors.newSingleThreadExecutor();

       CreateUser.setOnClickListener(v -> OnFormSubmit());

    }

    private void OnFormSubmit()
    {
        try{

            User usr = new User();
            usr.setName(userName.getText().toString());

            if(!Patterns.EMAIL_ADDRESS.matcher(userEmail.getText()).matches())
            {
                throw new  InvalidEmailException("Invalid email");
            }

            usr.setEmail(userEmail.getText().toString());



            usr.setPhoneNumber(userPhone.getText().toString());

            executorService.execute(() -> {
                UserDao userDB = db.userDao();

                userDB.insertUser(usr);

            });
            finish();

        }
        catch (Exception ex)
        {
            AlertDialogHelper.showDialog(com.example.tablet_lending_management.CreateUser.this,
                    "Error Occurred",
                    ex.getMessage(),
                    "Retry",
                    "Menu",
                    null,
                    null
                    );

        }

    }

    public static class InvalidEmailException extends Exception {
        public InvalidEmailException(String message) {
            super(message);
        }
    }
}