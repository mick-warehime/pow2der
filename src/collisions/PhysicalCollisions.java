package collisions;


import gameobjects.BasicObject;

import org.newdawn.slick.geom.Shape;

import world.Sector;
import world.SectorMap;

/*
 * Provides information about physically collideable 
 * Objects in a level;
 * 
 */

public class PhysicalCollisions  {

	private SectorMap sectorMap;

	public PhysicalCollisions( SectorMap sectorMap){
		this.sectorMap = sectorMap;
	}



	//Checks for collisions with blocks and game Objects
	public boolean isCollidedWithSolids(Shape shape){	
		
		return isCollidedWithBasicObjects(shape);
	}

	

	private boolean isCollidedWithBasicObjects(Shape shape){

		for (Sector sector : sectorMap.getSectorsNear(shape)){


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
		}
		
		return false;
		
	}






}
