package com.example;

public class Item {
    private final String name;
    private final double price;
    private final int quantity;
    private final ItemType itemType;

    public Item(String name, double price, int quantity, ItemType itemType) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.itemType = itemType;
    }

    public double calculateTax() {
        double finalTax = 0.0;
        switch (this.itemType) {
            case Raw:
                finalTax = this.calculateRawItemTax(this.price);
            case Manufactured:
                finalTax = this.calculateManufacturedItemTax(this.price);
            case Imported:
                finalTax = this.calculateImportedItemTax(this.price);
        }
        return finalTax;
    }

    public double calculateRawItemTax(double price) {
        return price * 0.125;
    }

    public double calculateManufacturedItemTax(double price) {
        return (price * 0.125 + (0.02 * (price * 1.125)));
    }

    public double calculateImportedItemTax(double price) {
        double importDuty = 0.1 * price;
        double postTaxPrice = price + importDuty;
        double surcharge = this.calculateSurcharge(postTaxPrice);
        return (postTaxPrice + surcharge);
    }

    public double calculateSurcharge(double postTaxPrice) {
        double surcharge;
        if (postTaxPrice <= 100) {
            surcharge = 5;
        }else if (postTaxPrice <= 200) {
            surcharge = 10;
        }else {
            surcharge = 0.05 * postTaxPrice;
        }
        return surcharge;
    }

    public double getFinalPriceEachItem(double tax) {
        return (this.price + tax);
    }

    public double getFinalPrice(double tax) {
        double perItemPrice = this.price + tax;
        return (this.quantity * perItemPrice);
    }

    public void printItemDetails() {
        double finalTax = this.calculateTax();
        System.out.println("Name of the item: " + this.name);
        System.out.println("Prize of the item: " + this.price);
        System.out.println("Sales tax liability per item: " + finalTax);
        System.out.println("Final prize of each item: " + this.getFinalPriceEachItem(finalTax));
        System.out.println("Total prize of the items: " + this.getFinalPrice(finalTax));
    }
}
