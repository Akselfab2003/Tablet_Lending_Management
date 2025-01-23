package com.example.tablet_lending_management;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tablet_lending_management.DAL.LoanDetailsDao;
import com.example.tablet_lending_management.Models.AppDatabase;
import com.example.tablet_lending_management.Models.CableTypes;
import com.example.tablet_lending_management.Models.LoanWithDetails;
import com.example.tablet_lending_management.Models.TabletBrands;
import com.example.tablet_lending_management.utils.LoanDetailsAdapter;

import java.text.DateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class AdminDashboard extends AppCompatActivity {

    private ExecutorService executorService;
    private AppDatabase db;
    private RecyclerView recyclerViewLoanDetails;
    private LoanDetailsAdapter loanDetailsAdapter;
    private List<LoanWithDetails> loanDetailsList;
    private Spinner FilterOptions,FilterValueOptions;

    private EditText FilterByDate;

    private Button ApplyFilter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        loanDetailsAdapter = new LoanDetailsAdapter(new ArrayList<>());

        db = AppDatabase.getInstance(this);
        executorService = Executors.newSingleThreadExecutor();

        recyclerViewLoanDetails = findViewById(R.id.recyclerViewLoanDetails);
        recyclerViewLoanDetails.setAdapter(loanDetailsAdapter);
        recyclerViewLoanDetails.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewLoanDetails.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        FilterOptions = findViewById(R.id.FilterOptions);
        FilterValueOptions = findViewById(R.id.FilterValueOptions);
        FilterByDate = findViewById(R.id.FilterByDate);
        ApplyFilter = findViewById(R.id.ApplyFilter);

        List<String> FilterOptionsValues = new ArrayList<String>();
        FilterOptionsValues.add("None");
        FilterOptionsValues.add("Brands");
        FilterOptionsValues.add("CableType");

        ArrayAdapter<String> FilterOptionsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, FilterOptionsValues);
        FilterOptionsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        FilterOptions.setAdapter(FilterOptionsAdapter);


        FilterOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String option = FilterOptionsAdapter.getItem(position);
                UpdateFilterValuesSpinner(option);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



        ApplyFilter.setOnClickListener(v -> {
            filterlist(loanDetailsList);
        });

        fetchLoanDetailsFromDatabase();
    }

    private void fetchLoanDetailsFromDatabase() {
        executorService.submit(()-> {
            LoanDetailsDao detailsDao = db.loanDetailsDao();

            List<LoanWithDetails>  listofLoans =  detailsDao.getAllLoansWithDetails();


            if(listofLoans != null)
            {
                runOnUiThread(()->{
                    loanDetailsList = listofLoans;
                   loanDetailsAdapter.setLoanWithDetailsList(listofLoans);
                });
            }
        });
    }

    private void UpdateFilterValuesSpinner(String option)
    {
        switch (option)
        {
            case "Brands":
            {
                ArrayAdapter<TabletBrands> tabletBrandAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, TabletBrands.values());
                tabletBrandAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                FilterValueOptions.setAdapter(tabletBrandAdapter);
                break;
            }
            case "CableType":
            {
                ArrayAdapter<CableTypes> cableTypesArrayAdapter= new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, CableTypes.values());
                cableTypesArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                FilterValueOptions.setAdapter(cableTypesArrayAdapter);
                break;
            }
            case "None":
            {
                FilterValueOptions.setAdapter( new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,new ArrayList<>()));
                break;
            }
        }
    }


    private void filterlist(List<LoanWithDetails>  listofLoans)
    {


        String option = FilterOptions.getSelectedItem().toString();
        String Selectedvalue;

        if(FilterValueOptions.getSelectedItem() != null)
        {
            Selectedvalue = FilterValueOptions.getSelectedItem().toString();
        } else {
            Selectedvalue = "";
        }

        String Date = FilterByDate.getText().toString();

        List<LoanWithDetails> filtered = new ArrayList<LoanWithDetails>();
        if(!option.isEmpty())
        {
            switch (option)
            {
                case "Brands":
                {
                    if(!Selectedvalue.isEmpty()){
                        filtered =  listofLoans.stream()
                                .filter(item -> item.tablet.getTabletBrand().toString().equals(Selectedvalue)).collect(Collectors.toList());
                    }
                    break;
                }
                case "CableType":
                {
                    if(!Selectedvalue.isEmpty()){
                        filtered = listofLoans.stream()
                                .filter(item -> item.tablet.getCableType().toString().equals(Selectedvalue)).collect(Collectors.toList());
                    }
                    break;
                }
                case "None":
                {
                    filtered = listofLoans;
                }
            }

        }




        if(!Date.isEmpty())
        {
            filtered = filtered.stream().filter(item -> item.loan.getTimeOfLoan().toLocalDate().toString().equals(Date)).collect(Collectors.toList());
        }

        loanDetailsAdapter.setLoanWithDetailsList(filtered);
    }
}