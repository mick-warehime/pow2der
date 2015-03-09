package commands;

import java.util.ArrayList;

import org.newdawn.slick.command.Command;
import org.newdawn.slick.geom.Shape;

import world.CollisionHandler;

/* Gives commands based on collisions with broadcasters */
public class BroadcasterCommandProvider implements CommandProvider {

	private CollisionHandler collisionHandler;
	private Class<?> ownerClass;
	private Shape ownerShape;

	public BroadcasterCommandProvider(CollisionHandler col, Class<?> ownerClass, Shape ownerShape){
		this.collisionHandler = col;
		this.ownerClass = ownerClass;
		this.ownerShape = ownerShape;
	}
	
	@Override
	public ArrayList<Command> getCommands() {
		
		return collisionHandler.resolveBroadcasterCollisions(ownerShape, ownerClass);
	}

}
