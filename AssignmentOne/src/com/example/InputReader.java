package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Optional;

public class InputReader {
    BufferedReader br;
    ArrayList<Item> itemList = new ArrayList<>();
    Optional<String> enteredName;
    Optional<Double> enteredPrice = Optional.empty();
    Optional<Integer> enteredQuantity = Optional.empty();
    Optional<ItemType> enteredItemType = Optional.empty();

    public InputReader() {
        br = new BufferedReader(new InputStreamReader(System.in));
    }

    public void startReadingInput() {
        try {
            this.readInput();
        }catch (IOException e) {
            System.out.println("Error in accepting input, please try again later");
            System.exit(0);
        }
    }

    public void setMockData(String enteredName, double enteredPrice,
                            int enteredQuantity, ItemType enteredItemType) {
        this.enteredName = Optional.of(enteredName);
        this.enteredPrice = Optional.of(enteredPrice);
        this.enteredQuantity = Optional.of(enteredQuantity);
        this.enteredItemType = Optional.of(enteredItemType);
    }

    public void setMockDataEmpty() {
        this.enteredName = Optional.empty();
        this.enteredPrice = Optional.empty();
        this.enteredQuantity = Optional.empty();
        this.enteredItemType = Optional.empty();
    }

    public void setMockItems() {
        Item itemRaw = new Item("Raw_Item", 10.4, 2, ItemType.Raw);
        Item itemManufactured = new Item("Manufactured_Item", 100.5, 10, ItemType.Manufactured);
        Item itemImported = new Item("Imported_Item", 200, 20, ItemType.Imported);
        itemList.add(itemRaw);
        itemList.add(itemManufactured);
        itemList.add(itemImported);
    }

    public void readInput() throws IOException  {
        this.printWelcomeMessage();
        String welcomeInput = br.readLine().trim();
        if (welcomeInput.equalsIgnoreCase("y")) {
            printItemEnteringDescription();
            while (true) {
                String nameLine = br.readLine().trim();
                if (acceptNameInput(nameLine)) {
                    enteredName = Optional.of(nameLine.split("-name ")[1]);
                    enteredName.ifPresent(s -> System.out.println("Entered name: " + s));
                    Item enteredItem = this.makeItem();
                    this.itemList.add(enteredItem);
                    System.out.println("Do you want to add another item? (y/n)");
                    boolean newItemRequest = this.acceptNewItemRequest();
                    if (!newItemRequest) {
                        break;
                    }
                }
            }
            this.printItems();
        }else if (welcomeInput.equalsIgnoreCase("n")) {
            System.out.println("Understandable, have a good day");
            System.exit(0);
        }else {
            System.out.println("Invalid input, exiting.....");
            System.exit(0);
        }
    }

    public void printWelcomeMessage() {
        System.out.println("Welcome to item inventory");
        System.out.println("Would you like to add items? (y/n)");
    }


    public void printItemEnteringDescription() {
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

    public boolean acceptNameInput(String nameLine) {
        if (nameLine.matches("-name .*")) {
            return true;
        }else {
            System.out.println("Invalid entry, please enter a non empty name of the item in the mentioned format. -name <item name>");
            return false;
        }
    }

    public Item makeItem() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Item enteredItem;
        while (true) {
            String attributeLine = br.readLine();
            if (attributeLine.matches("-price .*")) {
                this.acceptPrice(attributeLine);
            }else if (attributeLine.matches("-quantity .*")) {
                this.acceptQuantity(attributeLine);
            }else if (attributeLine.matches("-type .")) {
                this.acceptType(attributeLine);
            }else if (attributeLine.equalsIgnoreCase("done")) {
                if (this.validatePresenceOfAllDetails()) {
                    enteredItem = new Item(enteredName.get(), enteredPrice.get(), enteredQuantity.get(), enteredItemType.get());
                    System.out.println("Item Added!");
                    break;
                }
            }else if (attributeLine.matches("-name .*")) {
                this.acceptNewNameRequest(attributeLine);
            }else {
                System.out.println("Invalid input. Please add according to previously mentioned format.");
            }
        }
        return enteredItem;
    }

    public boolean acceptNewItemRequest() throws IOException {
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

    public void printItems() {
        if (!itemList.isEmpty()) {
            for (Item item: itemList
            ) {
                item.printItemDetails();
            }
        }
    }

    public void acceptPrice(String attributeLine) {
        String priceString = attributeLine.split("-price ")[1];
        try {
            double potentialPrice = Double.parseDouble(priceString);
            this.validatePrice(potentialPrice);
        }catch (NumberFormatException e) {
            System.out.println("Invalid price entered. Please enter price in decimal format");
        }
    }

    public void validatePrice(double potentialPrice) {
        if (potentialPrice <= 0) {
            System.out.println("Price cannot be negative or zero");
        }else {
            enteredPrice = Optional.of(potentialPrice);
            System.out.println("Entered price: " + enteredPrice.get());
        }
    }

    public void acceptQuantity(String attributeLine) {
        String quantityString = attributeLine.split("-quantity ")[1];
        try {
            int potentialQuantity = Integer.parseInt(quantityString);
            this.validateQuantity(potentialQuantity);
        }catch (NumberFormatException e) {
            System.out.println("Invalid quantity entered. Please add quantity in integer format");
        }
    }

    public void validateQuantity(int potentialQuantity) {
        if (potentialQuantity <= 0) {
            System.out.println("Quantity cannot be negative or zero");
        }else {
            enteredQuantity = Optional.of(potentialQuantity);
            enteredQuantity.ifPresent(s -> System.out.println("Entered quantity: " + enteredQuantity.get()));
        }
    }

    public void acceptType(String attributeLine) {
        String potentialItemType = attributeLine.split("-type ")[1];
        if (potentialItemType.equalsIgnoreCase("i")) {
            enteredItemType = Optional.of(ItemType.Imported);
            enteredItemType.ifPresent(s -> System.out.println("Entered type: " + enteredItemType.get()));
        }else if (potentialItemType.equalsIgnoreCase("m")) {
            enteredItemType = Optional.of(ItemType.Manufactured);
            enteredItemType.ifPresent(s -> System.out.println("Entered type: " + enteredItemType.get()));
        }else if (potentialItemType.equalsIgnoreCase("r")) {
            enteredItemType = Optional.of(ItemType.Raw);
            enteredItemType.ifPresent(s -> System.out.println("Entered type: " + enteredItemType.get()));
        }else {
            System.out.println("Invalid item type entered. Please add a valid item type from r/m/i");
        }
    }

    public boolean validatePresenceOfAllDetails() {
        if (!enteredName.isEmpty() && !enteredPrice.isEmpty() && !enteredQuantity.isEmpty() && !enteredItemType.isEmpty()) {
            return true;
        }else {
            System.out.println("Insufficient item details entered");
            return false;
        }
    }

    public void acceptNewNameRequest(String attributeLine) {
        enteredName = Optional.of(attributeLine.split("-name ")[1]);
        enteredName.ifPresent(s -> System.out.println("Entered name: " + s));
    }


}
