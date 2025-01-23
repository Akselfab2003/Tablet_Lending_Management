package com.example.tablet_lending_management.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Tablet {

    @PrimaryKey
    private int tabletId;

    @ColumnInfo(name = "TabletBrand")
    private TabletBrands tabletBrand;

    @ColumnInfo(name = "CableType")
    private CableTypes cableType;

    public TabletBrands getTabletBrand() {
        return tabletBrand;
    }

    public void setTabletBrand(TabletBrands tabletBrand) {
        this.tabletBrand = tabletBrand;
    }

    public CableTypes getCableType() {
        return cableType;
    }

    public void setCableType(CableTypes cableType) {
        this.cableType = cableType;
    }


    public int getTabletId() {
        return tabletId;
    }

    public void setTabletId(int tabletId) {
        this.tabletId = tabletId;
    }
}
