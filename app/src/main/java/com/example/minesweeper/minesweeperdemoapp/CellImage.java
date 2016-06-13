package com.example.minesweeper.minesweeperdemoapp;


import java.util.*;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class CellImage extends ImageButton {

	/*private int mState=NOT_CLICKED;
	public static final int NOT_CLICKED = 0;
	public static final int CLICKED = 1;
	*//*private static final int DEFAULT = 0;
	private static final int DEFAULT = 0;
	private static final int DEFAULT = 0;
	*/
	private int minesCount=0;
	private static final int CELL_HEIGHT = 120;
	private static final int CELL_WIDTH = CELL_HEIGHT;
	private boolean bomb=false;
	public boolean isRevealed = false;
	private int bombNeighborCount=0;
	private ArrayList<CellImage> neighbors;
	int mRow=0,mColumn=0;
	private final int ID_BOMB=990, ID_DEFAULT=99;


	public CellImage(Context context, int rowId,int columnId,boolean isBomb) {
		super(context);
		//setPosition(rowId,columnId);
		initialise(rowId,columnId);
	}
	private CellImage(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initialise(0,0);
	}

	private CellImage(Context context, AttributeSet attrs) {
		super(context, attrs);
		initialise(0,0);
	}

	public CellImage(Context context) {
		super(context);
		initialise(0,0);
	}


	public void reveal() {
		this.isRevealed = true;
		refreshUI();
	}
	public void refreshUI(){

	if(isRevealed){
		if(isBomb()){
			setImage(ID_BOMB);
		}else{
			setImage(getBombNeighborCount());
		}
	}
}
	private void initialise(int x,int y) {
		
		LinearLayout.LayoutParams paramsUnit = new LinearLayout.LayoutParams(CELL_WIDTH,CELL_HEIGHT);
        setLayoutParams(paramsUnit);
		setPosition(x, y);
		setBombNeighbourCount(0);
		this.neighbors = new ArrayList<CellImage>();
		isRevealed=false;
		setImage(ID_DEFAULT);
	}

	public void setBombNeighbourCount(int count){
		bombNeighborCount=count;
	}
	public int getBombNeighborCount() {
		return bombNeighborCount;
	}
	public ArrayList<CellImage> getNeighbors() {
		return this.neighbors;
	}
	public void addNeighbor(CellImage neighbor) {
		this.neighbors.add(neighbor);
		if(neighbor.isBomb()) {
			this.setBombNeighbourCount(this.getBombNeighborCount()+1);
		}
	}
	public void setPosition(int x, int y){
		mRow=x;
		mColumn=y;
	}
	private void setImage(int type){
	
		switch(type){
			case 0:
				setImageResource(R.drawable.count_0);
				break;
			case 1:
				setImageResource(R.drawable.count_1);
				break;
			case 2:
				setImageResource(R.drawable.count_2);
				break;
			case 3:
				setImageResource(R.drawable.count_3);
				break;
			case 4:
				setImageResource(R.drawable.count_4);
				break;
			case 5:
				setImageResource(R.drawable.count_5);
				break;
			case 6:
				setImageResource(R.drawable.count_6);
				break;
			case 7:
				setImageResource(R.drawable.count_7);
				break;
			case 8:
				setImageResource(R.drawable.count_8);
				break;

			case ID_BOMB:
				setImageResource(R.drawable.imgbomb);
			break;
			case ID_DEFAULT:
				setImageResource(R.drawable.defaultimg);
				break;
			default:
					setImageResource(R.drawable.defaultimg);
					break;
			}
		
	}

/*
	public void setState(int state) {
		mState=state;
		if(state==NOT_CLICKED)
			setImage(99);//setImageResource(R.drawable.ic_launcher);
	}

	public int getState(){
		return mState;
	}

	private void setCountImage(int counter) {

	}
*/

	public int getMinesCount(){
		return minesCount;
	}

	public boolean isBomb() {
		return bomb;
	}

	public void setBomb(boolean bomb) {
		this.bomb = bomb;
	}
}
