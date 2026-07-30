package com.example.daniellitvakproject;

public class Comment {
    private String email;
    private String text;

    public Comment() {
    }

    public Comment(String email, String text) {
        this.email = email;
        this.text = text;
    }

    public String getEmail() {

        return email;
    }
    public String getText() {

        return text;
    }
    public void setText(String text) {

        this.text = text;
    }
}
