package com.atreyee.playground.model;

public class PlaySite {
    private String typeOfPlaySite;
    private int rideTime; //rideTime  in minute
    private int workingHours;


    private PlaySiteQueue queue;
    private int totalCapacity;
    private int totalNumberOfKidsPlayed;
    private int totalRounds;

    public PlaySite(String typeOfPlaySite, int capacityPerInstallation, int numberOfInstallation, int rideTime, int workingHours) {
        this.typeOfPlaySite = typeOfPlaySite;
        this.rideTime = rideTime;
        this.workingHours = workingHours;

        this.totalCapacity = capacityPerInstallation * numberOfInstallation;
        this.queue = new PlaySiteQueue(10);
        this.totalNumberOfKidsPlayed = 0;
        this.totalRounds = 0;
    }

    public int addToQueue(Kid kid) {
       return this.queue.add(kid);
    }

    public Kid removeFromQueue(Kid kid) {
        int position = this.queue.isAvailable(kid);
        if (position != -1) {
            return this.queue.remove(position);
        }
        return null;
    }

    public int getKidPosition(Kid kid) {
        int position = this.queue.isAvailable(kid);
        if (position != -1) {
            return position;
        }
        return -1;
    }

    public PlaySiteQueue getQueue() {
        return queue;
    }

    public String getTypeOfPlaySite() {
        return typeOfPlaySite;
    }

    public void setTypeOfPlaySite(String typeOfPlaySite) {
        this.typeOfPlaySite = typeOfPlaySite;
    }

    public void startPlay() {
        //converting total duration to minute
        int totalWorkingDuration = this.workingHours * 60;
        int currentWorkingDuration = 0;

        while (currentWorkingDuration <= totalWorkingDuration && !this.queue.isEmpty()) {
            int kidCountPerRound = 0;
            while (kidCountPerRound < this.totalCapacity && !this.queue.isEmpty()) {
                this.queue.remove();
                kidCountPerRound++;
                this.totalNumberOfKidsPlayed++;
            }
            currentWorkingDuration += this.rideTime;
            this.totalRounds++;
        }
    }

    public double getUtilization() {
        double utilizationPercentage = (((double) this.totalNumberOfKidsPlayed / (this.totalRounds * this.totalCapacity))) * 100;
        return utilizationPercentage;
    }

    public int getTotalNumberOfKidsPlayed() {
        return this.totalNumberOfKidsPlayed;
    }

    @Override
    public String toString() {
        return "PlaySite{" +
                ", queue=" + this.queue.size() +
                ", typeOfPlaySite='" + this.typeOfPlaySite + '\'' +
                ", rideTime=" + this.rideTime +
                ", totalCapacity=" + this.totalCapacity +
                ", totalNumberOfKidsPlayed=" + this.totalNumberOfKidsPlayed +
                ", totalRounds=" + this.totalRounds +
                '}';
    }

    public String getPlaySiteHistoryReport() {
        int totalTime = this.totalRounds * this.rideTime;
        int hours = totalTime/60;
        int minutes = totalTime%60;
        return String.format("%d hrs %d minutes",hours,minutes); // how much time(in hour) playsite is used
    }

}
