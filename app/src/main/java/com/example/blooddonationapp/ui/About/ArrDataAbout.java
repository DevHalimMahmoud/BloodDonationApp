package com.example.blooddonationapp.ui.About;

public class ArrDataAbout {
    String Name ,ID;
    int img;

    public ArrDataAbout(String name,String ID,int img) {
        this.Name = name;
        this.ID = ID;
        this.img = img;
    }

    public String getName() {
        return Name;
    }

    public String getID() {
        return ID;
    }

    public int getImg() {
        return img;
    }
}
