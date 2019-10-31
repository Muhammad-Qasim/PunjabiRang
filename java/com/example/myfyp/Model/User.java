package com.example.myfyp.Model;

public class User {
    private String id;
    private String username;
    private String Email;
    private String imageurl;
    private String Password;
    private String new_password;
    private String old_password;
    private String Phoneno;


    public User(String id, String username, String Email, String imageUrl, String Password, String new_password, String old_password, String Phoneno) {
        this.id = id;
        this.username = username;
        this.Email = Email;
        this.imageurl = imageUrl;
        this.Phoneno= Phoneno;
        this.Password= Password;
        this.new_password = new_password;
        this.old_password = old_password;

    }

    public User() {
    }


    public String getPhoneno() {
        return Phoneno;
    }

    public void setPhoneno(String Phoneno) {
        this.Phoneno = Phoneno;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }


    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getNew_password() {
        return new_password;
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
    }

    public String getOld_password() {
        return old_password;
    }

    public void setOld_password(String old_password) {
        this.old_password = old_password;
    }


}

