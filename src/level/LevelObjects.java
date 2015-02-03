package level;

import java.util.ArrayList;

import org.newdawn.slick.geom.Rectangle;

public class LevelObjects {

	private ArrayList<Rectangle> blocks;
	private ArrayList<ArrayList<Integer>> itemLocations;
	private int tileHeight;
	private int tileWidth;

	public LevelObjects(){


		tileHeight = 32;
		tileWidth = 32;

		
		int[][] objectMatrix = randomObjectsMatrix(5,6);
		
		getObjects(objectMatrix);




	}



	private int[][] randomObjectsMatrix(int m, int n){
		

		int[][] blockMatrix = new int[m][n];

		for (int row = 0; row < m; row++){
			for (int col = 0; col < n; col++){
				if(row == 0 | col == 0 | row == (m-1) | col == (n-1) ){
					blockMatrix[row][col] = 1;	

				}else{
					// items on diagonal
					if(row==col){
						blockMatrix[row][col] = 2;
					}else{

						blockMatrix[row][col] = 0;
					}
				}
				// System.out.print(blockMatrix[row][col] + " ");
			}
		}

		// System.out.println();



		return blockMatrix;
	}
	
	

	private void getObjects(int[][] objectMatrix){
		this.blocks = new ArrayList<Rectangle>();
		this.itemLocations =   new ArrayList<ArrayList<Integer>>();

		// get the number of rows and columns from the objectMatrix
		int numRows = objectMatrix.length;
		int numCols = objectMatrix[0].length;

		for (int row = 0; row < numRows; row++){
			for (int col = 0; col < numCols; col++){

				// convert row/col position to x/y pixels
				int x = row*tileWidth;
				int y = row*tileHeight;

				
				if(objectMatrix[row][col]==1){

					// store solid collideable walls
					blocks.add(new Rectangle(x,y,tileWidth,tileHeight));
					
				}else{
					
					// store item locations
					if (objectMatrix[row][col]==2){
						ArrayList<Integer> itemCoords = new ArrayList<Integer>();
						itemCoords.add(x);
						itemCoords.add(y);
						itemLocations.add(itemCoords);
						
					}
				}
			}
		}

		
	}



}




