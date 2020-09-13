package com.ss.gamoney;

public class model_csgo {
    String price, prize, image, description, time;

    model_csgo() {
        //Blank constructor
    }

    public model_csgo(String price, String prize, String image, String description, String time) {
        this.price = price;
        this.prize = prize;
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

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize;
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
