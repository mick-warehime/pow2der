package world;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;



public class LevelBuilder {

	protected static final int OBJECT_START=-1;

	protected static final int OBJECT_BLANK = 0;

	protected static final int OBJECT_BLOCK = 1;
	protected static final int OBJECT_ITEM = 2;
	protected static final int OBJECT_DOOR = 3;
	protected static final int OBJECT_ENEMY = 4;
	protected static final int OBJECT_ROOM_TILE = 1;
	protected static final int OBJECT_HALLWAY_TILE = 3;
	protected static final int OBJECT_DOOR_TILE = 2;
	protected static final int OBJECT_WALL_TILE = 0;
	// used for building map
	protected static final int DOOR = -2;
	private final static int LEFT = 0;
	private final static int UP = 1;
	private final static int RIGHT = 2;
	private final static int DOWN = 3;


	private List<Integer> objectTypes;
	private List<Integer[]> objectPositions;

	private int[][] objectMatrix;

	// POSSIBLE IMPROVEMENTS / BUGS

	// Backtrack does grabs closest open point instead of a random open point.
	//  can you just get a random open index and try from there???


	public LevelBuilder(int width, int height){

		// premade level by hand
		newLevel(50,50);

	}


	
	public void newLevel(int m, int n){




		randomObjectsMatrix(m,n);
		defineObjectsFromMatrix();

	}

	public List<Integer> getObjectTypes(){
		return objectTypes;
	}

	private void randomObjectsMatrix(int m, int n){


		int[][] objectMatrix = new int[m][n];

		for (int row = 0; row < m; row++){
			for (int col = 0; col < n; col++){
				if(row == 0 | col == 0 | row == (m-1) | col == (n-1) ){
					objectMatrix[row][col] = OBJECT_BLOCK;	

				}else{
					// items on diagonal
					if(row==col){
						objectMatrix[row][col] = OBJECT_ITEM;
					}else{

						objectMatrix[row][col] = OBJECT_BLANK;
					}
				}
				//				 System.out.print(objectMatrix[row][col] + " ");
			}
			//			System.out.println();
		}



		// add something to run into in the middle of the map
		objectMatrix[12][10] = OBJECT_BLOCK;
		objectMatrix[2][2] = OBJECT_START;
		objectMatrix[5][5] = OBJECT_ENEMY;

		this.objectMatrix = objectMatrix;

	}

	private void defineObjectsFromMatrix(){


		objectTypes = new ArrayList<Integer>();
		objectPositions = new ArrayList<Integer[]>();
		// get the number of rows and columns from the objectMatrix
		int numRows = objectMatrix.length;
		int numCols = objectMatrix[0].length;

		for (int row = 0; row < numRows; row++){
			for (int col = 0; col < numCols; col++){

				if (objectMatrix[row][col]!=OBJECT_BLANK){

					// convert row/col position to x/y pixels
					int x = row*World.TILE_WIDTH;
					int y = col*World.TILE_HEIGHT;

					// store solid collideable walls
					objectTypes.add(objectMatrix[row][col]);
					objectPositions.add(new Integer[] {x,y});

				}

			}
		}


	}

	public List<Integer[]> getObjectPositions() {
		return this.objectPositions;
	}

	


}



