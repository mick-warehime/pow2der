package knowledge;

import java.util.ArrayList;

import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Shape;

import pathfinding.AStarPathFinder;
import pathfinding.LevelMap;
import pathfinding.Path;
import world.Level;
import actors.Enemy;
import actors.Player;
import actors.Status;

public class Knowledge {

	private Player player;
	private Level level;
	private Enemy self;
	private int searchDistance;
	private float agroDistance;

	public Knowledge(Enemy self, Player player, Level level){

		this.player = player;
		this.level = level;
		this.self = self;
		
		searchDistance = 15;
							
	}
	
	
	public Path aStarPath(){
		
		// create the astar data
		LevelMap map = new LevelMap(level.getMap());
		AStarPathFinder astar = new AStarPathFinder(map,searchDistance,true);
		
		// position of enemy in tiles
		int sx = (int) Math.floor(self.getCenterX()/map.getWidthInTiles());
		int sy = (int) Math.floor((int) self.getCenterY()/map.getHeightInTiles());
		
		// position of the player in tiles
		int tx = (int) Math.floor((int) player.getCenterX()/ map.getWidthInTiles());
		int ty = (int) Math.floor((int) player.getCenterY()/ map.getHeightInTiles());
		
		// try to calculate the astar path from enemy to player
		Path path = astar.findPath(self, sx, sy, tx, ty);
		
		return path;
		
	}
	
	public float distToPlayer(){
		
		//Make a line from centers of player and object
		float x1 = self.getCenterX();
		float y1 = self.getCenterY();
		float x2 = player.getCenterX();
		float y2 = player.getCenterY();

		return (float) Math.sqrt(Math.pow(x2-x1,2)+Math.pow(y2-y1,2));
		
	}
	

	//Checks if the straight line between two shapes intersects
	// any other collideable shape
	public boolean playerIsVisible() {
		
		

		//Make a line from centers of player and object
		float x1 = self.getCenterX();
		float y1 = self.getCenterY();
		float x2 = player.getCenterX();
		float y2 = player.getCenterY();

		Line line = new Line(x1, y1, x2, y2);

		//Also check the basic game tiles
		ArrayList<Shape> walls = new ArrayList<Shape>();
		for(Shape wall : walls){
			if(line.intersects(wall)){
				return false;
			}
		}
		ArrayList<Shape> doors = new ArrayList<Shape>();
		for(Shape door : doors){
			if(line.intersects(door)){
				return false;
			}
		}
			
		return true;
	}
	
	 
	
	// get direction to player
	public float[] directionToPlayer() {
	
		//Make a line from centers of player and object
		float x1 = self.getCenterX();
		float y1 = self.getCenterY();
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
		
		System.out.println("X: "+xDir+" "+", Y: "+yDir);
		return new float[] {(float) xDir, (float) yDir};

		
	}
	
	


}
