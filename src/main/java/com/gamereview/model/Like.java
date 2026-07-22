package com.gamereview.model;

public class Like {

    private int id;
    private int profileId;
    private int reviewId;

    public Like() {
    }

    public Like(int profileId, int reviewId) {
        this.profileId = profileId;
        this.reviewId = reviewId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }
}