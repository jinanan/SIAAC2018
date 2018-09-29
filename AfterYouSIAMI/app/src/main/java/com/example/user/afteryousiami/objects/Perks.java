package com.example.user.afteryousiami.objects;

import android.support.annotation.NonNull;

import java.io.Serializable;

public class Perks implements Serializable, Comparable<Perks> {

    private String category;
    private String name;
    private String description;
    private double pricePerUnit;
    private boolean hasAdded;       //checks if the item has been added to the cart or not
    private double totalPrice;
    private int perksID;
    private int quantity;       //used to check the quantity used for KrisPay Credit, Cash and KrisFlyer Miles

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Perks() {
    }

    public int getPerksID() {
        return perksID;
    }

    public void setPerksID(int perksID) {
        this.perksID = perksID;
    }

    public Perks(int perksID, String category, String name, String description, double pricePerUnit) {
        this.perksID = perksID;
        this.category = category;
        this.name = name;
        this.description = description;
        this.pricePerUnit = pricePerUnit;
    }

    public boolean isHasAdded() {
        return hasAdded;
    }

    public void setHasAdded(boolean hasAdded) {
        this.hasAdded = hasAdded;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Perks{" +
                "category='" + category + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", pricePerUnit=" + pricePerUnit +
                '}';
    }

    @Override
    public int compareTo(@NonNull Perks perks) {
        return perks.getName().compareTo(this.getName());
    }
}
