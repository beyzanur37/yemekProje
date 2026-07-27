package com.example.yemekproje;

public class Comment {
    private String userName;
    private String commentText;
    private long timestamp;

    public Comment() {}

    public Comment(String userName, String commentText, long timestamp) {
        this.userName = userName;
        this.commentText = commentText;
        this.timestamp = timestamp;
    }

    public String getUserName() { return userName; }
    public String getCommentText() { return commentText; }
    public long getTimestamp() { return timestamp; }
}