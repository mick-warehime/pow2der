package interfaces;

import collisions.PhysicalCollisionDetector;

/*
 * An ingame object that needs to know if its collided with a solid object (wall or basic object)
 * 
 */

public interface CollidesWithSolids {
	
	public void assignCollisionDetector(PhysicalCollisionDetector detector);

}
