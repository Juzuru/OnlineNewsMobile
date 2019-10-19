package com.example.onlinenewsmobile.models;

import java.io.Serializable;

public class CategoryDTO implements Serializable {
    private int id;
    private String name;
    private boolean visible;

    public CategoryDTO(int id, String name, boolean visible) {
        this.id = id;
        this.name = name;
        this.visible = visible;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

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
}
