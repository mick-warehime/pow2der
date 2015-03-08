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
	
	
	private Path aStarPath(){
		
		// create the astar data
		LevelMap map = new LevelMap(level.getMap());
		AStarPathFinder astar = new AStarPathFinder(map,searchDistance,true);
		
		// position of enemy in tiles
		int sx = (int) Math.floor(self.getX()/map.getWidthInTiles());
		int sy = (int) Math.floor((int) self.getY()/map.getHeightInTiles());
		
		// position of the player in tiles
		int tx = (int) Math.floor((int) player.getX()/ map.getWidthInTiles());
		int ty = (int) Math.floor((int) player.getY()/ map.getHeightInTiles());
		
		// try to calculate the astar path from enemy to player
		Path path = astar.findPath(self, sx, sy, tx, ty);
		
		return path;
		
	}
	
	public float distToPlayer(){
		
		//Make a line from centers of player and object
		float x1 = self.getShape().getCenterX();
		float y1 = self.getShape().getCenterY();
		float x2 = player.getShape().getCenterX();
		float y2 = player.getShape().getCenterY();

		return (float) Math.sqrt(Math.pow(x2-x1,2)+Math.pow(y2-y1,2));
		
	}
	

	//Checks if the straight line between two shapes intersects
	// any other collideable shape
	public boolean playerIsVisible() {
		
		

		//Make a line from centers of player and object
		float x1 = self.getShape().getCenterX();
		float y1 = self.getShape().getCenterY();
		float x2 = player.getShape().getCenterX();
		float y2 = player.getShape().getCenterY();

		Line line = new Line(x1, y1, x2, y2);

		//Also check the basic game tiles
		ArrayList<Shape> walls = level.getWalls();
		for(Shape wall : walls){
			if(line.intersects(wall)){
				return false;
			}
		}
		ArrayList<Shape> doors = level.getClosedDoors();
		for(Shape door : doors){
			if(line.intersects(door)){
				return false;
			}
		}
		
		
		
		return true;

	}
	
	// get direction to player
	public float[] directionToPlayer() {
		
		ArrayList<Shape> walls = level.getWalls();

		//Make a line from centers of player and object
		float x1 = self.getShape().getCenterX();
		float y1 = self.getShape().getCenterY();
		float x2 = player.getShape().getCenterX();
		float y2 = player.getShape().getCenterY();
		
		float xDir = (x2-x1);
		float yDir = (y2-y1);
		
		float length = (float) Math.sqrt((double) Math.pow(xDir,2)+Math.pow(yDir,2));
		
		xDir = xDir/length;
		yDir = yDir/length;
		
		double tol = 0.001;
		if(Math.abs((double) xDir)<tol){
			xDir = 0;
		}
		
		if(Math.abs((double) yDir)<tol){
			yDir = 0;
		}
		
		
		return new float[] {xDir/length, yDir/length};

	}	


	



}
