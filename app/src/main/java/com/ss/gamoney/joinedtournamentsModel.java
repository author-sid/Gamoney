package com.ss.gamoney;

public class joinedtournamentsModel {
    String time,date , month, tournament,image,location;

    joinedtournamentsModel(){

    }

    public joinedtournamentsModel(String time, String date, String month, String tournament, String image, String location) {
        this.time = time;
        this.date = date;
        this.month = month;
        this.tournament = tournament;
        this.image = image;
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
