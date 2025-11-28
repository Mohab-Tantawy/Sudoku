package com.company;

import jdk.swing.interop.SwingInterOpUtils;

import java.util.Scanner;

public class Main {
    private static volatile int validRows = 0;
    private static volatile int validColumns = 0;
    private static volatile int validBoxes = 0;
    private static volatile int TOTAL_CHECKS = 9;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println("🎯 SUDOKU VALIDATOR - MULTI-MODE");
        System.out.println("=".repeat(50));
        System.out.println("1. Single Thread");
        System.out.println("2. Multi-Thread");
        //System.out.println("3. Step-By-Step Thread");
        System.out.print("Choose mode (1-3): ");
        int mode = scan.nextInt();
        scan.close();

        int[][] grid = Grid.getGrid("sudoku_test.csv");

        if (grid[0][0] != 0) {
            switch (mode) {
                case 1:
                    runSingleThread(grid);
                    break;
                case 2:
                    //runMultiThread(grid);
                    break;
                /*case 3:
                    //runStepByStep(grid);
                    break;
                 */
                default:
                    System.out.println("Invalid mode! Using Multi-Threaded mode. ");
                    //runMultiThread(grid);
            }
        } else {
            System.out.println("ERROR: Couldn't Load Sudoku Grid :O ");
        }
    }
    private static void runSingleThread(int[][] grid){
        System.out.println("\n Mode 1: SINGLE THREADED VALIDATION");
        System.out.println("=".repeat(50));

        Check check = new Check(grid);

        int validRows = 0, validColumns = 0, validBoxes = 0;
        System.out.println();
            for (int i = 1; i < TOTAL_CHECKS; i++) {
                if (check.checkRow(i)) {
                    validRows++;
                    System.out.println("Row " + i + ": Valid\n");
                } else {
                    System.out.println("Row " + i + ": Invalid\n");
                }
                if (check.checkColumn(i)) {
                    validColumns++;
                    System.out.println("Column " + i + ": Valid\n");
                } else {
                    System.out.println("Column " + i + ": Invalid\n");
                }
                if (check.checkRow(i)) {
                    validBoxes++;
                    System.out.println("Box " + i + ": Valid\n");
                } else {
                    System.out.println("Box " + i + ": Invalid\n");
                }
                System.out.println("_______________________");
            }
        }
}

