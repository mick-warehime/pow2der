package world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class RandomLevelBuilder {

	protected static final int OBJECT_BLOCK = 1;
	protected static final int OBJECT_ITEM = 2;
	protected static final int OBJECT_DOOR = 3;
	
	private List<Map<Integer,Shape>> objectMaps;

	private int[][] objectMatrix;
	private int tileHeight;
	private int tileWidth;
	

	public RandomLevelBuilder(int tileWidth, int tileHeight){


		this.tileHeight = tileHeight;
		this.tileWidth = tileWidth;

	}


	public void newLevel(int m, int n){
		randomObjectsMatrix(m,n);
		getObjects();
	}
	
	public List<Map<Integer,Shape>> getObjectMaps(){
		return objectMaps;
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

						objectMatrix[row][col] = 0;
					}
				}
				// System.out.print(objectMatrix[row][col] + " ");
			}
		}

		// System.out.println();



		this.objectMatrix = objectMatrix;
	}



	private void getObjects(){


		objectMaps = new ArrayList<Map<Integer,Shape>>();



		// get the number of rows and columns from the objectMatrix
		int numRows = objectMatrix.length;
		int numCols = objectMatrix[0].length;

		for (int row = 0; row < numRows; row++){
			for (int col = 0; col < numCols; col++){
				
				Map<Integer,Shape> obj = new HashMap<Integer,Shape>();

				// convert row/col position to x/y pixels
				int x = row*tileWidth;
				int y = row*tileHeight;

				// store solid collideable walls
				//objectShapes.add(new Rectangle(x,y,tileWidth,tileHeight));
				// objectType.add(objectMatrix[row][col]);
				obj.put(objectMatrix[row][col], new Rectangle(x,y,tileWidth,tileHeight));
				
				objectMaps.add(obj);
			}
		}


	}



}




