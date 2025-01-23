package com.example.tablet_lending_management.utils;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tablet_lending_management.Models.LoanWithDetails;
import com.example.tablet_lending_management.R;

import java.util.List;

public class LoanDetailsAdapter extends RecyclerView.Adapter<LoanDetailsAdapter.LoanWithDetailsViewHolder> {

    private List<LoanWithDetails> loanDetailsList;

    public LoanDetailsAdapter(List<LoanWithDetails> LoanWithDetailsList) {
        this.loanDetailsList = LoanWithDetailsList;
    }

    @NonNull
    @Override
    public LoanWithDetailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loan_details, parent, false);
        return new LoanWithDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LoanWithDetailsViewHolder holder, int position) {
        LoanWithDetails LoanWithDetails = loanDetailsList.get(position);
        holder.textViewUserName.setText("Name: " + LoanWithDetails.user.getName());
        holder.textViewUserEmail.setText("Email: " + LoanWithDetails.user.getEmail());
        holder.textViewUserPhone.setText("Phone: " + LoanWithDetails.user.getPhoneNumber());
        holder.textViewTabletBrand.setText("Tablet Brand: " + LoanWithDetails.tablet.getTabletBrand());
        holder.textViewCableType.setText("Cable Type: " + LoanWithDetails.tablet.getCableType());
        holder.textViewCableIncluded.setText("Cable Included: " + (LoanWithDetails.loan.isCableIncluded() ? "Yes" : "No"));
        holder.textViewTimeOfLoan.setText("Time of Loan: " + LoanWithDetails.loan.getTimeOfLoan().toString());
    }

    @Override
    public int getItemCount() {
        return loanDetailsList.size();
    }

    static class LoanWithDetailsViewHolder extends RecyclerView.ViewHolder {
        TextView textViewUserName;
        TextView textViewUserEmail;
        TextView textViewUserPhone;
        TextView textViewTabletBrand;
        TextView textViewCableType;
        TextView textViewCableIncluded;
        TextView textViewTimeOfLoan;

        LoanWithDetailsViewHolder(View itemView) {
            super(itemView);
            textViewUserName = itemView.findViewById(R.id.textViewUserName);
            textViewUserEmail = itemView.findViewById(R.id.textViewUserEmail);
            textViewUserPhone = itemView.findViewById(R.id.textViewUserPhone);
            textViewTabletBrand = itemView.findViewById(R.id.textViewTabletBrand);
            textViewCableType = itemView.findViewById(R.id.textViewCableType);
            textViewCableIncluded = itemView.findViewById(R.id.textViewCableIncluded);
            textViewTimeOfLoan = itemView.findViewById(R.id.textViewTimeOfLoan);
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    public void setLoanWithDetailsList(List<LoanWithDetails> loanDetailsList) {
        this.loanDetailsList = loanDetailsList;
        notifyDataSetChanged();
    }


}