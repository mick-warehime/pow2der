package collisions;


import gameobjects.BasicObject;

import java.util.ArrayList;

import org.newdawn.slick.geom.Shape;

/*
 * Given a shape, says if that shape is collided with any other 
 * physical shapes (walls or basicObects that can collide)
 * 
 */

public class PhysicalCollisionDetector  {

	private ArrayList<Shape> walls;
	
	private ArrayList<BasicObject> basicObjects;


	public PhysicalCollisionDetector( ArrayList<Shape> walls,  ArrayList<BasicObject> basicObjects){
		this.walls = walls;
		this.basicObjects = basicObjects;
	}
	


//Checks for collisions with walls and game Objects
	public boolean isCollidedWithSolids(Shape shape){	
		boolean answer = false;
		//	check if collided with permanent solid walls	
		answer = answer || isCollidedWithWalls(shape);
		// check if collided with solid etherable Objects
		answer = answer || isCollidedWithBasicObjects(shape);

		return answer;
	}

	private boolean isCollidedWithWalls(Shape shape){
		
		for(Shape r: walls ){
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
