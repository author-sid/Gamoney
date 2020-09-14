package com.ss.gamoney;

public class model_freefire {
    String price, image, description, time;

    model_freefire() {
        //Blank Contructor
    }

    public model_freefire(String price, String image, String description, String time) {
        this.price = price;
        this.image = image;
        this.description = description;
        this.time = time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
