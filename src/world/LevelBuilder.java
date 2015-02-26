package world;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;



public class LevelBuilder {

	protected static final int START_PT=-1;

	protected static final int BLANK_TILE = 0;

	protected static final int OBJECT_BLOCK = 1;
	protected static final int OBJECT_ITEM = 2;
	protected static final int OBJECT_DOOR = 3;
	protected static final int OBJECT_ENEMY = 4;
	protected static final int DOOR = -2;

	private List<Integer> objectTypes;
	private List<Integer[]> objectPositions;

	private int[][] objectMatrix;


	private int width;
	private int height;

	private int numRoomPuts;				// 100 few rooms   - 1000 densely packed with rooms
	private float probBonusConnections;		// 0 1 door per room - 1 2 doors per room (on average)
	private int numBonusConnections; 		// number of chances (with prob = probBonusCconnection) to add addiational doors
	private float turnBias;					// 0 very straight hallways 1 very windy hallways
	private int roomMax;    				// max size of a room
	private int roomMin;    				// min size of a room

	private long randomSeed;

	private int[][] map;
	private int[][] rooms;
	private ArrayList<Maze> mazes;
	private int numRooms;
	private int groups; 					// each grp # is a unique room/hallway/door

	private Random rand; 

	private Connection connections;

	private final static int LEFT = 0;
	private final static int UP = 1;
	private final static int RIGHT = 2;
	private final static int DOWN = 3;


	// POSSIBLE IMPROVEMENTS / BUGS

	// Backtrack does grabs closest open point instead of a random open point.
	//  can you just get a random open index and try from there???



	public LevelBuilder(int width, int height){

		// premade level by hand
		newLevel(50,50);



		// new stuff
		this.width = 13;
		this.height = 19;

		probBonusConnections = 0.2f;
		numBonusConnections = 2;

		turnBias = 0.7f;
		roomMin = 3;
		roomMax = 3;

		numRoomPuts = 10;		

		// use 1000 for debugging and systemtime for normal use
		randomSeed = 1000;   
		//		randomSeed = System.currentTimeMillis();



		rand = new Random(randomSeed);


//		newLevel2();

	}


