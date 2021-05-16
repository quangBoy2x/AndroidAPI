package com.example.apibtl;

public class Weather {
    String day;
    String status;
    String imgStatus;
    String MaxTemp;
    String MinTemp;

    public Weather(String day, String status, String imgStatus, String maxTemp, String minTemp) {
        this.day = day;
        this.status = status;
        this.imgStatus = imgStatus;
        MaxTemp = maxTemp;
        MinTemp = minTemp;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImgStatus() {
        return imgStatus;
    }

    public void setImgStatus(String imgStatus) {
        this.imgStatus = imgStatus;
    }

    public String getMaxTemp() {
        return MaxTemp;
    }

    public void setMaxTemp(String maxTemp) {
        MaxTemp = maxTemp;
    }

    public String getMinTemp() {
        return MinTemp;
    }

    public void setMinTemp(String minTemp) {
        MinTemp = minTemp;
    }
}
