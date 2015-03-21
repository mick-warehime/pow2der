package interfaces;

import org.newdawn.slick.SlickException;

import collisions.PhysicalCollisions;

/*
 * An ingame object that needs to know if its collided with a solid object (wall or basic object)
 * 
 */

public interface CollidesWithSolids {
	
	public void assignCollisionDetector(PhysicalCollisions detector) throws SlickException;

}
