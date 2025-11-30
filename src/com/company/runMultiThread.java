package com.company;

public class runMultiThread implements SudokuValidator{

    private volatile int validRows = 0;
    private volatile int validColumns = 0;
    private volatile int validBoxes = 0;
    private final int TOTAL_CHECKS = 9;
    private final Object outputLock = new Object();

    @Override
    public void validate(int[][] grid){
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
        displayFinalResults();
    }

    private void displayFinalResults(){
        System.out.println("\n" + "=".repeat(50));
        System.out.println("FINAL VALIDATION RESULTS:");
        System.out.println("=".repeat(50));
        System.out.println("Valid Rows: " + validRows + "/" + TOTAL_CHECKS);
        System.out.println("Valid Columns: " + validColumns + "/" + TOTAL_CHECKS);
        System.out.println("Valid Boxes: " + validBoxes + "/" + TOTAL_CHECKS);
        if(validRows == TOTAL_CHECKS && validColumns == TOTAL_CHECKS && validBoxes == TOTAL_CHECKS){
            System.out.println("\n🎉 SUCCESS: Valid Sudoku Solution!");
        }else{
            System.out.println("\n❌ FAILED: Invalid Sudoku Solution!");
        }
        System.out.println("=".repeat(50));
    }

    class RowValidator implements Runnable{
        private Check check;

        public RowValidator(Check check){
            this.check = check;
        }

        @Override
        public void run() {
            synchronized (outputLock) {
                //System.out.println("Row validation thread started...");
            }
            for (int i = 1; i <= TOTAL_CHECKS; i++) {
                if (check.checkRow(i)) {
                    synchronized (runMultiThread.this) {
                        validRows++;
                    }
                    synchronized (outputLock) {
                       // System.out.println("Row " + i + ": Valid");
                    }
                } else {
                    synchronized (outputLock) {
                        check.printRepeatedinRow(i);
                    }
                }
            }
            synchronized (outputLock) {
                //System.out.println("Row Validation thread  completed.");
                System.out.println("-".repeat(50));
            }
        }
    }
    class ColumnValidator implements Runnable{
        private Check check;

        public ColumnValidator(Check check){
            this.check = check;
        }

        @Override
        public void run(){
            synchronized (outputLock) {
               // System.out.println("Column validation thread started...");
            }
            for (int i = 1; i <= TOTAL_CHECKS; i++) {
                if (check.checkColumn(i)) {
                    synchronized (runMultiThread.this) {
                        validColumns++;
                    }
                    synchronized (outputLock) {
                      //  System.out.println("Column " + i + ": Valid");
                    }
                } else {
                    synchronized (outputLock) {
                        check.printRepeatedinColumn(i);
                    }
                }
            }
            synchronized (outputLock) {
              //  System.out.println("Column Validation thread  completed.");
                System.out.println("-".repeat(50));
            }
        }
    }
     class BoxValidator implements Runnable{
        private Check check;

        public BoxValidator(Check check){
            this.check = check;
        }

        @Override
        public void run(){
            synchronized (outputLock) {
               // System.out.println("Box validation thread started...");
            }
            for (int i = 1; i <= TOTAL_CHECKS; i++) {
                if (check.checkBox(i)) {
                    synchronized (runMultiThread.this) {
                        validBoxes++;
                    }
                    synchronized (outputLock) {
                     //   System.out.println("Box " + i + ": Valid");
                    }
                } else {
                    synchronized (outputLock) {
                        check.printRepeatedinBox(i);
                    }
                }
            }
            synchronized (outputLock) {
               // System.out.println("Box Validation thread  completed.");
                System.out.println("-".repeat(50));
            }
        }
    }
}
