package world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class LevelBuilder {

	protected static final int START_PT=-1;
	
	protected static final int BLANK_TILE = 0;
	
	protected static final int OBJECT_BLOCK = 1;
	protected static final int OBJECT_ITEM = 2;
	protected static final int OBJECT_DOOR = 3;
	
	
	private List<Integer> objectTypes;
	private List<Shape> objectShapes;

	private int[][] objectMatrix;

	public LevelBuilder(int m, int n){

		newLevel(m,n);

	}


	public void newLevel(int m, int n){

		randomObjectsMatrix(m,n);
		getObjects();

	}

	public List<Integer> getObjectTypes(){
		return objectTypes;
	}
	public List<Shape> getObjectShapes(){
		return objectShapes;
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

						objectMatrix[row][col] = BLANK_TILE;
					}
				}
				// System.out.print(objectMatrix[row][col] + " ");
			}
		}

		// System.out.println();

		// add something to run into in the middle of the map
		objectMatrix[12][10] = OBJECT_BLOCK;
		objectMatrix[2][2] = START_PT;

		this.objectMatrix = objectMatrix;
		
	}



	private void getObjects(){




		objectShapes = new ArrayList<Shape>();
		objectTypes = new ArrayList<Integer>();
		// get the number of rows and columns from the objectMatrix
		int numRows = objectMatrix.length;
		int numCols = objectMatrix[0].length;

		for (int row = 0; row < numRows; row++){
			for (int col = 0; col < numCols; col++){
				
				if (objectMatrix[row][col]!=BLANK_TILE){


					Map<Integer,Shape> obj = new HashMap<Integer,Shape>();

					// convert row/col position to x/y pixels
					int x = row*World.TILE_WIDTH;
					int y = col*World.TILE_HEIGHT;

					// store solid collideable walls
					objectShapes.add(new Rectangle(x,y,World.TILE_WIDTH,World.TILE_HEIGHT));
					objectTypes.add(objectMatrix[row][col]);
				
				}

			}
		}


	}



}




