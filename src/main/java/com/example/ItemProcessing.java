package com.example;

class ItemProcessing {
    private final Item item;

    ItemProcessing(Item item) {
        this.item = item;
    }

    /**
     * Method to calculate tax of any type of item
     * @return final tax on an item
     */
    double calculateTax() {
        double finalTax = 0.0;
        switch (item.getItemType()) {
            case Raw:
                finalTax = calculateRawItemTax(item.getPrice());
                break;
            case Manufactured:
                finalTax = calculateManufacturedItemTax(item.getPrice());
                break;
            case Imported:
                finalTax = calculateImportedItemTax(item.getPrice());
                break;
        }
        return finalTax;
    }

    /**
     * Method to calculate tax on a raw item
     * @param price of the raw item
     * @return final tax on a raw item
     */
    double calculateRawItemTax(final double price) {
        return price * Constants.RAW_ITEM_TAX;
    }

    /**
     * Method to calculate tax on a manufactured item
     * @param price of the manufactured item
     * @return final tax on a manufactured item
     */
    double calculateManufacturedItemTax(final double price) {
        return (price * Constants.MANUFACTURED_ITEM_TAX + (Constants.MANUFACTURED_ITEM_TWO_PERCENT * (price + price * Constants.MANUFACTURED_ITEM_TAX)));
    }

    /**
     * Method to calculate tax on an imported item
     * @param price of the imported item
     * @return final tax on an imported item
     */
    double calculateImportedItemTax(final double price) {
        double importDuty = Constants.IMPORT_DUTY * price;
        double postTaxPrice = price + importDuty;
        double surcharge = calculateSurcharge(postTaxPrice);
        return (postTaxPrice + surcharge);
    }

    /**
     * Method to calculate surcharge for imported items
     * @param itemPrice price of an imported item
     * @return surcharge
     */
    double calculateSurcharge(final double itemPrice) {
        double surcharge;
        if (itemPrice <= Constants.HUNDRED) {
            surcharge = 5;
        }else if (itemPrice <= Constants.TWO_HUNDRED) {
            surcharge = 10;
        }else {
            surcharge = Constants.SURCHARGE_FIVE_PERCENT * itemPrice;
        }
        return surcharge;
    }

    /**
     * Method to return final item price
     * @param tax calculated tax for a item
     * @return final price
     */
    double getFinalPriceEachItem(final double tax) {
        return (item.getPrice() + tax);
    }

    /**
     * method to calculate final prize of the item including tax
     * @param tax calculated ta for a item
     * @return final prize
     */
    double getFinalPrice(final double tax) {
        double perItemPrice = item.getPrice() + tax;
        return (item.getQuantity() * perItemPrice);
    }

    /**
     * Method to print final details of the item
     */
    void printItemDetails() {
        double finalTax = calculateTax();
        System.out.println("Name of the item: " + item.getName());
        System.out.println("Prize of the item: " + item.getPrice());
        System.out.println("Sales tax liability per item: " + finalTax);
        System.out.println("Final prize of each item: " + getFinalPriceEachItem(finalTax));
        System.out.println("Total prize of the items: " + getFinalPrice(finalTax));
    }
}
