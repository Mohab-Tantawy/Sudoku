package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println("🎯 SUDOKU VALIDATOR - MULTI-MODE");
        System.out.println("=".repeat(50));
        System.out.println("1. Single Thread");
        System.out.println("2. Multi-Thread");
        System.out.println("3. Ultra Multi-Thread");
        System.out.print("Choose mode (1-3): ");
        int mode = scan.nextInt();
        scan.close();

        int[][] grid = Grid.getGrid("sudoku_test.csv");

        if (grid[0][0] != 0) {
            SudokuValidator validator;
            switch (mode) {
                case 1:
                    validator = new runSingleThread();
                    break;
                case 2:
                    validator = new runMultiThread();
                    break;
                case 3:
                    validator = new runUltraMultiThread();
                    break;

                default:
                    System.out.println("Invalid mode! Using Multi-Threaded mode. ");
                    validator = new runMultiThread();
                    break;
            }
            validator.validate(grid);
        } else {
            System.out.println("ERROR: Couldn't Load Sudoku Grid :O ");
        }
    }
}

