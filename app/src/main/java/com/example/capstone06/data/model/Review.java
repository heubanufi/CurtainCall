package com.example.capstone06.data.model;

public class Review {

    private String performanceName;
    private String theater;
    private String date;
    private String showtime;
    private String seat;
    public String with;
    public double rating;

    public Review() {
        // Default constructor required for Firestore
    }

    public Review(String performanceName, String theater, String date, String showtime, String seat) {
        this.performanceName = performanceName;
        this.theater = theater;
        this.date = date;
        this.showtime = showtime;
        this.seat = seat;
    }

    public String getPerformanceName() {
        return performanceName;
    }

    public String getTheater() {
        return theater;
    }

    public String getDate() {
        return date;
    }

    public String getShowtime() {
        return showtime;
    }

    public String getSeat() {
        return seat;
    }
    public String getWith() { return with; }
    public double getRating() { return rating; }
}

