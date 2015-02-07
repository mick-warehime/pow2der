package world;

import items.Item;

import java.util.ArrayList;

import org.newdawn.slick.command.Command;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import actors.Actor;
import actors.Player;
import commands.CommandProvider;
import gameobjects.BasicObject;
import gameobjects.GameObject;
import gameobjects.Interactive;
import gameobjects.Broadcaster;

public class CollisionHandler implements CommandProvider {

	private ArrayList<Shape> blocks;
//	private ArrayList<GameObject> gameObjects;
	private ArrayList<Actor> actors;

	//	private ArrayList<GameObject> gameObjects2;
	private Rectangle playerRect;

	// Objects that do something on collision
	private ArrayList<Broadcaster> interactiveGameObjects;
	private ArrayList<BasicObject> basicObjects;


	public CollisionHandler(ArrayList<Shape> blockedList){
		this.blocks = blockedList;
	}

	
	public CollisionHandler(Level level){
		this.blocks = level.getBlocks();
		this.basicObjects = level.getBasicObjects();
		this.actors = level.getActors();
		this.interactiveGameObjects = level.getBroadcasters();
	}

	public void receiveObjects(ArrayList<Actor> actors, ArrayList<Broadcaster> broadcasters, 
			ArrayList<BasicObject> basicObjects){

		this.basicObjects = basicObjects;
		this.actors = actors;
		this.interactiveGameObjects = broadcasters;	
		
	}

	public void addPlayerRect(Rectangle playerRect){
		this.playerRect = playerRect;		
	}


//	Returns a list of interactive game objects near the player
	public ArrayList<BasicObject> interactiveObjectsNearRect(Rectangle rect){



		ArrayList<BasicObject> output = new ArrayList<BasicObject>();

		for(BasicObject obj: basicObjects){
			
			if (obj instanceof Interactive){
				
				if (obj.isNear(playerRect)){
					output.add(obj);
				}
			}
		}

		return output;
	}

	//Checks for collisions with blocks and game Objects
	public boolean isCollided(Shape shape){	
		boolean answer = false;
		//	check if collided with permanent solid blocks	
		answer = answer || isCollidedWithBlocks(shape);
		// check if collided with solid etherable Objects
		answer = answer || isCollidedWithObjects(shape);

		return answer;
	}

	public boolean isCollidedWithBlocks(Shape shape){
		for(Shape r: blocks ){
			if(shape.intersects(r)){
				return true;
			}	
		}
		return false;
	}

	public boolean isCollidedWithActor(Shape shape){
		for (Actor nme: actors){
			if(nme.canCollide()){
				if(nme.getShape().intersects(shape)){
					return true;
				}
			}
		}

		return playerRect.intersects(shape); 
	}
//
	public boolean isCollidedWithObjects(Shape shape){
		// check if collided with solid etherable Objects
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

	public boolean isCollidedWithPlayer(Shape shape){
		return playerRect.intersects(shape);
	}


	public ArrayList<Command> getCommands(){
		return resolveInteractiveCollisions(playerRect, Player.class);
	}


	//Checks a shape for collisions with interactive collideables
	// outputs a list of commands for an actor with the shape to do,
	// and does the interactive's inherent collision command
	// For collisions to be class specific, we pass in a 
	// collidingObjectClass.
	public ArrayList <Command> resolveInteractiveCollisions(Rectangle rect, Class collidingObjectClass ){
		ArrayList<Command> output = new  ArrayList <Command>();

		//Make a slightly bigger rectangle because physics don't 
		// allow you to actually move into another object
		int proximity = 1;
		Rectangle slightlyBiggerRect = new Rectangle(rect.getX()-proximity,rect.getY()-proximity,rect.getWidth()+2*proximity,rect.getHeight()+2*proximity);

		//
		for (Broadcaster interObj : interactiveGameObjects){

			if (slightlyBiggerRect.intersects(interObj.getShape())){
				interObj.onCollisionDo(collidingObjectClass, rect);
				output.addAll(interObj.onCollisionBroadcast(collidingObjectClass, rect));
			}
		}

		return output;
	}






}
