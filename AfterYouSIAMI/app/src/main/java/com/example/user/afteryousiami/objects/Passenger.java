package com.example.user.afteryousiami.objects;

public class Passenger {

    private String firstname;
    private String lastname;
    private String bookingClass;        //"ECONOMY", "BUSINESS
    private String flightClass;         //K, H, B
    private String kfNumber;
    private String KFTier;
    private String checkinStatus;

    public Passenger(String firstname, String lastname, String bookingClass, String kfNumber, String KFTier, String checkinStatus) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.bookingClass = bookingClass;
        this.kfNumber = kfNumber;
        this.KFTier = KFTier;
        this.checkinStatus = checkinStatus;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getBookingClass() {
        return bookingClass;
    }

    public void setBookingClass(String bookingClass) {
        this.bookingClass = bookingClass;
    }

    public String getKfNumber() {
        return kfNumber;
    }

    public void setKfNumber(String kfNumber) {
        this.kfNumber = kfNumber;
    }

    public String getKFTier() {
        return KFTier;
    }

    public void setKFTier(String KFTier) {
        this.KFTier = KFTier;
    }

    public String getCheckinStatus() {
        return checkinStatus;
    }

    public void setCheckinStatus(String checkinStatus) {
        this.checkinStatus = checkinStatus;
    }



    @Override
    public String toString() {
        return "Passenger{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", bookingClass='" + bookingClass + '\'' +
                ", flightClass='" + flightClass + '\'' +
                ", kfNumber='" + kfNumber + '\'' +
                ", KFTier='" + KFTier + '\'' +
                ", checkinStatus='" + checkinStatus + '\'' +
                '}';
    }
}
