package com.example.tablet_lending_management.DAL;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.tablet_lending_management.Models.Tablet;

import java.util.List;

@Dao
public interface TabletDao {
    @Insert
    void insertTablet(Tablet tablet);

    @Update
    void updateTablet(Tablet tablet);

    @Delete
    void deleteTablet(Tablet tablet);

    @Query("SELECT * FROM Tablet WHERE tabletId = :tabletId")
    Tablet getTabletById(int tabletId);

    @Query("SELECT * FROM Tablet")
    List<Tablet> getAllTablets();

}
