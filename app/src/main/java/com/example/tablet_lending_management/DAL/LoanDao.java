package com.example.tablet_lending_management.DAL;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.tablet_lending_management.Models.Loan;

import java.util.List;

@Dao
public interface LoanDao {
    @Insert
    void insertLoan(Loan loan);

    @Update
    void updateLoan(Loan loan);

    @Delete
    void deleteLoan(Loan loan);

    @Query("SELECT * FROM Loan WHERE id = :loanId")
    Loan getLoanById(int loanId);

    @Query("SELECT * FROM Loan")
    List<Loan> getAllLoans();

    @Query("SELECT * FROM Loan WHERE userId = :userId")
    List<Loan> getLoansByUserId(int userId);

    @Query("SELECT * FROM Loan WHERE tabletId = :tabletId")
    List<Loan> getLoansByTabletId(int tabletId);
}
