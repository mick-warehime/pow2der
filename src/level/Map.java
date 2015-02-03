package level;

import java.util.ArrayList;

import org.newdawn.slick.geom.Rectangle;

public class Map {

	private ArrayList<Rectangle> blocks;
	private ArrayList<ArrayList<Integer>> itemLocations;
	private int tileHeight;
	private int tileWidth;

	public Map(){


		tileHeight = 32;
		tileWidth = 32;

		blocks = getMapBlocks(5,6);




	}



	private int[][] randomBlocksMatrix(int m, int n){

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

	private ArrayList<Rectangle> getMapBlocks(int m, int n){
		blocks = new ArrayList<Rectangle>();
		itemLocations =   new ArrayList<ArrayList<Integer>>();
		
		int[][] blockMatrix = randomBlocksMatrix(m,n);

		for (int row = 0; row < m; row++){
			for (int col = 0; col < n; col++){

				// convert row/col position to x/y pixels
				int x = row*tileWidth;
				int y = row*tileHeight;

				
				if(blockMatrix[row][col]==1){

					blocks.add(new Rectangle(x,y,tileWidth,tileHeight));
					
				}else{
					if (blockMatrix[row][col]==2){
						ArrayList<Integer> itemCoords = new ArrayList<Integer>();
						itemCoords.add(x);
						itemCoords.add(y);
						itemLocations.add(itemCoords);
						
					}
				}
			}
		}

		return blocks;
	}



}




