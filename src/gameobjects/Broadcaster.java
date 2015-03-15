package gameobjects;

import java.util.ArrayList;

import org.newdawn.slick.command.Command;
import org.newdawn.slick.geom.Shape;


//An object that does something when an actor collides with it
//Gives actors commands and runs an internal function on collision
// The functions run depend on the class of object colliding
public interface Broadcaster {

	public void onCollisionDo(Class<?> collidingObjectClass, Shape collidingObjectShape);
	public ArrayList<Command> onCollisionBroadcast(Class<?> collidingObjectClass, Shape collidingObjectShape);
	public Shape getInteractionRange();
	
}
