package interfaces;

import org.newdawn.slick.SlickException;

import collisions.ContextualCollisions;
import collisions.PhysicalCollisions;

/*
 * An ingame object that needs to know if its collided with a solid object (wall or basic object)
 * or if it's made a contextual collision
 */

public interface Collider {
	
	public void assignPhysicalCollisions(PhysicalCollisions physicalCollisions) throws SlickException;
	public void assignContextualCollisions(ContextualCollisions contextuals);
}
