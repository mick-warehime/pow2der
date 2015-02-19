package world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class LevelBuilder {

	protected static final int START_PT=-1;

	protected static final int BLANK_TILE = 0;

	protected static final int OBJECT_BLOCK = 1;
	protected static final int OBJECT_ITEM = 2;
	protected static final int OBJECT_DOOR = 3;


	private List<Integer> objectTypes;
	private List<Integer[]> objectPositions;

	private int[][] objectMatrix;


	private int width;
	private int height;

	private int numRoomPuts;				// 100 few rooms   - 1000 densely packed with rooms
	private float probBonusConnection;		// 0 1 door per room - 1 2 doors per room (on average)
	private int numBonusConnection; 		// number of chances (with prob = probBonusCconnection) to add addiational doors
	private float turnBias;					// 0 very straight hallways 1 very windy hallways
	private int roomMax;    				// max size of a room
	private int roomMin;    				// min size of a room

	private int[][] map;
	private int[][] rooms;

	public LevelBuilder(int width, int height){

		this.width = 25;
		this.height = 21;

		probBonusConnection = 0.5f;
		numBonusConnection = 2;

		turnBias = 0.6f;
		roomMin = 3;
		roomMax = 5;

		numRoomPuts = 20;		

		newLevel(50,50);

		newLevel2(width,height);

	}


	public void newLevel2(int m, int n){
		generateRandomRooms();		

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



	private void defineObjectsFromMatrix(){


		objectTypes = new ArrayList<Integer>();
		objectPositions = new ArrayList<Integer[]>();
		// get the number of rows and columns from the objectMatrix
		int numRows = objectMatrix.length;
		int numCols = objectMatrix[0].length;

		for (int row = 0; row < numRows; row++){
			for (int col = 0; col < numCols; col++){

				if (objectMatrix[row][col]!=BLANK_TILE){

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


	public void generateRandomRooms(){

		Random rand = new Random();

		// preallocate the map matrix
		//		for (int j=0; j<width;j++){
		//			for (int k=0; j<height;k++){
		//				map[k][j]=0;
		//			}
		//		}
		map = new int[height][width];
		rooms = new int[numRoomPuts][4];

		// numRoomSizes is a list of odd integers between roomMin and roomMax
		int numRoomSizes = 1+(roomMax-roomMin)/2;

		// each room is in a different "group" these are used to ensure connectivity later
		int group = 1;		
		for (int i = 0; i<numRoomPuts; i++){

			// nextInt is normally exclusive of the top value,
			// so add 1 to make it inclusive
			int topLeftX = 2*rand.nextInt(width/2)+1;
			int topLeftY = 2*rand.nextInt(height/2)+1;

			// random room sizes 
			int roomWidth = 2*rand.nextInt(numRoomSizes)+roomMin;
			int roomHeight = 2*rand.nextInt(numRoomSizes)+roomMin;

			if((topLeftX+roomWidth) > width){
				continue;
			}
			if((topLeftY+roomHeight) > height){
				continue;
			}

			// check to see if there is anything where this room will be placed
			boolean safe = true;
			for (int j=0; j<roomWidth;j++){
				for (int k=0; k<roomHeight;k++){
					System.out.println(topLeftX+" "+topLeftY+" "+j+" "+k);
					if(map[topLeftY+k][topLeftX+j]!=0){
						safe = false;
						break;
					}
				}				
				if(!safe){
					break;					
				}
			}

			if(!safe){
				continue;
			}

			// if it is safe fill in the room
			for (int j=0; j<roomWidth;j++){
				for (int k=0; k<roomHeight;k++){
					map[topLeftY+k][topLeftX+j]=group;					
				}
			}


			// store the details of the room for later
			rooms[group][0] = topLeftX;
			rooms[group][1] = topLeftY;
			rooms[group][2] = roomWidth;
			rooms[group][3] = roomHeight;

			group++;




		}

		for (int j=0; j<width;j++){
			for (int k=0; k<height;k++){
				System.out.print(map[k][j]);
			}
			System.out.println();
		}


	}


}



