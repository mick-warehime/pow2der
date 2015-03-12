package collisions;


import java.util.ArrayList;

import org.newdawn.slick.command.Command;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import world.Level;
import actors.Actor;
import gameobjects.BasicObject;
import gameobjects.Interactive;
import gameobjects.Broadcaster;

public class CollisionHandler  {

	private ArrayList<Actor> actors;
	// Objects that do something on collision
	private ArrayList<Broadcaster> broadcasters;
	private ArrayList<BasicObject> basicObjects;


	

	public CollisionHandler(Level level){
		this.basicObjects = level.getBasicObjects();
		this.actors = level.getActors();
		this.broadcasters = level.getBroadcasters();
	}
	


//	Returns a list of interactive game objects near the rect
	public ArrayList<Interactive> interactiveObjectsNearRect(Rectangle rect){


		ArrayList<Interactive> output = new ArrayList<Interactive>();

		for(BasicObject obj: basicObjects){
			
			if (obj instanceof Interactive){
				
				if (obj.isNear(rect)){
					output.add((Interactive) obj);
				}
			}
		}

		return output;
	}

	

	
	

	


	//Checks a shape for collisions with interactive collideables
	// outputs a list of commands for an actor with the shape to do,
	// and does the interactive's inherent collision command
	// For collisions to be class specific, we pass in a 
	// collidingObjectClass.
	public ArrayList <Command> resolveBroadcasterCollisions(final Shape collidingShape, Class<?> collidingObjectClass ){
		ArrayList<Command> outputCommands = new  ArrayList <Command>();
		
		//Make a slightly bigger rectangle because physics don't 
		// allow you to actually move into another object
		int proximity = 1;
		Rectangle slightlyBiggerRect = new Rectangle(collidingShape.getX()-proximity,collidingShape.getY()-proximity,collidingShape.getWidth()+2*proximity,collidingShape.getHeight()+2*proximity);

		
		for (Broadcaster bcaster : broadcasters){

			if (slightlyBiggerRect.intersects(bcaster.getShape())){
				bcaster.onCollisionDo(collidingObjectClass, collidingShape);
				ArrayList<Command> newCommands = bcaster.onCollisionBroadcast(collidingObjectClass, collidingShape);
				outputCommands.addAll(newCommands);	
//				System.out.println("Class type" + collidingObjectClass + " collided with" + bcaster);
//				System.out.println("received commands: " + newCommands);
			}
		}
		
		
		return outputCommands;
	}






}
