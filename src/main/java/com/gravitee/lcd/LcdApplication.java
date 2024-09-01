package com.gravitee.lcd;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class LcdApplication {
    public static void main(String[] args) {
        LCD lcd = new LCD();
        System.out.print("Enter a number to print : ");
        Scanner sc = new Scanner(System.in);
        String number = sc.next();
        try {
            lcd.print(number);
        } catch (NumberFormatException e) {
            System.err.println("Invalid input. Please enter a valid number.");
        }
    }
}
