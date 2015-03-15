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

	

	
	

	


	






}
