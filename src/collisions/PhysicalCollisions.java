package collisions;


import gameobjects.BasicObject;
import world.Sector;
import world.SectorMap;

import java.util.ArrayList;

import org.newdawn.slick.geom.Shape;

/*
 * Provides information about physically collideable 
 * Objects in a level;
 * 
 */

public class PhysicalCollisions  {

	private SectorMap sectorMap;
	private ArrayList<Shape> blocks;




	public PhysicalCollisions( SectorMap sectorMap, ArrayList<Shape> blocks){
		this.sectorMap = sectorMap;
		this.blocks = blocks;
	}



	//Checks for collisions with blocks and game Objects
	public boolean isCollidedWithSolids(Shape shape){	
		boolean answer = false;
		//	check if collided with permanent solid blocks	
		answer = answer || isCollidedWithBlocks(shape);
		// check if collided with solid etherable Objects
		answer = answer || isCollidedWithBasicObjects(shape);

		return answer;
	}

	private boolean isCollidedWithBlocks(Shape shape){
		
		
		
		
		for(Shape r: blocks ){
			if(shape.intersects(r)){
				return true;
			}	
		}
		return false;
	}

	private boolean isCollidedWithBasicObjects(Shape shape){

		Sector sector = sectorMap.getSectorWithPosition((int) shape.getX(), (int) shape.getY());
		
		
		for(BasicObject obj: sector.getBasicObjects()){
			// don't check with its own shape and dont check with objects that are currently being held
			assert (obj.getShape() != null) : "Error! Object" + obj + " has no shape!";

			if(obj.getShape() != shape){
				if(obj.canCollide()){
					if(shape.intersects(obj.getShape())){
						return true;
					}
				}
			}
		}
		return false;
	}






}
