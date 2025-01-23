package com.example.tablet_lending_management;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tablet_lending_management.DAL.LoanDao;
import com.example.tablet_lending_management.DAL.LoanDetailsDao;
import com.example.tablet_lending_management.DAL.LoanDetailsDao_Impl;
import com.example.tablet_lending_management.DAL.TabletDao;
import com.example.tablet_lending_management.DAL.UserDao;
import com.example.tablet_lending_management.Models.AppDatabase;
import com.example.tablet_lending_management.Models.CableTypes;
import com.example.tablet_lending_management.Models.Loan;
import com.example.tablet_lending_management.Models.LoanWithDetails;
import com.example.tablet_lending_management.Models.Tablet;
import com.example.tablet_lending_management.Models.TabletBrands;
import com.example.tablet_lending_management.Models.User;

import java.time.LocalDateTime;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class LendTablet extends AppCompatActivity {

    private AppDatabase db;
    private ExecutorService executorService;
    private Spinner spinnerCables;
    private Spinner spinnerTablets;
    private CheckBox checkboxIncludeCable;
    private Button buttonLendTablet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lend_tablet);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = AppDatabase.getInstance(this);
        executorService = Executors.newSingleThreadExecutor();


        spinnerCables = findViewById(R.id.spinner_cables);
        spinnerTablets = findViewById(R.id.spinner_tablets);
        checkboxIncludeCable = findViewById(R.id.checkbox_include_cable);
        buttonLendTablet = findViewById(R.id.button_lend_tablet);

        ArrayAdapter<TabletBrands> tabletBrandAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, TabletBrands.values());
        tabletBrandAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTablets.setAdapter(tabletBrandAdapter);

        ArrayAdapter<CableTypes> cableTypesArrayAdapter= new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, CableTypes.values());
        cableTypesArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCables.setAdapter(cableTypesArrayAdapter);

        buttonLendTablet.setOnClickListener(v-> {
            OnFormSubmit();
                }
        );
    }
// Creates the Loan and tablet in the database
    private void OnFormSubmit(){
        try{
            TabletDao tabletDao = db.tabletDao();

            Tablet tbl = new Tablet();

            tbl.setCableType((CableTypes) spinnerCables.getSelectedItem());

            tbl.setTabletBrand((TabletBrands) spinnerTablets.getSelectedItem());

            executorService.submit(() -> {
                tabletDao.insertTablet(tbl);
            });

            Loan loan = new Loan();

            loan.setTimeOfLoan(LocalDateTime.now());
            loan.setCableIncluded(checkboxIncludeCable.isChecked());

            executorService.submit(() -> {
                LoanDao loanDao = db.loanDao();
                UserDao userDao = db.userDao();

                Tablet tablet = tabletDao.getLastCreatedTablet();

                User user = userDao.getLastCreatedUser();

                loan.setTabletId(tablet.getTabletId());

                loan.setUserId(user.getUserId());

                loanDao.insertLoan(loan);

                runOnUiThread(()-> {
                    showLoanDetailsDialog(loan,tbl,user);
                });
            });
        }
        catch (Exception ex){
            AlertDialogHelper.showDialog(this,
                    "Error Occurred",
                    ex.getMessage(),
                    "Retry",
                    "Menu",
                    null,
                    null
            );
        }
    }
// Shows the Loan Request receipt
    private void showLoanDetailsDialog(Loan loan,Tablet tablet,User user) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_loan_details, null);
        builder.setView(dialogView);

        TextView textViewUserName = dialogView.findViewById(R.id.textViewUserName);
        TextView textViewUserEmail = dialogView.findViewById(R.id.textViewUserEmail);
        TextView textViewUserPhone = dialogView.findViewById(R.id.textViewUserPhone);
        TextView textViewTabletBrand = dialogView.findViewById(R.id.textViewTabletBrand);
        TextView textViewCableType = dialogView.findViewById(R.id.textViewCableType);
        TextView textViewCableIncluded = dialogView.findViewById(R.id.textViewCableIncluded);
        TextView textViewTimeOfLoan = dialogView.findViewById(R.id.textViewTimeOfLoan);

        textViewUserName.setText("Name: " + user.getName());
        textViewUserEmail.setText("Email: " + user.getEmail());
        textViewUserPhone.setText("Phone: " +user.getPhoneNumber());
        textViewTabletBrand.setText("Tablet Brand: " + tablet.getTabletBrand());
        textViewCableType.setText("Cable Type: " + tablet.getCableType());
        textViewCableIncluded.setText("Cable Included: " + (loan.isCableIncluded() ? "Yes" : "No"));
        textViewTimeOfLoan.setText("Time of Loan: " + loan.getTimeOfLoan().toString());

        builder.setTitle("Loan Details");

        builder.setPositiveButton("OK",(dialog,id) ->  finish());
        builder
                .create()
                .show();
    }
}