package com.gamereview.model;

import java.time.LocalDate;

public class Review {

    private int id;
    private String gameTitle;
    private String platform;
    private double rating;
    private String text;
    private LocalDate reviewDate;
    private int profileId;

    public Review() {
    }

    public Review(String gameTitle, String platform, double rating, String text,
                  LocalDate reviewDate, int profileId) {
        this.gameTitle = gameTitle;
        this.platform = platform;
        this.rating = rating;
        this.text = text;
        this.reviewDate = reviewDate;
        this.profileId = profileId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGameTitle() {
        return gameTitle;
    }

    public void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDate getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDate reviewDate) {
        this.reviewDate = reviewDate;
    }

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }
}