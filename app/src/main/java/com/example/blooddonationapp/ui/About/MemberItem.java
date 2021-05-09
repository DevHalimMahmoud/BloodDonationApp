package com.example.blooddonationapp.ui.About;

public class MemberItem {
    String
            Name,
            id;
    int img;

    public MemberItem() {
    }

    public MemberItem(String name, String id, int img) {
        this.Name = name;
        this.id = id;
        this.img = img;
    }

    public String getName() {
        return Name;
    }

    public String getId() {
        return id;
    }

    public int getImg() {
        return img;
    }
}
