package com.company;
import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;

public class case27Threads {
    private final ConcurrentLinkedDeque<String> captured = new ConcurrentLinkedDeque<>();
    private static final Object PRINT_LOCK = new Object();

    private final Check check;

    public case27Threads(Check check){
        this.check=check;
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
        private final int type;
        private final int index;

        public Worker(int type, int index) {
            this.type = type;
            this.index = index;
        }
        @Override
        public void run(){
            boolean valid;

        }

    }

}
