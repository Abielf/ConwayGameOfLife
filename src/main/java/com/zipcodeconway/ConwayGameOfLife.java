package com.zipcodeconway;

import java.util.Arrays;
import java.util.Random;

public class ConwayGameOfLife {
    int[][] startMatrix;
    int[][] futureMatrix;
    int matrixSize;



    public ConwayGameOfLife(Integer dimension) {
        matrixSize=dimension-1;
        startMatrix=new int[dimension][dimension];
        startMatrix=createRandomStart(dimension);
        futureMatrix=new int[dimension][dimension];
        for(int i=0;i<futureMatrix.length;i++){
            for(int j=0;j<futureMatrix[i].length;j++){futureMatrix[i][j]=0;}
        }
        System.out.println(Arrays.toString(startMatrix));
        System.out.println(Arrays.toString(futureMatrix));
     }

    public ConwayGameOfLife(Integer dimension, int[][] startmatrix) {
        matrixSize=dimension-1;
        startMatrix=startmatrix;
        futureMatrix=new int[dimension][dimension];
        for(int i=0;i<futureMatrix.length;i++){
            for(int j=0;j<futureMatrix[i].length;j++){futureMatrix[i][j]=0;}
        }

    }

    public static void main(String[] args) {
        ConwayGameOfLife sim = new ConwayGameOfLife(50);
        int[][] endingWorld = sim.simulate(50);
    }

    // Contains the logic for the starting scenario.
    // Which cells are alive or dead in generation 0.
    // allocates and returns the starting matrix of size 'dimension'
    private int[][] createRandomStart(Integer dimension) {
        int[][] randomMatrix=new int[dimension][dimension];
        Random coinFlip=new Random();
        for(int i=0;i<startMatrix.length;i++){
            for(int j=0;j<startMatrix[i].length;j++){startMatrix[i][j]=coinFlip.nextInt(2);}
        }
        return randomMatrix;
    }

    public int[][] simulate(Integer maxGenerations) {
        int g=maxGenerations;
        while (g>=0) {
            for (int i = 0; i < futureMatrix.length; i++) {
                for (int j = 0; j < futureMatrix[i].length; j++) {
                    futureMatrix[i][j] = isAlive(i, j, startMatrix);
                }
            }
            copyAndZeroOut();
            g--;
        }
        return startMatrix;
    }


    // copy the values of 'next' matrix to 'current' matrix,
    // and then zero out the contents of 'next' matrix
    public void copyAndZeroOut() {
        for(int i = 0; i < futureMatrix.length; i++){
            startMatrix[i] = futureMatrix[i].clone();}

        for(int i=0;i<futureMatrix.length;i++){
            for(int j=0;j<futureMatrix[i].length;j++){futureMatrix[i][j]=0;}
        }

    }

    // Calculate if an individual cell should be alive in the next generation.
    // Based on the game logic:
	/*
		Any live cell with fewer than two live neighbours dies, as if by needs caused by underpopulation.
		Any live cell with more than three live neighbours dies, as if by overcrowding.
		Any live cell with two or three live neighbours lives, unchanged, to the next generation.
		Any dead cell with exactly three live neighbours cells will come to life.
	*/

    //FIX TO DIMENSIONS OF PASSED ARRAY PLEASE
    private int isAlive(int row, int col, int[][] world) {
        int totalLife=0;
        if(row!=0){
            if(world[row-1][col]==1){totalLife++;}
            if(col!=0){if(world[row-1][col-1]==1){totalLife++;}}
            if(col!=matrixSize){if(world[row-1][col+1]==1){totalLife++;}}
        }
        if(row!=matrixSize){
            if(world[row+1][col]==1){totalLife++;}
            if(col!=0){if(world[row+1][col-1]==1){totalLife++;}}
            if(col!=matrixSize){if(world[row+1][col+1]==1){totalLife++;}}
        }
        if(col!=0){
            if(world[row][col-1]==1){totalLife++;}
        }
        if(col!=matrixSize){
            if(world[row][col+1]==1){totalLife++;}
        }
        if (totalLife==3){return 1;}
        if ((world[row][col]==1)&&(totalLife==2)){return 1;}
        return 0;
    }
}
