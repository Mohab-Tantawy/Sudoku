package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        if(args.length == 1 && ("-h".equals(args[0]) || "--help".equals(args[0]))){
            printUsage();
            return;
        }

        if(args.length != 2){
            System.out.println("ERROR: Incorrect number of arguments");
            printUsage();
            System.exit(1);
        }
        String filename = args[0];
        int mode = 0;
        try{
            mode = Integer.parseInt(args[1]);
        }catch (NumberFormatException e){
            System.out.println("ERROR: Mode must be a number (1 or 2 or 3)");
            printUsage();
            System.exit(1);
        }

        if(mode < 1 || mode >3){
            System.out.println("ERROR: Mode must be 1 or 2 or 3");
        }

        int[][] grid = Grid.getGrid(filename);
        if(grid[0][0] == 0){
            System.out.println("ERROR: Could not load Sudoku grid from: " + filename);
            System.out.println("Please check if the file exists and has correct format");
            System.exit(1);
        }
        SudokuValidator validator = createValidator(mode);
        validator.validate(grid);
    }
    private static SudokuValidator createValidator(int mode){
        switch (mode) {
            case 1:
                return new runSingleThread();
            case 2:
                return new runMultiThread();
            case 3:
                return new runUltraMultiThread();

            default:
                System.out.println("Invalid mode! Using Multi-Threaded mode. ");
                return new runMultiThread();
        }
    }
    private static void printUsage(){

    }
}