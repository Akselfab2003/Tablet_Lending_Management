package com.example.tablet_lending_management.DAL;

import androidx.room.Query;
import androidx.room.Transaction;

import com.example.tablet_lending_management.Models.LoanWithDetails;

import java.util.List;

public interface LoanDetailsDao {
    @Transaction
    @Query("SELECT * FROM Loan")
    List<LoanWithDetails> getAllLoansWithDetails();

    @Transaction
    @Query("SELECT * FROM Loan WHERE userId = :userId")
    List<LoanWithDetails> getLoansWithDetailsByUserId(int userId);

    @Transaction
    @Query("SELECT * FROM Loan WHERE tabletId = :tabletId")
    List<LoanWithDetails> getLoansWithDetailsByTabletId(int tabletId);
}
