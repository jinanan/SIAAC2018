package com.example.user.afteryousiami.objects;

public class Perks {

    private String category;
    private String name;
    private String description;
    private double pricePerUnit;

    public Perks() {
    }


    public Perks(String category, String name, String description, double pricePerUnit) {
        this.category = category;
        this.name = name;
        this.description = description;
        this.pricePerUnit = pricePerUnit;
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
