
package com.company;


    public class Check {
    int[][] grid;

    public Check(int[][] grid) {
        this.grid = grid;
    }


    public boolean checkRow(int row){
        row--;
        boolean[] seen=new boolean[10];
        for(int col=0;col<9;col++){
            int num=grid[row][col];
            if(seen[num]){
                return false;}
            seen[num]=true;
        }
        return true;
    }

    public boolean checkColumn(int col){
        col--;
        boolean[] seen=new boolean[10];
        for(int row=0;row<9;row++){
            int num=grid[row][col];
            if(seen[num]){
                return false;}
            seen[num]=true;
        }
        return true;
    }

    public boolean checkBox(int box){
        boolean[] seen=new boolean[10];
        box--;
        int brow= (box/3)*3;
        int bcol= (box%3)*3;
        for(int row=brow;row<brow+3;row++){
            for(int col=bcol;col<bcol+3;col++){
                int num =grid[row][col];
                if(seen[num]){
                    return false;}
                seen[num]=true;
            }
        }
        return true;
    }

}
