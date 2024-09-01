package com.gravitee.lcd;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.InputMismatchException;
import java.util.Scanner;

@SpringBootApplication
public class LcdApplication {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.print("Enter the height for the number to be printed : ");
            int height = sc.nextInt();
            System.out.print("Enter the width for the number to be printed : ");
            int width = sc.nextInt();
            System.out.print("Enter a number to print : ");
            String number = sc.next();
            LCD lcd = new LCD(height, width);
            lcd.print(number);
        } catch (InputMismatchException i) {
            System.err.println("Invalid input. Please provide a valid input.");
        } catch (RuntimeException r) {
            System.err.println(r.getMessage());
        }
    }
}
