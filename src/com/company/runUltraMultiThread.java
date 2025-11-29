package com.company;
import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;

public class runUltraMultiThread implements SudokuValidator {
    private final ConcurrentLinkedDeque<String> captured = new ConcurrentLinkedDeque<>();
    private static final Object PRINT_LOCK = new Object();

    @Override
    public void validate(int[][] grid) {
        System.out.println("\nMode 3: ULTRA MULTI-THREAD VALIDATION");
        System.out.println("=".repeat(50));

        Check check = new Check(grid);
        try{
            runAndPrint(check);
        }catch (InterruptedException e){
            System.out.println("Thread Interrupted");
        }
    }

    private static String capturePrint(Runnable task){
        synchronized (PRINT_LOCK){
            PrintStream originalOut = System.out;
            ByteArrayOutputStream baos= new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(baos);
            try{
                System.setOut(ps);
                task.run();
                ps.flush();
            }finally{
                System.setOut(originalOut);
            }
            return baos.toString();
        }
    }
    private class Worker implements Runnable{
        private final Check check;
        private final int type;
        private final int index;

        public Worker(Check check, int type, int index) {
            this.check = check;
            this.type = type;
            this.index = index;
        }
        @Override
        public void run(){
            boolean valid;
            if(type== 0){
                valid= check.checkRow(index);

            if(!valid){
                String out= capturePrint(()-> check.printRepeatedinRow(index));
                if(!out.isEmpty())
                    captured.add(out);
            }}
            else if(type == 1){
                valid= check.checkColumn(index);

                if(!valid){
                    String out= capturePrint(()-> check.printRepeatedinColumn(index));
                    if(!out.isEmpty())
                        captured.add(out);
                }

            }
            else{
                valid= check.checkBox(index);

                if(!valid){
                    String out= capturePrint(()-> check.printRepeatedinBox(index));
                    if(!out.isEmpty())
                        captured.add(out);
                }
            }
        }

    }

    public void runAndPrint(Check check) throws InterruptedException{
        Thread[] threads= new Thread[27];
        int t=0;
        for(int i=1;i<10;i++){
            threads[t++]=new Thread(new Worker(check,0,i));
        }

        for(int i=1;i<10;i++){
            threads[t++]=new Thread(new Worker(check,1,i));
        }

        for(int i=1;i<10;i++){
            threads[t++]=new Thread(new Worker(check,2,i));
        }

        for(int i=0;i<threads.length;i++){
            threads[i].start();
        }

        for(int i=0;i<threads.length;i++){
            threads[i].join();
        }

        List<String> lines= new ArrayList<>();
        String line;
        while((line= captured.poll())!=null){
            if(line.trim().isEmpty())
                continue;

            String[] l= line.split("\r\n");
            for(String ll: l){
                String trim= ll.trim();
                if(!trim.isEmpty()){
                    lines.add(trim);
                }
            }
        }
        if(lines.isEmpty()){
            System.out.println("Valid");
            return;
        }
        System.out.println("Invalid");
        System.out.println();
        
        for(int r=1;r<10;r++){
            String pre="ROW "+r+",";
            for(String line1: lines){
                if(line1.startsWith(pre))
                    System.out.println(line1);
            }
        }
        System.out.println("-".repeat(50));
        for(int c=1;c<10;c++){
            String pre="COL "+c+",";
            for(String line2: lines){
                if(line2.startsWith(pre))
                    System.out.println(line2);
            }
        }
        System.out.println("-".repeat(50));
        for(int b=1;b<10;b++){
            String pre="BOX "+b+",";
            for(String line3: lines){
                if(line3.startsWith(pre))
                    System.out.println(line3);
            }
        }
        System.out.println("-".repeat(50));
    }

}
