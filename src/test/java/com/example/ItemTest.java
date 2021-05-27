package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

public class ItemTest {
    private ItemProcessing itemRaw;
    private ItemProcessing itemManufactured;
    private ItemProcessing itemImported;

    @BeforeEach
    public void init() {
        itemRaw = new ItemProcessing(new Item("Raw_Item", 10.4, 2, ItemType.Raw));
        itemManufactured = new ItemProcessing(new Item("Manufactured_Item", 100.5, 10, ItemType.Manufactured));
        itemImported = new ItemProcessing(new Item("Imported_Item", 200, 20, ItemType.Imported));
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
