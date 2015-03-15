package collisions;


import java.util.ArrayList;

import org.newdawn.slick.geom.Shape;

import world.Level;
import gameobjects.BasicObject;

/*
 * Given a shape, says if that shape is collided with any other 
 * physical shapes (blocks or basicObects that can collide)
 * 
 */

public class PhysicalCollisionDetector  {

	private ArrayList<Shape> blocks;
	
	private ArrayList<BasicObject> basicObjects;


	

	public PhysicalCollisionDetector(Level level){
		this.blocks = level.getWalls();
		this.basicObjects = level.getBasicObjects();
		
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
		
		for(BasicObject obj: basicObjects){
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
