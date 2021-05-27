package com.example;

import java.io.IOException;

public class AssignmentOne {
    public static void main(String[] args) {
        try {
            new InputReader().readInput();
        } catch (IOException e) {
            System.out.println("Error in accepting input, please try again later..");
            e.printStackTrace();
            System.exit(0);
        }
    }
}

