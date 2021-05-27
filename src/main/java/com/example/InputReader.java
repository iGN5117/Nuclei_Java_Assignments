package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Optional;

class InputReader {
    BufferedReader br;
    ArrayList<ItemProcessing> itemList = new ArrayList<>();
    Optional<String> enteredName;
    Optional<Double> enteredPrice;
    Optional<Integer> enteredQuantity;
    Optional<ItemType> enteredItemType;

    InputReader() {
        br = new BufferedReader(new InputStreamReader(System.in));
    }

    /**
     * Entry method to accept all user input
     * @throws IOException
     */
    void readInput() throws IOException  {
        printWelcomeMessage();
        String welcomeInput = br.readLine().trim();
        if (welcomeInput.equalsIgnoreCase("y")) {
            printItemEnteringDescription();
            while (true) {
                String nameLine = br.readLine().trim();
                if (validateNameInput(nameLine)) {
                    enteredName = Optional.of(nameLine.split("-name ")[1]);
                    enteredName.ifPresent(s -> System.out.println("Entered name: " + s));
                    ItemProcessing enteredItem = new ItemProcessing(makeItem());
                    itemList.add(enteredItem);
                    System.out.println(Constants.ADD_ANOTHER_ITEM);
                    boolean newItemRequest = acceptNewItemRequest();
                    if (!newItemRequest) {
                        break;
                    }
                }
            }
            printItems();
        }else if (welcomeInput.equalsIgnoreCase("n")) {
            System.out.println("Understandable, have a good day");
            System.exit(0);
        }else {
            System.out.println("Invalid input, exiting.....");
            System.exit(0);
        }
    }

    /**
     * Helper method to print welcome message
     */
    void printWelcomeMessage() {
        System.out.println(Constants.WELCOME_MESSAGE);
    }

    /**
     * Helper method to print steps to enter item details
     */
    void printItemEnteringDescription() {
        System.out.println("-------------------------------------------------------------");
        System.out.println("                        Item Inventory                       ");
        System.out.println("-------------------------------------------------------------");
        System.out.println("Enter a non empty item name by entering -name <item name>");
        System.out.println("After entering a valid name, details need to be entered (in any order). Following are the required details with the accepted format:");
        System.out.println("-price <price of one item>");
        System.out.println("-quantity <quantity of entered item>");
        System.out.println("-type <type of the the item>. Accepted values are for the type are: r for Raw, m for Manufactured, i for Imported");
        System.out.println("Entered detail can be edited by entering the same key again");
        System.out.println("Enter done to save an item");
        System.out.println("Go ahead and start entering items!");
    }

    /**
     * Method to validate name entry
     * @param nameLine input entered by user for item name
     * @return TRUE if valid name is entered, FALSE otherwise
     */
    boolean validateNameInput(final String nameLine) {
        if (nameLine.matches("-name .*")) {
            return true;
        }else {
            System.out.println("Invalid entry, please enter a non empty name of the item in the mentioned format. -name <item name>");
            return false;
        }
    }

