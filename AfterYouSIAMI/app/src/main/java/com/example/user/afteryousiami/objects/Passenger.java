package com.example.user.afteryousiami.objects;

public class Passenger {

    private String firstname;
    private String lastname;
    private String bookingClass;        //"ECONOMY", "BUSINESS
    private String flightClass;         //K, H, B
    private int kfNumber;
    private String KFTier;
    private String checkinStatus;
    private String bookingID;
    private String email;
    private String phone;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFlightClass() {
        return flightClass;
    }

    public void setFlightClass(String flightClass) {
        this.flightClass = flightClass;
    }

    public String getBookingID() {
        return bookingID;
    }

    public void setBookingID(String bookingID) {
        this.bookingID = bookingID;
    }

    public Passenger(String firstname, String lastname, String bookingClass, int kfNumber, String KFTier, String checkinStatus, String bookingID, String phone, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.bookingClass = bookingClass;
        this.bookingID = bookingID;
        this.kfNumber = kfNumber;
        this.KFTier = KFTier;
        this.checkinStatus = checkinStatus;
        this.phone = phone;
        this.email = email;
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

    public int getKfNumber() {
        return kfNumber;
    }

    public void setKfNumber(int kfNumber) {
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
                ", kfNumber=" + kfNumber +
                ", KFTier='" + KFTier + '\'' +
                ", checkinStatus='" + checkinStatus + '\'' +
                ", bookingID=" + bookingID +
                '}';
    }
}