	public void newLevel2(){
		generateRandomRooms();		
		addHallways();
		getConnectivty();
		addPassages();

		printMap();

		//      connections.print();
		//		printMap();
		//		testOpenBacktrack();
		//		testOpenPoint();
		//		testTestOpen();
		//      testGetDir();
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

	private void generateRandomRooms(){

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

	private void addHallways(){

		mazes = new ArrayList<Maze>();

		int[] xy = openPoint();
		int x = xy[0];
		int y = xy[1];

		Maze maze = new Maze(x,y,true);

		int oldDir = getNewDir(x,y,-1);
		map[y][x] = groups;

		boolean done = false;
		while(!done){
			//		for(int i=0;i<35;i++){
			int newDir = getNewDir(x,y,oldDir);

			if(newDir!=-1){
				x = x+getXDir(newDir);
				y = y+getYDir(newDir);
				oldDir = newDir;
				map[y][x]=groups;

				// add a new point
				maze.addPoint(x,y,true);
			}else{

				int backtrackIndex = attemptBacktrack(maze);
				//				System.out.println(backtrackIndex);
				//				maze.print();
				if(backtrackIndex>1){
					x = maze.getX(backtrackIndex);
					y = maze.getY(backtrackIndex);

					oldDir = getNewDir(x,y,-1);
					//					System.out.println("x: "+xx+" "+ "y: "+yy+", d: "+oldDir);
					//					maze.print();
					continue;
				}

				xy = openPoint();
				x = xy[0];
				y = xy[1];


				if(x==-1 && y==-1){

					done = true;
				}else{

					// store current maze 
					mazes.add(maze);


					// start new maze
					maze = new Maze(x,y,true);

					groups++;

					oldDir = getNewDir(x,y,-1);
					map[y][x]=groups;


				}
			}



		}

		//		System.out.println(oldDir);

	}

	private int attemptBacktrack(Maze maze){
		//		closes points that cant be accesed (have no free moves)
		// returns an index of an open point
		// returns -1 if no points are open

		//		boolean backtracked = false;

		int numPoints = maze.size()-1;

		// close last point
		maze.setOpen(numPoints, false);
		while(maze.numOpenPoints()>0){
			//		for(int i=0; i<20; i++){
			//						
			//			System.out.println(maze.numOpenPoints());

			// check if current point is already closed
			int randomOpen = maze.randomOpen();
			//			System.out.println(randomOpen);
			//			maze.print();
			int oldDir = backtrackDir(maze,randomOpen); 

			// check if there is an open direction
			int newDir = getNewDir(maze.getX(randomOpen),maze.getY(randomOpen),oldDir);
			//			System.out.println(newDir);
			if(newDir!=-1){					
				// if its an there is 
				return randomOpen;					

			}else{
				// close the point
				maze.setOpen(randomOpen, false);
			}



		}

		//		while(counter>0){
		//			//			
		//			counter--;
		//
		//			// check if current point is already closed
		//			if(maze.getOpen(counter)){
		//
		//				int oldDir = backtrackDir(maze,counter); 
		//
		//				// check if there is an open direction
		//				int newDir = getNewDir(maze.getX(counter),maze.getY(counter),oldDir);
		//				//				System.out.println(newDir);
		//				if(newDir!=-1){					
		//					// if its an there is 
		//					return counter;					
		//
		//				}else{
		//					// close the point
		//					maze.setOpen(counter, false);
		//				}
		//
		//			}
		//
		//		}


		return -1;
	}

	private int backtrackDir(Maze maze, int counter){

		// for a backtrack step we need to know which direction he stepped in

		int xf = maze.getX(counter);
		int xi = maze.getX(counter+1);
		int yf = maze.getY(counter);
		int yi = maze.getY(counter+1);

		if(xf>xi){
			return RIGHT; 
		}
		if(xf<xi){
			return LEFT;		
		}
		if(yf>yi){
			return DOWN; 
		}
		if(yf<yi){
			return UP;		
		}

		return -1;


	}

	private void getConnectivty(){
		connections = new Connection();

		// loop over the entire map  and look for the following points
		// where the a's and b's are 0's in the map


		//      1  a  1 
		//      b  0  0		
		//      2  0  0

		for(int x = 1; x<(width-1); x++){
			for(int y = 1; y<(height-1); y++){

				if(map[y][x]!=0){
					continue;
				}

				// find connections of type b
				if(map[y+1][x]!=0 && map[y-1][x]!=0){
					if(map[y+1][x] != map[y-1][x]){
						connections.addConnection(x,y,map[y-1][x],map[y+1][x]);
						continue;
					}
				}

				// find connections of type a
				if(map[y][x+1]!=0 && map[y][x-1]!=0){
					if(map[y][x+1]!=map[y][x-1]){
						connections.addConnection(x,y,map[y][x-1],map[y][x+1]);
					}
				}


			}
		}


	}

	private class Connection{

		private ArrayList<Integer> xList = new ArrayList<Integer>();
		private ArrayList<Integer> yList = new ArrayList<Integer>();
		private ArrayList<Integer> group1List = new ArrayList<Integer>();
		private ArrayList<Integer> group2List = new ArrayList<Integer>();

		private Connection(){}

		private void addConnection(int x, int y, int group1, int group2) {			
			xList.add(x);
			yList.add(y);
			group1List.add(group1);
			group2List.add(group2);
		}

		private void removeConnection(int index) {			
			xList.remove(index);
			yList.remove(index);
			group1List.remove(index);
			group2List.remove(index);
		}

		private int getX(int index){
			return xList.get(index);
		}
		private int getY(int index){
			return yList.get(index);
		}

		private void removeConnectionsBetweenGroups(int group1, int group2){
			ArrayList<Integer> connectedList = new ArrayList<Integer>();

			int numConnections = xList.size();

			// find all points connected to the given group
			for (int j=0; j<numConnections;j++){
				if(group1List.get(j)==group1 && group2List.get(j)==group2){					
					connectedList.add(j);	
					continue;
				}
				if(group1List.get(j)==group2 && group2List.get(j)==group1){					
					connectedList.add(j);					
				}
			}

			// delete the reverse sorted list to ensure list to ensure proper deletion 
			Collections.reverse(connectedList);
			for (int j = 0; j<connectedList.size(); j++){
				removeConnection(connectedList.get(j));
			}
		}

		private int getOtherGroup(int index, int group){
			int g1 = group1List.get(index);
			int g2 = group2List.get(index);

			if(g1==group){
				return g2;
			}
			if(g2==group){
				return g1;
			}
			return -1;
		}

		private int randomConnectionToGroup(int group){

			ArrayList<Integer> connectedList = new ArrayList<Integer>();

			int numConnections = xList.size();
			int numConnectedToGroup = 0;

			// find all points connected to the given group
			for (int j=0; j<numConnections;j++){
				if(group1List.get(j)==group || group2List.get(j)==group){					
					connectedList.add(j);
					numConnectedToGroup++;					
				}
			}
			if(connectedList.size()>0){
				return connectedList.get(rand.nextInt(numConnectedToGroup));
			}else{
				return -1;
			}
		}

		private void print(){
			int numConnections = xList.size();

			for (int j=0; j<numConnections;j++){
				int x = xList.get(j);
				int y = yList.get(j);
				int g1 = group1List.get(j);
				int g2 = group2List.get(j);
				System.out.format(" %2d %2d %2d %2d \n",x,y,g1,g2);

			}
		}


	}


	private void addPassages() {

		// 1) pick a random group and add a random connection to it
		// 2) for numBonusConnections attempts add another connection involving this group
		// 3) add group from (1) to conected list
		// 4) remove all connections between two groups in (1)
		// 5) repeat until all groups are connected
		
		int numGroups = numRooms + mazes.size();

		HashSet<Integer> connectedGroups = new HashSet<Integer>();
		ArrayList<Integer> connected = new ArrayList<Integer>();
				
		// the first group is picked from the rooms
		int currentGroup = rand.nextInt(numRooms)+1;

		connectedGroups.add(currentGroup);
		connected.add(currentGroup);
		int nextGroup = currentGroup;

				for(int kk = 0; kk<500; kk++){
//		while(connectedGroups.size()<(numGroups)){
					
//					System.out.println(currentGroup);
					
					
			for(int i = 0; i < (1+numBonusConnections); i++){

				
				
				// add a routine that takes the connectedToGroupt hashset and finds a random point connected to any member of the group
				
				
				// get a new connection to the current group
				int connectionIndex = connections.randomConnectionToGroup(currentGroup);
				
//				System.out.println(connectionIndex);
				// if there are no connections get a random group and try again
				if(connectionIndex==-1){
					
					// you cant get items of a HashSet
					int size = connectedGroups.size();
					int randIndex = rand.nextInt(size);
					currentGroup = connected.get(randIndex);
					
					continue;
				}

				// always add a connection to the first iteration
				// only add additional connections probBonusConnection percent of the time
				if(i==0 || (probBonusConnections>rand.nextFloat()) ){

					addPassageToMap(connectionIndex);

					int newGroup = connections.getOtherGroup(connectionIndex, currentGroup);

					// prevent repeat connecitons by removing the current connection
					connections.removeConnection(connectionIndex);

					if(i==0){
						nextGroup = newGroup;
						connectedGroups.add(newGroup);
						connected.add(newGroup);
					}
				}

			}

			// delete all connections between currentGroup and nextGroup to ensure the next round
			// will connected nextGroup with a new group and not recconected to currentGroup
			connections.removeConnectionsBetweenGroups(currentGroup,nextGroup);
			currentGroup = nextGroup;
//			connections.print();
//			System.out.println(connectedGroups+" "+numGroups);

		}
	}

	private void addPassageToMap(int connectionIndex){

		int x = connections.getX(connectionIndex);
		int y = connections.getY(connectionIndex);

		map[y][x] = DOOR;

	}



	private class Maze{
		private ArrayList<Integer> xList;
		private ArrayList<Integer> yList;
		private ArrayList<Boolean> openList;
		private int numPoints;
		private Maze(int x, int y, boolean open){

			xList = new ArrayList<Integer>();
			yList = new ArrayList<Integer>();
			openList = new ArrayList<Boolean>();

			addPoint(x,y,open); 


		}

		private void addPoint(int x, int y, boolean open){
			xList.add(x);
			yList.add(y);
			openList.add(open);
			numPoints++;
		}



		private int getX(int index){
			return xList.get(index);
		}
		private int getY(int index){
			return yList.get(index);
		}
		private boolean getOpen(int index){
			return openList.get(index);
		}

		private boolean setOpen(int index, boolean open){
			return openList.set(index, open);
		}

		private int size(){
			return xList.size();
		}

		private int numOpenPoints(){
			int n = xList.size();
			int count = 0;
			for(int i = 0; i<n; i++){
				if(openList.get(i)){
					count++;
				}
			}			
			return count;
		}

		private int randomOpen(){

			ArrayList<Integer> openPts = new ArrayList<Integer>();
			int numOpen = 0;
			//			get indices of open points
			for(int j = 0; j<numPoints; j++){
				if(openList.get(j)){
					openPts.add(j);
					numOpen++;
				}
			}



			// return an open point at random 
			if(numOpen>0){
				return openPts.get(rand.nextInt(numOpen));
			}else{
				return -1;
			}

		}

		private void print(){

			int n = xList.size();
			for(int i = 0; i<n; i++){
				System.out.println("ind: "+i+", x: "+xList.get(i)+", y: "+yList.get(i)+", op: "+openList.get(i));
			}


		}

	}

	private int[] openPoint(){

		// given the current map status look for a point on an with odd row and odd column that has no neighbors 


		int[] newPoint = new int[] {-1,-1};

		// loop over all odd points (only want to start mazes on odd tiles)
		for (int xj = 1; xj < width; xj+=2){
			for (int yk = 1; yk < height; yk+=2){

				if(map[yk][xj]==0){
					if(xj<(width-1) && xj>0 && yk<(height-1) && yk>0){

						if(testOpen(xj,yk,-1)){
							newPoint[0] = xj;
							newPoint[1] = yk;
							return newPoint;
						}
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


			if(xi>(width-1) || xi<1 || yi>(height-1) || yi<1){
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
		// given some point (x,y) in the map and  


		//		Keeps track of whether or not you can keep going straight ahead 
		boolean oldDirOpen = false;

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
			if(xNew<1 | yNew<1 | xNew>(width-2) | yNew>(height-2) ){
				//				System.out.println("out of bouds");
				continue;
			}

			// if the new spot has no neighbors add it to the possible new directions list
			if( testOpen(xNew,yNew,map[y][x])){
				if(newDir == oldDir){
					oldDirOpen = true;
				}else{
					possibleDirs.add(newDir);
				}
			}

		}
		//		System.out.println(possibleDirs);
		int numPossibleDirs = possibleDirs.size();

		if(numPossibleDirs == 0){
			if(oldDirOpen){
				return oldDir;
			}
			//			System.out.println("no available turns");
			return -1;
		}else if(numPossibleDirs == 1 ){
			return possibleDirs.get(0);
		}

		// if the old direction is still open return old direction with frequency according to turnBias  		
		if(oldDirOpen & (turnBias > Math.random())){
			//			System.out.println("here");
			return oldDir;			
		}

		// return a random new direction
		return possibleDirs.get(rand.nextInt(numPossibleDirs));
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
		if(dirId==-1){
			return -1;			
		}
		// left, up , right down
		int[] xdir = new int[] {-1,0,1,0};

		return xdir[dirId];


	}

	private int getYDir(int dirId){
		if(dirId==-1){
			return -1;			
		}

		int[] ydir = new int[] {0,-1,0,1};

		return ydir[dirId];

	}





	//////////////////////////////////////////////////////	//////
	//	
	//	  DIAGNOSTIC / TESTING / PRINTING
	//	
	//	//////////////////////////////////////////////////////



	private void printMap(){
		for (int k=0; k<height;k++){
			for (int j=0; j<width;j++){

				System.out.format(" %2d ",map[k][j]);
			}
			System.out.println();
		}
	}


	private void createMap(int height, int width){
		map = new int[height][width];

		for(int i=0;i<width;i++){
			for(int j=0;j<height;j++){
				map[j][i]=0;
			}			
		}


	}

	private void testOpenBacktrack(){
		height = 4;
		width = 3;
		createMap(height,width);

		map[2][1]=2;
		map[1][1]=2;
		map[0][1]=2;
		printMap();
		System.out.println(testOpen(1,1,1));



	}

	//	private void testOpenPoint(){
	//		height = 4;
	// width = 3;
	// 	int[][] map = createMap(height,width);

	//
	//		map[2][2]=1;
	//		map[1][1]=1;
	//		map[0][1]=2;
	//		printMap();
	//
	//		int[] op = openPoint();
	//		System.out.println("x: "+" " +op[0]+" "+"y: "+" " +op[1]);
	//	}

	//	private void testTestOpen(){
	//		width =3;
	//		height=4;
	//		map = new int[height][width];
	//
	//		for(int i=0;i<width;i++){
	//			for(int j=0;j<height;j++){
	//				map[j][i]=0;
	//			}			
	//		}
	//
	//		map[2][2]=1;
	//		map[1][1]=1;
	//		map[0][1]=2;
	//		printMap();
	//
	//		for(int i=0;i<width;i++){
	//			for(int j=0;j<height;j++){
	//				System.out.print(testOpen(i,j,-1));
	//			}			
	//			System.out.println();
	//		}
	//
	//	}

	//
	//	private void testGetDir(){
	//		width  = 9;
	//		height = 7;
	//		map = new int[height][width];
	//
	//		for(int i=0;i<width;i++){
	//			for(int j=0;j<height;j++){
	//				map[j][i]=0;
	//			}			
	//		}
	//
	//
	//		map[2][2]=2;
	//		map[2][3]=2;
	//
	//		map[1][7]=4;
	//		map[2][7]=4;
	//		map[3][7]=4;
	//		map[4][7]=4;
	//		map[5][7]=4;
	//
	//
	//
	//		System.out.println(getNewDir(3,2,RIGHT));
	//
	//
	//
	//
	//		printMap();
	//	}


}



