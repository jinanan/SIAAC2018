package com.example.user.afteryousiami.objects;

import java.io.Serializable;

public class Perks implements Serializable {

    private String category;
    private String name;
    private String description;
    private double pricePerUnit;
    private boolean hasAdded;       //checks if the item has been added to the cart or not

    public Perks() {
    }


    public Perks(String category, String name, String description, double pricePerUnit) {
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


    @Override
    public String toString() {
        return "Perks{" +
                "category='" + category + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", pricePerUnit=" + pricePerUnit +
                '}';
    }
}
