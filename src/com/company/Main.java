package com.company;

public class Main {

    public static void main(String[] args) {
        int[][] grid=Grid.getGrid("sudoku_test.csv");

        Check check=new Check(grid);
        if(grid[0][0]!=0) {
            for (int i = 1; i < 10; i++) {
                if (check.checkRow(i)) {
                    System.out.println("Row:" + i + " Valid\n");
                } else {
                    System.out.println("Row:" + i + " Invalid\n");
                }
                if (check.checkColumn(i)) {
                    System.out.println("Column:" + i + " Valid\n");
                } else {
                    System.out.println("Column:" + i + " Invalid\n");
                }
                if (check.checkRow(i)) {
                    System.out.println("Box:" + i + " Valid\n");
                } else {
                    System.out.println("Box:" + i + " Invalid\n");
                }
                System.out.println("_______________________");
            }
            //System.out.println("Valid");
        }
    }
}
