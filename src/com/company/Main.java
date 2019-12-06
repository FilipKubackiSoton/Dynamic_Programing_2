package com.company;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        int a = countWays(25);
        System.out.print(a);
        int n =3;
        Tower[] towers = new Tower[n];
        for(int i =0; i<n; i++){
            towers[i]= new Tower(i);
        }

        for(int i=n-1; i>=0; i--){
            towers[0].add(i);
        }

        towers[0].moveDisks(n, towers[2],towers[1]);

    }

    //E.x 8.6
    //towers
    //look a the bottom

    //E.x 8.5
    //Recursive Multiply

    int minProduct(int a, int b) {
        int bigger = a < b ? b : a;
        int smaller = a < b ? a : b;
        return minProduct(smaller, bigger);
    }

    int minProductHelper(int smaller, int bigger){
        if(smaller==0) return 0;
        else if(smaller==1) return bigger;

        int s = smaller >>1; //divide by two
        int halfProd= minProductHelper(s, bigger);//compute half
        if(smaller %2==0)
           return halfProd+halfProd;
        else
            return halfProd+halfProd+bigger;
    }


    //E.x 8.4
    //Power Set
    //time complexity O(n2^n)
    //recursion

    ArrayList<ArrayList<Integer>> getSubsets(ArrayList<Integer>set, int index){
        //create set of new set to store adding results
        ArrayList<ArrayList<Integer>> allsubsets;
        //base case - add empty set
        if(set.size()==index){
            allsubsets = new ArrayList<ArrayList<Integer>>();
            //adding empty set
            allsubsets.add(new ArrayList<Integer>());
        }else{
            allsubsets = getSubsets(set, index+1);
            int item = set.get(index);
            ArrayList<ArrayList<Integer>> moresubsets = new ArrayList<ArrayList<Integer>>();
            for(ArrayList<Integer> subset : allsubsets){
                ArrayList<Integer> newsubset = new ArrayList<>();
                newsubset.addAll(subset);
                newsubset.add(item);
                moresubsets.add(newsubset);
            }
            allsubsets.addAll(moresubsets);
        }
        return allsubsets;
    }




    //E.x 8.3
    //Magic Index

    int magicFast(int[] array){
        return magicFast(array, 0, array.length-1);
    }
    int magicFast(int[] array, int start, int end){
        if(end<start){
            return -1;
        }

        int mid = (end - start)/2;
        if(array[mid]==mid) return mid;
        if(array[mid]>mid)
            return magicFast(array, start, mid-1);
        else
            return magicFast(array, mid+1, mid);
    }

    int magicFastWithRepetition(int[] array, int start, int end){
        if(end<start){
            return -1;
        }

        int midIndex = (end - start)/2;
        int midValue = array[midIndex];
        if(midIndex==midValue) return midIndex;

        //search left
        int leftIndex = Math.min(midIndex-1, midValue);
        int left = magicFastWithRepetition(array,start,leftIndex);
        if(left >=0) return left;


        //search right
        int rightIndex = Math.max(midIndex+1, midValue);
        int right = magicFastWithRepetition(array,rightIndex, end);

        return right;
    }

    //E.x 8.2
    //Robot in grid
    //time complexity O(XY)
    ArrayList<Point> getPath(boolean[][] maze){
        //check base case
        if(maze == null || maze.length==0) return null;

        //create memo
        HashSet<Point> failedPoints = new HashSet<>();

        ArrayList<Point> path = new ArrayList<>();
        if(getPath(maze, maze.length,maze[0].length,path, failedPoints))
            return path;
        return null;
    }

    boolean getPath(boolean[][]maze, int row, int col, ArrayList<Point>path, HashSet<Point> failedPoints ){
        //if out of bound or not available, return /
        if(col <0 || row<0 || !maze[row][col]) return false;

        //create a point
        Point p = new Point(row, col);
        //if we already visited this point
        if(failedPoints.contains(p)) return false;
        //check if we have reached origin
        boolean isAtOrigin = (row==0)&&(col==0);

        if(isAtOrigin || getPath(maze, row, col-1, path, failedPoints)||getPath(maze, row-1, col, path, failedPoints)){
            path.add(p);
            return true;
        }
        failedPoints.add(p);//cache result
        return false;
    }

    //E.x 8.1
    //tripple Step
    static int countWays(int n){
        int[] memo = new int[n+1];
        Arrays.fill(memo,-1);
        return countWays(n, memo);
    }


    static int countWays(int n, int [] memo){
        if(n<0){
            return 0;
        }else if(n==0){
            return 1;
        }else if(memo[n]>1){
            return memo[n];
        }else{
            memo[n]= countWays(n-1, memo)+countWays(n-2, memo)+countWays(n-3, memo);
            return memo[n];
        }
    }
}
//E.x 8.6
//Tower and disks
class Tower{
    private Stack<Integer> disks;
    private int index;
    public Tower(int i){
        disks = new Stack<>();
        index = i;
    }
    public int index(){
        return index;
    }

    public void add(int d){
        if(!disks.isEmpty() &&disks.peek()<=d){
            System.out.println("Error placing disk "+d);
        }else{
            disks.push(d);
        }
    }

    public void moveTopTo(Tower t){
        int top = disks.pop();
        t.add(top);
    }

    public void moveDisks(int n, Tower destination, Tower buffer){
        if(n>0){
            moveDisks(n-1, buffer, destination);
              moveTopTo(destination);
            moveDisks(n-1, destination, this);
        }
    }
}