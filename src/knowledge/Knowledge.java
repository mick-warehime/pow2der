package knowledge;

import org.newdawn.slick.geom.Line;

import pathfinding.AStarPathFinder;
import pathfinding.LevelMap;
import pathfinding.Path;
import world.World;
import actors.Enemy;
import actors.Player;
import actors.Status;
import collisions.PhysicalCollisions;


public class Knowledge {

	private Player player;
	
	private int searchDistance;
	private Status status;

	public Knowledge( Player player, Status status){

		this.player = player;
		
		this.status = status;
		
		searchDistance = 100;
		
	}


	private int[] localMapDimensions(){
		
		int xTopLeft =  (int) Math.min(status.getCenterX(), player.getCenterX());
		int yTopLeft =  (int) Math.min(status.getCenterY(), player.getCenterY());
		
		int dx =  (int) Math.abs(status.getCenterX()-player.getCenterX());
		int dy =  (int) Math.abs(status.getCenterY()-player.getCenterY());
		
		xTopLeft -= (dx/2 + 50);
		yTopLeft -= (dy/2 + 50);
		
		int widthInTiles = (2*dx+ 100)/World.TILE_WIDTH;
		int heightInTiles = (2*dy+100)/World.TILE_HEIGHT;
		
		return new int[] {xTopLeft,yTopLeft,widthInTiles,heightInTiles};
		
	}
	
	private int [][] getLocalMap(){
		
		
		
		return status.getPhysicalCollisions().generateLocalMap(localMapDimensions(), World.TILE_WIDTH, World.TILE_HEIGHT);
	}
	
	public Path aStarPath(){
		
		
		
		
		// create the astar data
		LevelMap map = new LevelMap(getLocalMap());
		AStarPathFinder astar = new AStarPathFinder(map,searchDistance,false);

		int [] mapDims = localMapDimensions();
		
		
		// position of enemy in tiles
		int sx = (int) ((status.getCenterX()-mapDims[0])/World.TILE_WIDTH);
		int sy = (int) ((status.getCenterY()-mapDims[1])/World.TILE_HEIGHT);

		// position of the player in tiles
		int tx = (int) ((player.getCenterX()-mapDims[0])/ World.TILE_WIDTH);
		int ty = (int) ((player.getCenterY()-mapDims[1])/ World.TILE_HEIGHT);

		
		
		
		// try to calculate the astar path from enemy to player
		Path path = astar.findPath(null, sx, sy, tx, ty);
 

		return path;

	}

	public float distToPlayer(){

		//Make a line from centers of player and object
		float x1 = status.getCenterX();
		float y1 = status.getCenterY();
		float x2 = player.getCenterX();
		float y2 = player.getCenterY();

		return (float) Math.sqrt(Math.pow(x2-x1,2)+Math.pow(y2-y1,2));

	}


	//Checks if the straight line between two shapes intersects
	// any other collideable shape
	public boolean playerIsVisible() {



		//Make a line from centers of player and object
		float x1 = status.getX();
		float y1 = status.getY();
		float x2 = player.getX();
		float y2 = player.getY();

		Line line = new Line(x1, y1, x2, y2);

		
		PhysicalCollisions physicalCollisions = status.getPhysicalCollisions();
		if (physicalCollisions.isCollidedWithSolids(line)){ return false;}
		

			
		return true;
	}



	// get direction to player
	public float[] directionToPlayer() {

		//Make a line from centers of player and object
		float x1 = status.getCenterX();
		float y1 = status.getCenterY();
		float x2 = player.getCenterX();
		float y2 = player.getCenterY();

		double xDir = x2-x1;
		double yDir = y2-y1;

		double length = Math.sqrt((double) Math.pow(xDir,2)+Math.pow(yDir,2));

		xDir = xDir/length;
		yDir = yDir/length;

		return new float[] {(float) xDir, (float) yDir};

	}	


	public float[] aStarDirection(Path path, int index) {

		float x1 = path.getX(index);
		float y1 = path.getY(index);
		float x2 = path.getX(index+1);
		float y2 = path.getY(index+1);

		double xDir = x2-x1;
		double yDir = y2-y1;

		double length = Math.sqrt((double) Math.pow(xDir,2)+Math.pow(yDir,2));

		xDir = xDir/length;
		yDir = yDir/length;

//		System.out.println("X: "+xDir+" "+", Y: "+yDir);
		return new float[] {(float) xDir, (float) yDir};


	}




}
