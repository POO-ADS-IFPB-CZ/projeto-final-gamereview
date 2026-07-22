package com.gamereview.model;

import java.time.LocalDate;

public class Comment {

    private int id;
    private String text;
    private LocalDate commentDate;
    private int profileId;
    private int reviewId;

    public Comment() {
    }

    public Comment(String text, LocalDate commentDate,
                   int profileId, int reviewId) {
        this.text = text;
        this.commentDate = commentDate;
        this.profileId = profileId;
        this.reviewId = reviewId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDate getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(LocalDate commentDate) {
        this.commentDate = commentDate;
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