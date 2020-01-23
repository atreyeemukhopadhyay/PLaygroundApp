package com.atreyee.playground.model;

public class Kid {
    private String name;
    private int age;
    private int ticketNumber;

    public Kid(String name, int age, int ticketNumber, boolean isVip) {
        this.name = name;
        this.age = age;
        this.ticketNumber = ticketNumber;
        this.isVip = isVip;
    }

    public boolean isVip() {
        return isVip;
    }

    private boolean isVip;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(int ticketNumber) {
        this.ticketNumber = ticketNumber;
    }
}
