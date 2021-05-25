package com.example;

import org.junit.Before;
import org.junit.Test;
import org.junit.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

public class ItemTest {
    private Item itemRaw;
    private Item itemManufactured;
    private Item itemImported;

    @BeforeEach
    public void init() {
        itemRaw = new Item("Raw_Item", 10.4, 2, ItemType.Raw);
        itemManufactured = new Item("Manufactured_Item", 100.5, 10, ItemType.Manufactured);
        itemImported = new Item("Imported_Item", 200, 20, ItemType.Imported);
    }

    @Test
    @DisplayName("Test to check if tax per item is calculated properly")
    public void testTaxCalculation() {
        Assertions.assertEquals(itemRaw.calculateTax(), 16.44, "Raw item tax calculation should be proper");
        Assertions.assertEquals(itemManufactured.calculateTax(), 120.55, "Manufactured item tax calculation should be proper");
        Assertions.assertEquals(itemImported.calculateTax(), 231, "Imported item tax calculation should be proper");
    }

    @Test
    @DisplayName("Test to check if item details are printed")
    public void testItemDetailsPrinting() {
        itemRaw.printItemDetails();
        itemManufactured.printItemDetails();
        itemImported.printItemDetails();
    }
}
