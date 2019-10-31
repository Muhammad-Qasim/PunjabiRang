package com.example.myfyp.Model;

public class InstrumentsM
{

    private String name ;
    private String description;
    private String image_url;
    private String models;
    private String avrgrating;

    public InstrumentsM(String name, String description, String image_url, String models, String avrgrating) {
        this.name = name;
        this.description = description;
        this.image_url = image_url;
        this.models = models;
        this.avrgrating = avrgrating;
    }

    public InstrumentsM() {
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getModels() {
        return models;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public void setModels(String models) {
        this.models = models;
    }

    public String getAvrgrating() {
        return avrgrating;
    }

    public void setAvrgrating(String avrgrating) {
        this.avrgrating = avrgrating;
    }
}
