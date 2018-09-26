package com.example.user.afteryousiami.objects;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Attraction implements Comparable<Attraction> {

    private String type;
    private String name;
    private double price;
    private double rating;


    public Attraction(String type, String name, double price, double rating) {
        this.type = type;
        this.name = name;
        this.price = price;
        this.rating = rating;

    }


    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Attraction{" +
                "type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", rating=" + rating +
                '}';
    }

    @Override
    public int compareTo(@NonNull Attraction attraction) {
        if (rating < attraction.getRating()) {
            return 1;
        } else if (rating > attraction.getRating()) {
            return -1;
        } else {
            return 0;
        }
    }

    public static void main(String[] args) {
        Attraction a1 = new Attraction("a", "a", 0, 1);
        Attraction a2 = new Attraction("b", "b", 0, 2);
        Attraction a3 = new Attraction("c", "c", 0, 4);
        Attraction a4 = new Attraction("d", "d", 0, 3);

        List<Attraction> list = new ArrayList<>();
        list.add(a1);
        list.add(a2);
        list.add(a3);
        list.add(a4);

        Collections.sort(list);

        for(Attraction a : list){
            System.out.println(a);
        }
    }
}
