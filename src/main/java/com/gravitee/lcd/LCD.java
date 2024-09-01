package com.gravitee.lcd;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LCD {
    private static final Map<Integer, Digit> DIGIT_MAP = new HashMap<>(10);

    LCD() {
        initializeDigits();
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
        boolean allDigits = number.chars().allMatch(Character::isDigit);
        if (!allDigits) throw new NumberFormatException("Invalid input. Please provide a valid number.");
        List<Integer> digits = number.chars().mapToObj(c -> c - '0').toList();

        int rows = 3; //as the digits should be 3 lines high
        int cols = 3;

        for (int row = 0; row < rows; row++) {
            for (int digit : digits) {
                Digit currentDigit = DIGIT_MAP.get(digit);
                for (int col = 0; col < cols; col++) {
                    if (isTopRow(row)) {
                        printTop(currentDigit, col, cols);
                    } else if (isAboveMiddle(row, 1)) {
                        printAboveMiddle(currentDigit, col, cols);
                    } else if (isMiddleRow(row, 1)) {
                        printMiddle(currentDigit, col, cols);
                    } else if (isBelowMiddle(row, 1, rows)) {
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

    private boolean isAboveMiddle(int row, int middleRow) {
        return row >= 1 && row < middleRow;
    }

    private boolean isMiddleRow(int row, int middleRow) {
        return row == middleRow;
    }

    private boolean isBelowMiddle(int row, int middleRow, int rows) {
        return row > middleRow && row < rows - 1;
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
