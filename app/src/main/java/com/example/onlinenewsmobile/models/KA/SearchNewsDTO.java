package com.example.onlinenewsmobile.models.KA;

import java.io.Serializable;
import java.time.LocalDateTime;

public class SearchNewsDTO implements Serializable {
    private long id;
    private String title;
    private LocalDateTime timer;
    private String newspaper;
    private String imgTitle;

    public SearchNewsDTO() {
    }

    public SearchNewsDTO(long id, String title, LocalDateTime timer, String newspaper, String imgTitle) {
        this.id = id;
        this.title = title;
        this.timer = timer;
        this.newspaper = newspaper;
        this.imgTitle = imgTitle;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getTimer() {
        return timer;
    }

    public void setTimer(LocalDateTime timer) {
        this.timer = timer;
    }

    public String getNewspaper() {
        return newspaper;
    }

    public void setNewspaper(String newspaper) {
        this.newspaper = newspaper;
    }

    public String getImgTitle() {
        return imgTitle;
    }

    public void setImgTitle(String imgTitle) {
        this.imgTitle = imgTitle;
    }
}
