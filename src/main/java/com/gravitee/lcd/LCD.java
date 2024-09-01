package com.gravitee.lcd;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LCD {
    private static final Map<Integer, Digit> DIGIT_MAP = new HashMap<>(10);
    private int height = 1;
    private int width = 1;

    LCD() {
        initializeDigits();
    }

    LCD(int height, int width) {
        this();
        if (height < 1 || width < 1) throw new IllegalArgumentException("Height or width should not be less than 1.");
        this.height = height;
        this.width = width;
    }

    void initializeDigits() {
        DIGIT_MAP.put(0, Digit.builder().top(true).topRight(true).bottomRight(true).bottom(true).bottomLeft(true).topLeft(true).build());
        DIGIT_MAP.put(1, Digit.builder().topRight(true).bottomRight(true).build());
        DIGIT_MAP.put(2, Digit.builder().top(true).topRight(true).middle(true).bottomLeft(true).bottom(true).build());
        DIGIT_MAP.put(3, Digit.builder().top(true).topRight(true).middle(true).bottomRight(true).bottom(true).build());
        DIGIT_MAP.put(4, Digit.builder().topLeft(true).middle(true).topRight(true).bottomRight(true).build());
        DIGIT_MAP.put(5, Digit.builder().top(true).topLeft(true).middle(true).bottomRight(true).bottom(true).build());
        DIGIT_MAP.put(6, Digit.builder().top(true).topLeft(true).bottomLeft(true).bottom(true).bottomRight(true).middle(true).build());
        DIGIT_MAP.put(7, Digit.builder().top(true).topRight(true).bottomRight(true).build());
        DIGIT_MAP.put(8, Digit.builder().top(true).topLeft(true).middle(true).bottomRight(true).bottom(true).bottomLeft(true).topRight(true).build());
        DIGIT_MAP.put(9, Digit.builder().top(true).topLeft(true).middle(true).topRight(true).bottomRight(true).bottom(true).build());
    }

    void print(String number) {
        //check if number has all digits or not
        boolean allDigits = number.chars().allMatch(Character::isDigit);
        if (!allDigits) throw new NumberFormatException("Invalid input. Please provide a valid number.");

        List<Integer> digits = number.chars().mapToObj(c -> c - '0').toList();

        int rows = height * 2 + 1; // one row for the top and other rows as twice on height
        int cols = width + 2;

        for (int row = 0; row < rows; row++) {
            for (int digit : digits) {
                Digit currentDigit = DIGIT_MAP.get(digit);
                for (int col = 0; col < cols; col++) {
                    if (isTopRow(row)) {
                        printTop(currentDigit, col, cols);
                    } else if (isAboveMiddle(row, height)) {
                        printAboveMiddle(currentDigit, col, cols);
                    } else if (isMiddleRow(row, height)) {
                        printMiddle(currentDigit, col, cols);
                    } else if (isBelowMiddle(row, height, rows)) {
                        printBelowMiddle(currentDigit, col, cols);
                    } else if (isBottomRow(row, rows)) {
                        printBottom(currentDigit, col, cols);
                    }
                }
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    // Methods for conditions
    private boolean isTopRow(int row) {
        return row == 0;
    }

    private boolean isAboveMiddle(int row, int height) {
        return row >= 1 && row < height;
    }

    private boolean isMiddleRow(int row, int height) {
        return row == height;
    }

    private boolean isBelowMiddle(int row, int height, int rows) {
        return row > height && row < rows - 1;
    }

    private boolean isBottomRow(int row, int rows) {
        return row == rows - 1;
    }

    // Methods to print segments
    private void printTop(Digit currentDigit, int currCol, int cols) {
        if (currCol == 0 || currCol == cols - 1) {
            System.out.print(" ");
        } else if (currentDigit.isTop()) {
            System.out.print("_");
        } else {
            System.out.print(" ");
        }
    }

    private void printAboveMiddle(Digit currentDigit, int currCol, int cols) {
        if (isLeftOrRightEdge(currentDigit, currCol, true, cols)) {
            System.out.print("|");
        } else {
            System.out.print(" ");
        }
    }

    private void printMiddle(Digit currentDigit, int currCol, int cols) {
        if (isLeftOrRightEdge(currentDigit, currCol, true, cols)) {
            System.out.print("|");
        } else if (currentDigit.isMiddle() && currCol > 0 && currCol < cols - 1) {
            System.out.print("_");
        } else {
            System.out.print(" ");
        }
    }

    private void printBelowMiddle(Digit currentDigit, int currCol, int cols) {
        if (isLeftOrRightEdge(currentDigit, currCol, false, cols)) {
            System.out.print("|");
        } else {
            System.out.print(" ");
        }
    }

    private void printBottom(Digit currentDigit, int currCol, int cols) {
        if (isLeftOrRightEdge(currentDigit, currCol, false, cols)) {
            System.out.print("|");
        } else if (currentDigit.isBottom() && currCol > 0 && currCol < cols - 1) {
            System.out.print("_");
        } else {
            System.out.print(" ");
        }
    }

    // Utility method for left or right edge checks
    private boolean isLeftOrRightEdge(Digit currentDigit, int currCol, boolean top, int cols) {
        return (currCol == 0 && (top ? currentDigit.isTopLeft() : currentDigit.isBottomLeft())) ||
                (currCol == cols - 1 && (top ? currentDigit.isTopRight() : currentDigit.isBottomRight()));
    }
}
