package com.ss.gamoney;

public class model_pubg {
    String price, image,time, map, date , month, tournament;

    model_pubg() {
        //Blank constructor
    }

    public model_pubg(String price, String image, String time, String map, String date, String month, String tournament) {
        this.price = price;
        this.image = image;
        this.time = time;
        this.map = map;
        this.date = date;
        this.month = month;
        this.tournament = tournament;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getTournament() {
        return tournament;
    }

    public void setTournament(String tournament) {
        this.tournament = tournament;
    }
}
