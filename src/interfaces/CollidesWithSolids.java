package interfaces;

import org.newdawn.slick.SlickException;

import collisions.PhysicalCollisions;
import collisions.PhysicalCollisionsNew;

/*
 * An ingame object that needs to know if its collided with a solid object (wall or basic object)
 * 
 */

public interface CollidesWithSolids {
	
	public void assignCollisionDetector(PhysicalCollisionsNew physicalCollisionsNew) throws SlickException;

}
