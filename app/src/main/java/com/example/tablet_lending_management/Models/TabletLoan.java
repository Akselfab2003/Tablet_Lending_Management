package com.example.tablet_lending_management.Models;

import java.time.LocalDateTime;

public class TabletLoan
{
    private int id;
    private TabletBrands tabletBrand;
    private CableTypes cableType;
    private User loaner;
    private boolean isCableIncluded;
    private LocalDateTime timeOfLoan;

}
