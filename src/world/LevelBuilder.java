package world;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;



public class LevelBuilder {

	protected static final int START_PT=-1;

	protected static final int BLANK_TILE = 0;

	protected static final int OBJECT_BLOCK = 1;
	protected static final int OBJECT_ITEM = 2;
	protected static final int OBJECT_DOOR = 3;
	protected static final int OBJECT_ENEMY = 4;


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
	private int numRooms;
	private int groups; 					// each grp # is a unique room/hallway/door

	private final static int LEFT = 0;
	private final static int UP = 1;
	private final static int RIGHT = 2;
	private final static int DOWN = 3;
	
	
	
	public LevelBuilder(int width, int height){

		this.width = 5;
		this.height = 11;

		probBonusConnection = 0.5f;
		numBonusConnection = 2;

		turnBias = 0.6f;
		roomMin = 3;
		roomMax = 7;

		numRoomPuts = 9;		

		newLevel(50,50);

		newLevel2(width,height);

	}


	public void newLevel2(int m, int n){
//		generateRandomRooms();		
		//		addHallways();

//		printMap();


		//		testTestOpen();
				testGetDir();
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
		map = new int[height][width];
		rooms = new int[numRoomPuts][4];

		// numRoomSizes is a list of odd integers between roomMin and roomMax
		int numRoomSizes = 1+(roomMax-roomMin)/2;

		// each room is in a different "group" these are used to ensure connectivity later
		groups = 1;		
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
					//					System.out.println(topLeftX+" "+topLeftY+" "+j+" "+k);
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
					map[topLeftY+k][topLeftX+j]=groups;					
				}
			}


			// store the details of the room for later
			rooms[groups][0] = topLeftX;
			rooms[groups][1] = topLeftY;
			rooms[groups][2] = roomWidth;
			rooms[groups][3] = roomHeight;

			groups++;




		}

		numRooms = groups-1;
	}


	public void addHallways(){



	}

	public int[] openPoint(){

		// given the current map status look for a point on an with odd row and odd column that has no neighbors 


		int[] newPoint = new int[2];

		// loop over all odd points (only want to start mazes on odd tiles)
		for (int xj = 1; xj < width; xj+=2){
			for (int yk = 1; yk < height; yk+=2){

				if(map[yk][xj]==0){

					if(testOpen(xj,yk,-1)){
						newPoint[0] = xj;
						newPoint[1] = yk;
						return newPoint;
					}
				}
			}
		}
		// no new point found
		return newPoint;
	}

	private boolean testOpen(int x,int y, int group){
		boolean open = false;


		// point is open if nothing is adjacent or only 1 other point of the same group is nearby
		int groupCount = 0;
		for(int i = 0; i < 4; i++){
			int xi = x+getXDir(i);
			int yi = y+getYDir(i);


			if(xi>(width-1) || xi<0 || yi>(height-1) || yi<0){
				continue;
			}


			if(map[yi][xi] == 0){
				continue;
			}

			if(map[yi][xi] != group){
				return open;
			}else{
				groupCount++;
			}

		}

		if(groupCount < 2){
			open = true;
		}

		return open;

	}



	private int getNewDir(int x,int y,int oldDir){


		int oldDirX = getXDir(oldDir);
		int oldDirY = getYDir(oldDir);

		ArrayList<Integer> possibleDirs = new ArrayList<Integer>(); 
		for(int newDir = 0; newDir<4; newDir++){

			int newDirX = getXDir(newDir);
			int newDirY = getYDir(newDir);

			// when getting a new direction we dont want to look backwards				
			if(oldDirX == -newDirX & oldDirY == -newDirY){				
				continue;
			}


			// if turning, check if the turn is allowed 
			if(newDir!=oldDir){
				if(!canTurn(x,y,newDir)){
					continue;
				}
			}

			// if continuing in the same direction make sure it is in the map and open

			// take step
			int xNew = x+newDirX;
			int yNew = y+newDirY;

			// make sure it is at lowest in row 1 and at most in height/width-2
			if(xNew<1 | yNew<1 | xNew>(width-1) | yNew<(height-1) ){
				System.out.println(xNew+" "+yNew);
				continue;
			}

			
			
			// if the new spot has no neighbors add it to the possible new directions list
			if( testOpen(xNew,yNew,map[y][x])){
				possibleDirs.add(newDir);				
			}

		}


		int numPossibleDirs = possibleDirs.size();

		if(numPossibleDirs == 0){
			return -1;
		}else if(numPossibleDirs == 1 ){
			return possibleDirs.get(0);
		}

		
		Random rand = new Random();
		if(possibleDirs.contains(oldDir)){

			

			// if the old direction is still open return old direction with frequency according to turnBias

			// higher turn bias more chance to turn
			if(turnBias < Math.random()){
				return oldDir;
			}else{
				possibleDirs.remove(oldDir);
			}

		}
		 
		// return a random element of the remaining directions
		return possibleDirs.get(rand.nextInt(numPossibleDirs+1));
	}

	private boolean canTurn(int x,int y,int newDir){

		boolean canTurn = false;

		// only turn left or right if x is odd 
		if(newDir == 0 | newDir == 2){
			if(y % 2 != 0){
				canTurn = true;
			}
		}
		// only turn up or down if y is odd 
		if(newDir == 1 | newDir == 3){
			if(x % 2 != 0){
				canTurn = true;
			}
		}

		return canTurn;

	}




	private int getXDir(int dirId){

			// left, up , right down
		int[] xdir = new int[] {-1,0,1,0};

		return xdir[dirId];


	}

	private int getYDir(int dirId){


		int[] ydir = new int[] {0,-1,0,1};

		return ydir[dirId];

	}



	private void printMap(){
		for (int k=0; k<height;k++){
			for (int j=0; j<width;j++){

				System.out.print(map[k][j]);
			}
			System.out.println();
		}
	}



	private void testTestOpen(){
		width =3;
		height=4;
		map = new int[height][width];

		for(int i=0;i<width;i++){
			for(int j=0;j<height;j++){
				map[j][i]=0;
			}			
		}

		map[2][2]=1;
		map[1][1]=1;
		map[0][1]=2;
		printMap();

		for(int i=0;i<width;i++){
			for(int j=0;j<height;j++){
				System.out.print(testOpen(i,j,-1));
			}			
			System.out.println();
		}

	}


	private void testGetDir(){
		width  = 9;
		height = 7;
		map = new int[height][width];

		for(int i=0;i<width;i++){
			for(int j=0;j<height;j++){
				map[j][i]=0;
			}			
		}


		map[2][4]=2;
		map[2][3]=2;

		map[1][7]=4;
		map[2][7]=4;
		map[3][7]=4;
		map[4][7]=4;
		map[5][7]=4;


		
		System.out.println(getNewDir(4,2,RIGHT));
		



		printMap();
	}


}



