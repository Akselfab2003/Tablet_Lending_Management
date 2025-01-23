package com.example.tablet_lending_management.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;

// Creates a Loan Table that contains foreign keys to the User and the Tablet tables

@Entity(foreignKeys = {
    @ForeignKey(entity = User.class,parentColumns = "userId",childColumns = "userId",onDelete = ForeignKey.CASCADE),
    @ForeignKey(entity = Tablet.class,parentColumns = "tabletId",childColumns = "tabletId",onDelete = ForeignKey.CASCADE)

})
public class Loan
{
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int userId;
    private int tabletId;
    private boolean isCableIncluded;
    private LocalDateTime timeOfLoan;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTabletId() {
        return tabletId;
    }

    public void setTabletId(int tabletId) {
        this.tabletId = tabletId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isCableIncluded() {
        return isCableIncluded;
    }

    public void setCableIncluded(boolean cableIncluded) {
        isCableIncluded = cableIncluded;
    }

    public LocalDateTime getTimeOfLoan() {
        return timeOfLoan;
    }

    public void setTimeOfLoan(LocalDateTime timeOfLoan) {
        this.timeOfLoan = timeOfLoan;
    }

}
