package com.example.onlinenewsmobile.models;

import java.io.Serializable;

public class NewspaperDTO implements Serializable {
    private int id;
    private String name;
    private String imageBase64;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }
}
