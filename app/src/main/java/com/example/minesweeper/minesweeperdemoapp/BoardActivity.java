package com.example.minesweeper.minesweeperdemoapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Date;

public class BoardActivity extends Activity implements iCellClickListener {
	private	boolean isGameOver=false;
	private ViewGroup	linear=null;
	private CellContainer cellContainer=null;
	private Context mContext;
	private TextView txtView;

	@Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
		mContext=this;
		initialiseLayout();
	}

	private void initialiseLayout() {
		setContentView(R.layout.activity_board);
	    linear= (ViewGroup) findViewById(R.id.game_container);

		int bombCount=15;
		int size=CellContainer.MIN_ROW_COUNT;//9;
		cellContainer=new CellContainer(this,size,bombCount,this);
		cellContainer.initialise();
		prepareUI(size,size);
	}

	private void prepareUI(int rows, int columns){
		LinearLayout layout = (LinearLayout)findViewById(R.id.game_container);
		for(int i = 0; i < rows; i++)
		{
			LinearLayout row = new LinearLayout(this);
			row.setOrientation(LinearLayout.HORIZONTAL);
			LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			row.setLayoutParams(params);
			//LinearLayout.LayoutParams paramsUnit = new LinearLayout.LayoutParams(CELL_WIDTH,CELL_HEIGHT);
			for(int j = 0; j < columns; j++) {
				row.addView(cellContainer.cellMatrix[i][j]);
			}
			layout.addView(row);
		}
	}


	@Override
	public void onClick(View clickedView) {
		if(!isGameOver && clickedView!=null && clickedView instanceof CellImage){
			CellImage cellImage = (CellImage)clickedView;
			if(!cellImage.isRevealed){
				if(this.cellContainer.reveal(cellImage)) {
					gameOver(true);
				}
			}
		}
	}
	public void gameOver(boolean showNewGame) {
		this.isGameOver = true;
		for(int i = 0; i < this.cellContainer.cellMatrix.length; i++) {
			for(int j = 0; j < this.cellContainer.cellMatrix.length; j++) {
				this.cellContainer.showBombs(this.cellContainer.cellMatrix[i][j]);
				this.cellContainer.reveal(this.cellContainer.cellMatrix[i][j]);
			}
		}
		if(showNewGame)
			showGameOverMsg("Game Over","Do you want to play the game again?");
	}

	public void showGameOverMsg(String title, String message){
		AlertDialog.Builder builder = new AlertDialog.Builder(BoardActivity.this);
		builder.setMessage(message)
				.setTitle(title);

		builder.setPositiveButton(R.string.newgame, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				startNewGame();
			}
		});
		builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// User cancelled the dialog
			}
		});
		AlertDialog dialog = builder.create();
		dialog.show();
	}

	private void startNewGame() {
		Intent intnt=new Intent(mContext,BoardActivity.this.getClass());
		startActivity(intnt);
	}
}
