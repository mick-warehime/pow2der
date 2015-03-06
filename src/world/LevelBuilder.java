package world;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;



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
	protected static final int OBJECT_WALL_TILE = 8;
	// used for building map
	protected static final int DOOR = -2;
	private final static int LEFT = 0;
	private final static int UP = 1;
	private final static int RIGHT = 2;
	private final static int DOWN = 3;

	// this can be an odd number bigger than 1
	public final static int SCALING = 5;

	public final static int DOORSIZE = 3;					// when map(y,x) is a door DOORSIZE tells MAP how big to make the hole for a door 


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
	private int[][] MAP;
	private int[][] rooms;
	private ArrayList<Maze> mazes;
	private int numRooms;
	private int groups; 					// each grp # is a unique room/hallway/door


	private Random rand;

	private ArrayList<Shape> walls;
	private ArrayList<Shape> doors;
	private ArrayList<Shape> floors;
	private ArrayList<Shape> halls;


	public LevelBuilder(int width, int height){

		// new stuff
		this.width = width;
		this.height = height;

		probBonusConnections = 0.3f;
		numBonusConnections = 2;

		turnBias = 0.7f;
		roomMin = 3;
		roomMax = 7;

		numRoomPuts = 50;		

		// use 1000 for debugging and systemtime for normal use
		//		randomSeed = 1000;   
		randomSeed = System.currentTimeMillis();

		rand = new Random(randomSeed);


		generateRandomRooms();		
		addHallways();		
		addPassages();

		for(int i=0; i<2;i++){
			removeDeadEnds();
			removeBogusDoors();
		}

		fillInHallways();


		reduceMapGroups(map);

		scaleMap();
		removeBogusWalls();
		createShapes(MAP);
		createDoorShapes(map);

		//		printMap(map);
		//		printMap(MAP);



		//      connections.print();
		//		printMap();
		//		testOpenBacktrack();
		//		testOpenPoint();
		//		testTestOpen();
		//      testGetDir();
	}

	public ArrayList<int[]> generateRandomItemLocations(double placeProbability, int placeAttempts){


		ArrayList<int[]> itemLocations = new ArrayList<int[]>();

		//		for every room in the map this routine tries to place an item placeAttempts time
		//		if a random number is smaller than placeProbability then an item is placed randomly in that room

		for(int i = 0; i < numRooms; i++){
			for(int j = 0; j<placeAttempts; j++){
				if(placeProbability > Math.random()){
					// start in the center of a random room		

					int topLeftX = rooms[i][0];
					int topLeftY = rooms[i][1];
					int width = rooms[i][2];
					int height = rooms[i][3];

					int xPos = rand.nextInt((width-1)*World.TILE_WIDTH)+topLeftX*World.TILE_WIDTH;
					int yPos = rand.nextInt((height-1)*World.TILE_WIDTH)+topLeftY*World.TILE_WIDTH;

					map[(int) (topLeftY + Math.floor(height/2)+1)][(int) (topLeftX + Math.floor(width/2)+1)] =5;



					itemLocations.add(new int[] {xPos*SCALING, yPos*SCALING});
				}
			}
		}

		return itemLocations;

	}


	// change this to get a point in a random room
	public int[] getStartingPosition(){

		// start in the center of a random room		
		int randRoom = rand.nextInt(numRooms);

		int topLeftX = rooms[randRoom][0];
		int topLeftY = rooms[randRoom][1];
		int width = rooms[randRoom][2];
		int height = rooms[randRoom][3];

		int xPos = (int) (topLeftX + Math.floor(width/2))*World.TILE_WIDTH;
		int yPos = (int) (topLeftY + Math.floor(height/2))*World.TILE_HEIGHT;

		map[(int) (topLeftY + Math.floor(height/2)+1)][(int) (topLeftX + Math.floor(width/2)+1)] =5;



		int[] position = new int[] {xPos*SCALING, yPos*SCALING};

		return position;	
	}

	public ArrayList<Shape> getWalls(){
		return walls;
	}
	public ArrayList<Shape> getDoors(){
		return doors;
	}
	public ArrayList<Shape> getFloors(){
		return floors;
	}
	public ArrayList<Shape> getHalls(){
		return halls;
	}

	public int[][] getMap(){
		return MAP;
	}
	public int[][] getMiniMap(){
		return map;
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
			rooms[groups-1][0] = topLeftX;
			rooms[groups-1][1] = topLeftY;
			rooms[groups-1][2] = roomWidth;
			rooms[groups-1][3] = roomHeight;

			groups++;




		}

		numRooms = groups-1;
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

		private void removePoint(int index){
			xList.remove(index);
			yList.remove(index);
			openList.remove(index);
			numPoints--;
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

		@SuppressWarnings("unused")
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

		@SuppressWarnings("unused")
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

		@SuppressWarnings("unused")
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
					if(xj<(width-1) & xj>0 & yk<(height-1) & yk>0){

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

			// make sure no one is where you are going (relevant for backtracking)
			if(map[yNew][xNew]!=0){
				continue;
			}

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

			int newDir = getNewDir(x,y,oldDir);

			if(newDir!=-1){
				x = x+getXDir(newDir);
				y = y+getYDir(newDir);
				oldDir = newDir;
				map[y][x]=groups;

				// add a new point
				maze.addPoint(x,y,true);
			}else{

				// try to backtrack
				int backtrackIndex = maze.size()-1;

				// close last point
				maze.setOpen(backtrackIndex, false);

				boolean getNewPoint = true;
				while(backtrackIndex>0){

					backtrackIndex--;

					// skip previously closed points
					if(!maze.getOpen(backtrackIndex)){
						continue;
					}

					// direction from n+1 -> n
					oldDir = backtrackDir(maze,backtrackIndex); 

					// check if there is an open direction
					newDir = getNewDir(maze.getX(backtrackIndex),maze.getY(backtrackIndex),oldDir);

					if(newDir==-1){
						// close the point
						maze.setOpen(backtrackIndex, false);
					}else{
						getNewPoint = false;
						break;
					}									

				}

				// if there was an open point on the maze keep going
				if(!getNewPoint){

					x = maze.getX(backtrackIndex);
					y = maze.getY(backtrackIndex);

					oldDir = getNewDir(x,y,-1);
					continue;
				}


				// if there were no open points try to start a new maze
				xy = openPoint();
				x = xy[0];
				y = xy[1];

				// if there are no remaining open points stop looking for mazes
				if(x==-1 & y==-1){
					done = true;
				}else{

					// store current maze 
					mazes.add(maze);					

					// start new maze
					maze = new Maze(x,y,true);

					// increase the group counter for the new maze
					groups++;

					oldDir = getNewDir(x,y,-1);

					map[y][x]=groups;

				}

			}



		}

		mazes.add(maze);
		//		System.out.println(oldDir);

	}

	private int backtrackDir(Maze maze, int index){

		// for a backtrack step we need to know which direction he stepped in

		int xf = maze.getX(index);
		int xi = maze.getX(index+1);
		int yf = maze.getY(index);
		int yi = maze.getY(index+1);

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

	private Connection getConnectivty(int[][] map){
		Connection connections = new Connection();

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
				if(map[y+1][x]!=0 & map[y-1][x]!=0){
					if(map[y+1][x] != map[y-1][x]){
						connections.addConnection(x,y,map[y-1][x],map[y+1][x]);
						continue;
					}
				}

				// find connections of type a
				if(map[y][x+1]!=0 & map[y][x-1]!=0){
					if(map[y][x+1]!=map[y][x-1]){
						connections.addConnection(x,y,map[y][x-1],map[y][x+1]);
					}
				}


			}
		}

		return connections;


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

		@SuppressWarnings("unused")
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

		private void absorbGroup(int connectedGroup, int newGroup,int[][] map) {

			//			find all newGroup in map and replace with connectedgroup

			for(int i=0;i<width;i++){
				for(int j=0;j<height;j++){
					if(map[j][i]==newGroup){
						map[j][i]=connectedGroup;
					}
				}			
			}

		}


	}

	private void addPassages() {

		// 1) pick a random group and add a random connection to it
		// 2) for numBonusConnections attempts add another connection involving this group
		// 3) add group from (1) to conected list
		// 4) remove all connections between two groups in (1)
		// 5) repeat until all groups are connected

		int[][] tempMap = new int[height][];
		for(int i = 0; i < height; i++){
			tempMap[i] = map[i].clone();
		}

		Connection connections = getConnectivty(tempMap);

		int numGroups = numRooms + mazes.size()+1;

		ArrayList<Integer> connected = new ArrayList<Integer>();

		// the first group is picked from the rooms
		int currentGroup = rand.nextInt(numRooms)+1;
		connected.add(currentGroup);

		for(int kk = 0; kk<500; kk++){

			connections = getConnectivty(tempMap);
			for(int i = 0; i < (1+numBonusConnections); i++){

				// get a new connection to the current group
				int connectionIndex = connections.randomConnectionToGroup(currentGroup);
				// if there are no connections get a random group and try again
				if(connectionIndex==-1){
					continue;
				}

				// always add a connection to the first iteration
				// only add additional connections probBonusConnection percent of the time
				if(i==0 || (probBonusConnections>rand.nextFloat()) ){

					addPassageToMap(connectionIndex, connections);

					int newGroup = connections.getOtherGroup(connectionIndex, currentGroup);

					// prevent repeat connecitons by removing the current connection
					connections.removeConnection(connectionIndex);

					if(i==0){
						connected.add(newGroup);
						connections.absorbGroup(currentGroup,newGroup,tempMap);
					}
				}

			}
			if(connected.size()==(numGroups-1)){
				return;
			}		
		}
		System.out.println("MAP NOT BUILT CORRECTLY. NOT ALL GROUPS PROPERLY CONNECTED");
	}

	private void addPassageToMap(int connectionIndex, Connection connections){

		int x = connections.getX(connectionIndex);
		int y = connections.getY(connectionIndex);

		map[y][x] = DOOR;

	}

	private void removeDeadEnds(){

		// loop over all Mazes in mazes list
		for(int j = 0; j<mazes.size(); j++){

			Maze mz = mazes.get(j);

			boolean hasDeadEnds = true;

			while(hasDeadEnds){

				// if a point was deleted look again for more deletable points
				boolean deletedPoint = false;
				for(int i = 0; i<mz.size(); i++){

					// current point in maze
					int x = mz.getX(i);
					int y = mz.getY(i);
					//					System.out.println(map[y][x]);
					// a point should be deleted if there is only 1 neighbor
					if(numAdjacentPoints(x,y)<2){
						mz.removePoint(i);
						map[y][x]=0;
						deletedPoint = true;
						break;
					}


				}
				// if a point was deleted keep looking
				if(!deletedPoint){
					// if it gets to the end there are no deadEnds left
					hasDeadEnds = false;
				}
			}
		}


	}

	private int numAdjacentPoints(int x, int y){

		int numAdj = 0;
		for(int d = 0; d < 4; d++){
			int xd = x+getXDir(d);
			int yd = y+getYDir(d);

			if(map[yd][xd] !=0){
				numAdj++;
			}

			if(numAdj>1){
				break;
			}

		}

		return numAdj;
	}

	private void removeBogusDoors(){
		for (int y=1; y<(height-1);y++){
			for (int x=1; x<(width-1);x++){

				if(map[y][x]==DOOR){
					if(numAdjacentPoints(x,y)<2){
						map[y][x]=0;
					}

					// three doors in a row is too much
					if(map[y+1][x]==DOOR&map[y-1][x]==DOOR){
						map[y][x]=0;
					}
					if(map[y][x+1]==DOOR&map[y][x-1]==DOOR){
						map[y][x]=0;
					}

				}



			}
		}

	}


	private void fillInHallways(){
		//		this fills in gaps in winding hallways
		//  1 1 1		1 1 1
		//  0 0 1  --> 	0 1 1
		//  0 1 1		0 1 1
		//
		for (int y=1; y<(height-1);y++){
			for (int x=1; x<(width-1);x++){

				if(map[y][x]==0){
					if((map[y+1][x]==map[y-1][x]) & map[y+1][x]!=DOOR){
						map[y][x]=map[y+1][x];
					}

					if((map[y][x+1]==map[y][x-1])& map[y][x+1]!=DOOR){
						map[y][x]=map[y][x+1];
					}
				}
			}
		}

	}




	private void reduceMapGroups(int[][] map){

		// at the start of this routine each hall / room has a unique number
		// at the end of this routine all rooms have the same number
		// and all halls have the same number

		for (int y=0; y<height;y++){
			for (int x=0; x<width;x++){
				if(map[y][x]!=0){
					if(map[y][x]==DOOR){
						map[y][x] = OBJECT_DOOR_TILE;
					} else if(map[y][x]<= numRooms){					
						map[y][x] = OBJECT_ROOM_TILE;		

					} else{					
						map[y][x] = OBJECT_HALLWAY_TILE;
					}
				}
			}
		}
	}

	private void createShapes(int[][] map){

		int height = map.length;
		int width = map[0].length;

		this.walls = new ArrayList<Shape>();		
		this.floors = new ArrayList<Shape>();
		this.halls = new ArrayList<Shape>();

		for (int y=0; y<height;y++){
			for (int x=0; x<width;x++){

				int xPos = x*World.TILE_WIDTH;
				int yPos = y*World.TILE_HEIGHT;

				if(map[y][x]==OBJECT_WALL_TILE){
					walls.add(new Rectangle(xPos,yPos, World.TILE_WIDTH, World.TILE_HEIGHT));					
				} else if(map[y][x] == OBJECT_ROOM_TILE){										
					floors.add(new Rectangle(xPos,yPos, World.TILE_WIDTH, World.TILE_HEIGHT));
				} else if(map[y][x] == OBJECT_HALLWAY_TILE){										
					halls.add(new Rectangle(xPos,yPos, World.TILE_WIDTH, World.TILE_HEIGHT));
				}

			}
		}		
	}


	private void createDoorShapes(int[][] map){

		int height = map.length;
		int width = map[0].length;

		this.doors = new ArrayList<Shape>();

		// not entirely sure why this is necessary
		int scalingOffset = (SCALING-1)/2-1;

		// loop over the doors, determine their orientation n/s or e/w and make the shape according to doorsize
		for (int y=0; y<height;y++){
			for (int x=0; x<width;x++){



				if(map[y][x]==OBJECT_DOOR_TILE){
					if(northSouthDoor(x,y)){						
						int xPos = (x*SCALING+scalingOffset)*World.TILE_WIDTH;
						int yPos = (y*SCALING+scalingOffset+1)*World.TILE_HEIGHT;
						doors.add(new Rectangle(xPos,yPos, DOORSIZE*World.TILE_WIDTH, World.TILE_HEIGHT));
					}else{
						int xPos = (x*SCALING+scalingOffset+1)*World.TILE_WIDTH;
						int yPos = (y*SCALING+scalingOffset)*World.TILE_HEIGHT;
						doors.add(new Rectangle(xPos,yPos, World.TILE_WIDTH, DOORSIZE*World.TILE_HEIGHT));
					}
				}  

			}
		}
	}


	private void scaleMap(){

		MAP = new int[height*SCALING][width*SCALING];

		for (int y=0; y<height;y++){
			for (int x=0; x<width;x++){

				// for every point in map add SCALING^2 points to MAP


				// room or hallway tile just blow them up to SCALING^2 size
				if(map[y][x]==OBJECT_ROOM_TILE || map[y][x]==OBJECT_HALLWAY_TILE){
					for(int X = 0; X < SCALING; X++){
						for(int Y = 0; Y < SCALING; Y++){

							MAP[Y+y*SCALING][X+x*SCALING] = map[y][x];
						}					
					}
				}

				// replace doors with the correct form 
				// either a centered door in a vertical or horizontal wall 
				// depending on the doors direction
				if(map[y][x]==OBJECT_DOOR_TILE){
					addScaledDoor(x, y);
				}

				// if any of the 8 points adjacent to this tile have a nonzero element then we need to add walls
				if(map[y][x]==0 & needsWalls(x,y)){

					addCornerWalls(x,y);
					addSideWalls(x,y);

				}



			}
		}

	}

	private void addSideWalls(int x, int y){

		// if top/left/right/bottom has a tile add it to entire side
		// if SCALING = 5
		// 
		//				0 0 X 6 6
		//  0 0 0       0 0 X 6 6
		//  0 5 6  -->  X X X 6 6
		//  0 0 0		0 0 X 6 6
		//				0 0 X 6 6

		int midPt = Math.round(SCALING/2);


		// top
		if(y-1>0){
			if( map[y-1][x]==OBJECT_ROOM_TILE || map[y-1][x]==OBJECT_HALLWAY_TILE ){
				for(int X = 0; X < SCALING; X++){
					for(int Y = 0; Y < midPt; Y++){					
						MAP[Y+y*SCALING][X+x*SCALING] = map[y-1][x];				
					}	
				}
			}
		}

		// bottom
		if(y+1<height){
			if( map[y+1][x]==OBJECT_ROOM_TILE || map[y+1][x]==OBJECT_HALLWAY_TILE ){
				for(int X = 0; X < SCALING; X++){
					for(int Y = midPt+1; Y < SCALING; Y++){					
						MAP[Y+y*SCALING][X+x*SCALING] = map[y+1][x];				
					}	
				}
			}
		}

		// left
		if(x-1>0){
			if( map[y][x-1]==OBJECT_ROOM_TILE || map[y][x-1]==OBJECT_HALLWAY_TILE ){
				for(int X = 0; X < midPt; X++){
					for(int Y = 0; Y < SCALING; Y++){					
						MAP[Y+y*SCALING][X+x*SCALING] = map[y][x-1];				
					}
				}
			}
		}

		// right
		if(x+1<width){
			if( map[y][x+1]==OBJECT_ROOM_TILE || map[y][x+1]==OBJECT_HALLWAY_TILE ){
				for(int X = midPt+1; X < SCALING; X++){
					for(int Y = 0; Y < SCALING; Y++){					
						MAP[Y+y*SCALING][X+x*SCALING] = map[y][x+1];				
					}	
				}
			}
		}



	}

	private void addCornerWalls(int x, int y){

		// for every point that needs to add walls 
		// add the corner tiles first

		//    if SCALING = 5
		// 
		//				1 1 X 3 3
		//  1 2 3       1 1 X 3 3
		//  4 5 6  -->  X X X X X
		//  7 8 9		7 7 X 9 9
		//				7 7 X 9 9

		// where X is a wall

		int midPt = Math.round(SCALING/2);
		int mapXY = 0;
		boolean addPoint = false;

		for(int X = 0; X < SCALING; X++){
			for(int Y = 0; Y < SCALING; Y++){

				addPoint = false;

				// top left
				if(X<midPt & Y<midPt){
					if(y>0 & x>0){
						addPoint = true;
						mapXY = map[y-1][x-1];

						// dont add extra doors
						if(mapXY==OBJECT_DOOR_TILE){
							if(northSouthDoor(x-1,y-1)){
								mapXY = map[y][x-1];
							}else{
								mapXY = map[y-1][x];
							}
						}


					}

					// bottom left
				}else if(X<midPt & Y>midPt){
					if(y<(height-1) & x>0){
						addPoint = true;
						mapXY = map[y+1][x-1];

						// dont add extra doors
						if(mapXY==OBJECT_DOOR_TILE){
							if(northSouthDoor(x-1,y+1)){
								mapXY = map[y][x-1];
							}else{
								mapXY = map[y+1][x];
							}
						}

					}

					// bottom right
				}else if(X>midPt & Y>midPt){
					if(y<(height-1) & x<(width-1)){
						addPoint = true;
						mapXY = map[y+1][x+1];

						// dont add extra doors
						if(mapXY==OBJECT_DOOR_TILE){
							if(northSouthDoor(x+1,y+1)){
								mapXY = map[y][x+1];
							}else{
								mapXY = map[y+1][x];
							}
						}

					}

					// top right
				}else if(X>midPt & Y<midPt){
					if(y>0 & x<(width-1)){
						addPoint = true;
						mapXY = map[y-1][x+1];

						// dont add extra doors
						if(mapXY==OBJECT_DOOR_TILE){
							if(northSouthDoor(x+1,y-1)){
								mapXY = map[y][x+1];
							}else{
								mapXY = map[y-1][x];
							}
						}


					}
				}else{
					addPoint = true;
					mapXY = OBJECT_WALL_TILE;
				}


				if(addPoint){
					MAP[Y+y*SCALING][X+x*SCALING] = mapXY;
				}

			}
		}
	}

	private boolean needsWalls(int x,int y){
		boolean needsWall = false;

		for(int i = -1; i<2; i++){
			for(int j = -1; j<2; j++){

				// confine check to points in map
				if((x+i)<0 || (y+j)<0 || (x+i)>(width-1) || (y+j)>(height-1)){
					continue;
				}

				// check if there are nonzero neighbors
				if(map[y+j][x+i]!=0){	
					needsWall = true;
					return needsWall;
				}
			}			
		}

		return needsWall;

	}

	private void addScaledDoor(int x, int y){

		int midPt = Math.round(SCALING/2)+1;

		int doorBuffer = (SCALING-DOORSIZE)/2;

		// add a horizontal door
		if(northSouthDoor(x,y)){
			//						System.out.println("north-south door");
			for(int X = 0; X < SCALING; X++){
				for(int Y = 0; Y < SCALING; Y++){

					if(Y == midPt-1){
						if(X<doorBuffer || X>(doorBuffer+DOORSIZE-1)){
							MAP[Y+y*SCALING][X+x*SCALING] = OBJECT_WALL_TILE;
						}else{
							MAP[Y+y*SCALING][X+x*SCALING] = OBJECT_DOOR_TILE;
						}
					}else if(Y<(midPt-1)){
						MAP[Y+y*SCALING][X+x*SCALING] = map[y-1][x];
					}else if(Y>(midPt-1)){
						MAP[Y+y*SCALING][X+x*SCALING] = map[y+1][x];									
					}

				}					
			}
		}
		else{
			// add vertical door centered with size DOORSIZE
			for(int X = 0; X < SCALING; X++){
				for(int Y = 0; Y < SCALING; Y++){

					if(X == midPt-1){
						if(Y<doorBuffer || Y>(doorBuffer+DOORSIZE-1)){
							MAP[Y+y*SCALING][X+x*SCALING] = OBJECT_WALL_TILE;
						}else{
							MAP[Y+y*SCALING][X+x*SCALING] = OBJECT_DOOR_TILE;
						}
					}else if(X<(midPt-1)){
						MAP[Y+y*SCALING][X+x*SCALING] = map[y][x-1];
					}else if(X>(midPt-1)){
						MAP[Y+y*SCALING][X+x*SCALING] = map[y][x+1];									
					}

				}					
			}


		}
	}

	// checks which direction the door should face

	private boolean northSouthDoor(int x, int y){
		boolean northSouth = false;
		if(map[y-1][x]==OBJECT_ROOM_TILE||map[y+1][x]==OBJECT_ROOM_TILE){
			northSouth = true;
		}
		if(map[y-1][x]==OBJECT_HALLWAY_TILE||map[y+1][x]==OBJECT_HALLWAY_TILE){
			northSouth = true;
		}

		return northSouth;

	}

	private void removeBogusWalls(){
		//		this clips any bogus 8's that were added in the add corner walls method

		for(int x = 0; x<width*SCALING; x++){
			for(int y = 0; y<height*SCALING; y++){
				if(MAP[y][x]==OBJECT_WALL_TILE){
					if(y>0 & (y+1)<height*SCALING){
						if(MAP[y-1][x]==0 & MAP[y+1][x]==0){
							MAP[y][x]=0;
						}
					}

					if(x>0 & (x+1)<width*SCALING){
						if(MAP[y][x+1]==0 & MAP[y][x-1]==0){
							MAP[y][x]=0;
						}
					}
				}

			}
		}

	}

	//////////////////////////////////////////////////////	//////
	//	
	//	  DIAGNOSTIC / TESTING / PRINTING
	//	
	//	//////////////////////////////////////////////////////



	@SuppressWarnings("unused")
	private void printMap(int[][] map){

		int height = map.length;
		int width = map[0].length;

		for (int k=0; k<height;k++){
			for (int j=0; j<width;j++){

				System.out.format(" %2d ",map[k][j]);
			}
			System.out.println();
		}
	}

	//
	//	private void createMap(int height, int width){
	//		map = new int[height][width];
	//
	//		for(int i=0;i<width;i++){
	//			for(int j=0;j<height;j++){
	//				map[j][i]=0;
	//			}			
	//		}
	//
	//
	//	}

	//	private void testOpenBacktrack(){
	//		height = 4;
	//		width = 3;
	//		createMap(height,width);
	//
	//		map[2][1]=2;
	//		map[1][1]=2;
	//		map[0][1]=2;
	//		printMap(map);
	//		System.out.println(testOpen(1,1,1));
	//
	//	}

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



