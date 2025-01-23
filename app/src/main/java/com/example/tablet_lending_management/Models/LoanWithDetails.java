package com.example.tablet_lending_management.Models;

import androidx.room.Embedded;
import androidx.room.Relation;

public class LoanWithDetails {
    @Embedded
    public Loan loan;
    @Relation(
            parentColumn = "userId",
            entityColumn = "userId"
    )
    public User user;

    @Relation(
            parentColumn = "tabletId",
            entityColumn = "tabletId"
    )
    public Tablet tablet;
}
