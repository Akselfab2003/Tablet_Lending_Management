package com.example.tablet_lending_management.Models;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.example.tablet_lending_management.Converters;
import com.example.tablet_lending_management.DAL.LoanDao;
import com.example.tablet_lending_management.DAL.LoanDetailsDao;
import com.example.tablet_lending_management.DAL.TabletDao;
import com.example.tablet_lending_management.DAL.UserDao;

// Sets up the Database Context and the Data Access Objects
@Database(entities = {Loan.class,Tablet.class,User.class},version = 2)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract LoanDao loanDao();
    public abstract LoanDetailsDao loanDetailsDao();
    public abstract TabletDao tabletDao();
    private static volatile AppDatabase INSTANCE;
    public static AppDatabase getInstance(Context context)
    {
        if(INSTANCE == null){
            synchronized (AppDatabase.class){
                if(INSTANCE == null)
                {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class,
                                    "TabletLoanDatabase")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }


}
