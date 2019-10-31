package com.example.myfyp.Model;

public class Ranking {
    private String username;
    private int no_of_posts;
    private String imageUrl;

    public Ranking(String username, int no_of_posts, String imageUrl) {
        this.username = username;
        this.no_of_posts = no_of_posts;
        this.imageUrl = imageUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getNo_of_posts() {
        return no_of_posts;
    }

    public void setNo_of_posts(int no_of_posts) {
        this.no_of_posts = no_of_posts;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
