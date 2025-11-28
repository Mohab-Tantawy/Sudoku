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
        //System.out.println("3. 27 Thread");
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
                    runMultiThread(grid);
                    break;
                /*case 3:
                    //runUNKOWN(grid);
                    break;
                 */
                default:
                    System.out.println("Invalid mode! Using Multi-Threaded mode. ");
                    runMultiThread(grid);
            }
        } else {
            System.out.println("ERROR: Couldn't Load Sudoku Grid :O ");
        }
    }
    private static void runSingleThread(int[][] grid){
        System.out.println("\n Mode 1: SINGLE THREADED VALIDATION");
        System.out.println("=".repeat(50));

        Check check = new Check(grid);

        validRows = 0;
        validColumns = 0;
        validBoxes = 0;

        System.out.println();
            for (int i = 1; i <= TOTAL_CHECKS; i++) {
                if (check.checkRow(i)) {
                    validRows++;
                    System.out.println("Row " + i + ":  Valid\n");
                } else {
                    //System.out.println("Row " + i + ": Invalid\n");
                    check.printRepeatedinRow(i);
                    System.out.println();
                }
                if (check.checkColumn(i)) {
                    validColumns++;
                    System.out.println("Column " + i + ": Valid\n");
                } else {
                   // System.out.println("Column " + i + ": Invalid\n");
                    check.printRepeatedinColumn(i);
                    System.out.println();
                }
                if (check.checkBox(i)) {
                    validBoxes++;
                    System.out.println("Box " + i + ": Valid\n");
                } else {
                    System.out.println("Box " + i + ": Invalid\n");
                }
                System.out.println("_______________________");
            }
        }
        private static void runMultiThread(int[][] grid){
            System.out.println("\n Mode 2: MULTI-THREADED VALIDATION");
            System.out.println("=".repeat(50));

            Check check = new Check(grid);
            validRows = 0;
            validColumns = 0;
            validBoxes = 0;

            Thread rowThread = new Thread(new RowValidator(check));
            Thread columnThread = new Thread(new ColumnValidator(check));
            Thread boxThread = new Thread(new BoxValidator(check));

            rowThread.start();
            columnThread.start();
            boxThread.start();

            try{
                rowThread.join();
                columnThread.join();
                boxThread.join();
            }catch (InterruptedException e){
                System.out.println("Thread Execution is Interrupted " + e.getMessage());
                return;
            }
        }
        static class RowValidator implements Runnable{
            private Check check;

            public RowValidator(Check check){
                this.check = check;
            }

            @Override
            public void run(){
                System.out.println("Row validation thread started...");
                for(int i = 1; i <= TOTAL_CHECKS; i++){
                    if(check.checkRow(i)){
                        synchronized (Main.class) {
                            validRows++;
                        }
                        System.out.println("Row " + i + ": Valid");
                    }else{
                        System.out.println("Row " + i + ": Invalid");
                    }
                }
                System.out.println("Row Validation thread completed.");
            }
        }
        static class ColumnValidator implements Runnable{
            private Check check;

            public ColumnValidator(Check check){
                this.check = check;
            }

            @Override
            public void run(){
                System.out.println("Column validation thread started...");
                for(int i = 1; i <= TOTAL_CHECKS; i++){
                    if(check.checkColumn(i)){
                        synchronized (Main.class) {
                            validColumns++;
                        }
                        System.out.println("Column " + i + ": Valid");
                    }else{
                        System.out.println("Column " + i + ": Invalid");
                    }
                }
                System.out.println("Column Validation thread completed.");
            }
        }
        static class BoxValidator implements Runnable{
            private Check check;

            public BoxValidator(Check check){
                this.check = check;
            }

            @Override
            public void run(){
                System.out.println("Box validation thread started...");
                for(int i = 1; i <= TOTAL_CHECKS; i++){
                    if(check.checkBox(i)){
                        synchronized (Main.class) {
                            validBoxes++;
                        }
                        System.out.println("Box " + i + ": Valid");
                    }else{
                        System.out.println("Box " + i + ": Invalid");
                    }
                }
                System.out.println("Box Validation thread completed.");
            }
        }
}

