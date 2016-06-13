package com.example.minesweeper.minesweeperdemoapp;

import android.content.Context;
import android.widget.ImageView;

import java.util.*;
/**
 * Created by sunil_000
 */
public class CellContainer {

    public static final int MIN_ROW_COUNT =10 ;
    public CellImage[][] cellMatrix;
    private int noOfBombs;
    private int noOfRows;
    Context mContext;
    private iCellClickListener listener;


public CellContainer(Context c,int size,int bombs,iCellClickListener clickListener){
    mContext=c;
    if(size<MIN_ROW_COUNT)
        noOfRows=MIN_ROW_COUNT;
    else
        noOfRows=size;

    if(bombs>=(noOfRows*noOfRows))
        noOfBombs=MIN_ROW_COUNT/2;
    else
        noOfBombs=bombs;
    listener=clickListener;
}

    public boolean reveal(CellImage c) {
        c.reveal();
        if(!c.isBomb()) {
            this.cellsRevealed++;
            if(c.getBombNeighborCount() == 0) {
                ArrayList<CellImage> neighbors = c.getNeighbors();
                for(int i = 0; i < neighbors.size(); i++) {
                    if(!neighbors.get(i).isRevealed) {
                        reveal(neighbors.get(i));
                    }
                }
            }
        }
        return c.isBomb();
    }

    public void initialise() {

        this.cellMatrix = new CellImage[noOfRows][noOfRows];
        CellImage imgView;
        for(int i = 0; i < this.cellMatrix.length; i++) {
            for(int j = 0; j < this.cellMatrix.length; j++) {
                int id = j + (i * noOfRows);
                imgView= new CellImage( mContext,i, j, false);
                imgView.setId(id);
                imgView.setScaleType(ImageView.ScaleType.FIT_XY);
                imgView.setPadding(1, 1, 1, 1);
                imgView.setPosition(i, j);

                imgView.setOnClickListener(listener);
                this.cellMatrix[i][j]=imgView;
            }
        }
        this.allocateBombs(noOfBombs);
        this.calculateCellNeighbors();
        this.cellsRevealed = 0;
    }
    int cellsRevealed=0;

    public int getRevealedCount() {
        return this.cellsRevealed;
    }

    public void calculateCellNeighbors() {
        for(int x = 0; x < this.cellMatrix.length; x++) {
            for(int y = 0; y < this.cellMatrix.length; y++) {
                for(int i = x - 1; i <= x + 1; i++) {
                    for(int j =y- 1; j <= y + 1; j++) {
                        if(i >= 0 && i < this.cellMatrix.length && j >= 0 && j < this.cellMatrix.length ) {
                            if(i!=x || j!=y)
                                 this.cellMatrix[x][y].addNeighbor(this.cellMatrix[i][j]);
                        }
                    }
                }
            }
        }
    }
    public void showBombs(CellImage c) {
        if(c.isBomb()) {
            c.reveal();
        }
    }

    public void allocateBombs(int bombCount) {
        boolean spotAvailable = true;
        Random random = new Random();
        int row;
        int column;
        for(int i = 0; i < bombCount; i++) {
            do {
                column = random.nextInt(MIN_ROW_COUNT);
                row = random.nextInt(MIN_ROW_COUNT);
                spotAvailable = this.cellMatrix[column][row].isBomb();
            } while (spotAvailable);
            this.cellMatrix[column][row].setBomb(true);
        }
    }

}
