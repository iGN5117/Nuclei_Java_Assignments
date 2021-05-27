package com.example;

 class Item {
    private final String name;
    private final double price;
    private final int quantity;
    private final ItemType itemType;

     Item(String name, double price, int quantity, ItemType itemType) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.itemType = itemType;
    }

     String getName() {
        return name;
    }

     double getPrice() {
        return price;
    }

     int getQuantity() {
        return quantity;
    }

     ItemType getItemType() {
        return itemType;
    }
}
