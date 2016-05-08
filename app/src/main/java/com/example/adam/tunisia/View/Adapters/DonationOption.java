package com.example.adam.tunisia.View.Adapters;

/**
 * Created by Adam on 06/05/2016.
 */
public class DonationOption {

    public final String description;
    public final String amount;

    public DonationOption(String amount, String description) {
        this.amount = amount;
        this.description = description;
    }

    @Override
    public String toString() {
        return "DonationOption{" +
                "description='" + description + '\'' +
                ", amount='" + amount + '\'' +
                '}';
    }
}