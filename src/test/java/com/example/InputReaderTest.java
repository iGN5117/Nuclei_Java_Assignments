package com.example;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


public class InputReaderTest {

    static InputReader inputReader;

    @BeforeAll
    public static void setup() {
        inputReader = new InputReader();
    }

    @Test
    public void validateCorrectQuantity() {
        inputReader.validateQuantity(10);
    }

    @Test
    public void validateIncorrectQuantity() {
        inputReader.validateQuantity(-10);
        inputReader.validateQuantity(0);
    }

    @Test
    public void validateCorrectPrice() {
        inputReader.validatePrice(100.52);
    }

    @Test
    public void validateIncorrectPrice() {
        inputReader.validatePrice(-12.2);
        inputReader.validatePrice(0);
    }

    @Test
    public void testAcceptNameRequest() {
        inputReader.acceptNewNameRequest("-name New_Name");
    }

    @Test
    public void testCorrectAcceptType() {
        inputReader.acceptType("-type r");
        inputReader.acceptType("-type m");
        inputReader.acceptType("-type i");
    }

    @Test
    public void testIncorrectAcceptType() {
        inputReader.acceptType("-type random_type");
    }

    @Test
    public void testValidAcceptNameInput() {
        inputReader.validateNameInput("-name New_Name");
    }

    @Test
    public void testInvalidAcceptNameInput() {
        inputReader.validateNameInput("random Line");
    }

    @Test
    public void testPrintWelcomeMessage() {
        inputReader.printWelcomeMessage();
    }

    @Test
    public void testPrintItemEnteringDescription() {
        inputReader.printItemEnteringDescription();
    }

    @Test
    public void testInvalidAcceptQuantity() {
        inputReader.acceptQuantity("-quantity  ");
        inputReader.acceptQuantity("-quantity Invalid_String");
    }

    @Test
    public void testValidAcceptQuantity() {
        inputReader.acceptQuantity("-quantity 10");
    }

    @Test
    public void testValidAcceptPrice() {
        inputReader.acceptPrice("-price 10.2");
    }

    @Test
    public void testInvalidAcceptPrice() {
        inputReader.acceptPrice("-price random_String");
    }
}
