package com.example.tablet_lending_management;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tablet_lending_management.DAL.TabletDao;
import com.example.tablet_lending_management.Models.AppDatabase;
import com.example.tablet_lending_management.Models.CableTypes;
import com.example.tablet_lending_management.Models.Tablet;
import com.example.tablet_lending_management.Models.TabletBrands;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LendTablet extends AppCompatActivity {

    private AppDatabase db;
    private ExecutorService executorService;
    private Spinner spinnerCables;
    private Spinner spinnerTablets;
    private CheckBox checkboxIncludeCable;
    private Button buttonLendTablet;

    private int userId;

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


        Intent intent = getIntent();

        userId = intent.getIntExtra("UserId",0);


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

    }

    private void OnFormSubmit(){

        try{


            TabletDao tabletDao = db.tabletDao();

            Tablet tbl = new Tablet();

            tbl.setCableType((CableTypes) spinnerCables.getSelectedItem());

            tbl.setTabletBrand((TabletBrands) spinnerTablets.getSelectedItem());

            executorService.execute(() -> {
                tabletDao.insertTablet(tbl);
            });

        }
        catch (Exception ex){

        }


    }


}