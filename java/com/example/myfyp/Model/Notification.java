package com.example.myfyp.Model;
public class Notification {
    private String notificationid;
    private String userid;
    private String text;
    private String postid;
    private boolean ispost;
    private String rank;
    private boolean viewed;

    private String str_viewed;

    public Notification(String notficationid, String userid, String text, String postid, boolean ispost, String rank, boolean viewed, String str_viewed) {
        this.notificationid = notficationid;
        this.userid = userid;
        this.text = text;
        this.postid = postid;
        this.ispost = ispost;
        this.rank = rank;
        this.viewed= viewed;

        this.str_viewed= str_viewed;

    }

    public Notification() {
    }

    public String getNotificationId() {
        return notificationid;
    }

    public void setNotificationId(String notificationId) {
        this.notificationid = notificationId;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public boolean isIspost() {
        return ispost;
    }

    public void setIspost(boolean ispost) {
        this.ispost = ispost;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public boolean isViewed() {
        return viewed;
    }

    public void setViewed(boolean viewed) {
        this.viewed = viewed;
    }

    public String getStr_viewed() {
        return str_viewed;
    }

    public void setStr_viewed(String str_viewed) {
        this.str_viewed = str_viewed;
    }
}