    /**
     * Method to return Item with details entered by users
     * @return Item
     * @throws IOException
     */
    Item makeItem() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Item enteredItem;
        while (true) {
            String attributeLine = br.readLine();
            if (attributeLine.matches("-price .*")) {
                acceptPrice(attributeLine);
            }else if (attributeLine.matches("-quantity .*")) {
                acceptQuantity(attributeLine);
            }else if (attributeLine.matches("-type .")) {
                acceptType(attributeLine);
            }else if (attributeLine.matches("done")) {
                if (validatePresenceOfAllDetails()) {
                    enteredItem = new Item(enteredName.get(), enteredPrice.get(), enteredQuantity.get(), enteredItemType.get());
                    System.out.println("Item Added!");
                    break;
                }
            }else if (attributeLine.matches("-name .*")) {
                acceptNewNameRequest(attributeLine);
            }else {
                System.out.println("Invalid input. Please add according to previously mentioned format.");
            }
        }
        return enteredItem;
    }

    /**
     * Method to enter user response for adding another item
     * @return TRUE if user intends to enter another item, FALSE otherwise
     * @throws IOException
     */
    boolean acceptNewItemRequest() throws IOException {
        String newItemRequest;
        while (true) {
            newItemRequest = br.readLine().trim();
            if (!(newItemRequest.equalsIgnoreCase("y")) && !(newItemRequest.equalsIgnoreCase("n"))) {
                System.out.println("Invalid input");
            }else {
                break;
            }
        }
        return newItemRequest.equalsIgnoreCase("y");
    }

    /**
     * Method to print final item details of all items created by the user
     */
    void printItems() {
        if (!itemList.isEmpty()) {
            for (ItemProcessing item: itemList
            ) {
                item.printItemDetails();
            }
        }
    }

    /**
     * Method to enter and validate price entered by user
     * @param attributeLine input entered by user for item price
     */
    void acceptPrice(final String attributeLine) {
        String priceString = attributeLine.split("-price ")[1];
        try {
            double potentialPrice = Double.parseDouble(priceString);
            validatePrice(potentialPrice);
        }catch (NumberFormatException e) {
            System.out.println("Invalid price entered. Please enter price in decimal format");
        }
    }

    /**
     * 
     * @param potentialPrice input price which is yet to be validated
     */
    void validatePrice(final double potentialPrice) {
        if (potentialPrice <= 0) {
            System.out.println("Price cannot be negative or zero");
        }else {
            enteredPrice = Optional.of(potentialPrice);
            System.out.println("Entered price: " + enteredPrice.get());
        }
    }

    /**
     * Method to accept and validate quantity entered by user
     * @param attributeLine input quantity entered by user for item quantity
     */
    void acceptQuantity(final String attributeLine) {
        String quantityString = attributeLine.split("-quantity ")[1];
        try {
            int potentialQuantity = Integer.parseInt(quantityString);
            validateQuantity(potentialQuantity);
        }catch (NumberFormatException e) {
            System.out.println("Invalid quantity entered. Please add quantity in integer format");
        }
    }

    /**
     * Helper method to validate a given quantity
     * @param potentialQuantity input quantity which is yet to be validated
     */
    void validateQuantity(final int potentialQuantity) {
        if (potentialQuantity <= 0) {
            System.out.println("Quantity cannot be negative or zero");
        }else {
            enteredQuantity = Optional.of(potentialQuantity);
            enteredQuantity.ifPresent(s -> System.out.println("Entered quantity: " + enteredQuantity.get()));
        }
    }

    /**
     * Method to accept and validate ItemType entered by the user
     * @param attributeLine input entered by user for item type
     */
    void acceptType(final String attributeLine) {
        String potentialItemType = attributeLine.split("-type ")[1];
        switch (potentialItemType.toLowerCase()) {
            case "r":
                enteredItemType = Optional.of(ItemType.Raw);
                enteredItemType.ifPresent(s -> System.out.println("Entered type: " + enteredItemType.get()));
                break;
            case "m":
                enteredItemType = Optional.of(ItemType.Manufactured);
                enteredItemType.ifPresent(s -> System.out.println("Entered type: " + enteredItemType.get()));
                break;
            case "i":
                enteredItemType = Optional.of(ItemType.Imported);
                enteredItemType.ifPresent(s -> System.out.println("Entered type: " + enteredItemType.get()));
                break;
            default:
                System.out.println("Invalid item type entered. Please add a valid item type from r/m/i");
        }
    }

    /**
     * Method to check presence of all details required for an item
     * @return TRUE if all details are present, FALSE otherwise
     */
    boolean validatePresenceOfAllDetails() {
        if (!enteredName.isPresent() && !enteredPrice.isPresent() && !enteredQuantity.isPresent() && !enteredItemType.isPresent()) {
            return true;
        }else {
            System.out.println("Insufficient item details entered");
            return false;
        }
    }

    /**
     * Method to accept change request for name
     * @param attributeLine input entered by user for change in name
     */
    void acceptNewNameRequest(final String attributeLine) {
        enteredName = Optional.of(attributeLine.split("-name ")[1]); //co
        enteredName.ifPresent(s -> System.out.println("Entered name: " + s));
    }


}
