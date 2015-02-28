package world;

import items.Item;
import items.ItemBuilder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import actors.Actor;
import actors.Enemy;
import gameobjects.BasicObject;
import gameobjects.Broadcaster;



/* Top level management of all game objects. */

public class NewLevel {


	private int startX;
	private int startY;
	
	private int height;
	private int width;

	private ArrayList<Actor> actors;
	private ArrayList<Broadcaster> broadcasters;
	private ArrayList<BasicObject> basicObjects;
	private ArrayList<Shape> walls;
	private ArrayList<Shape> doors;
	private ArrayList<Shape> floors;
	

	public NewLevel(ItemBuilder itemBuilder, int width, int height) throws SlickException {
		
		
		// build a new Level
		NewLevelBuilder levelBuilder = new NewLevelBuilder(width,height);
		
		// store the shapes for doors/walls/floors
		walls = levelBuilder.getWalls();
		doors = levelBuilder.getDoors();
		floors = levelBuilder.getFloors();
		
		// poop out the starting position
		int[] startPosition = levelBuilder.getStartingPosition();
		startX = startPosition[0];
		startY = startPosition[1];
		
		this.actors = new ArrayList<Actor>(); 
		this.broadcasters = new ArrayList<Broadcaster>(); 
		this.basicObjects = new ArrayList<BasicObject>();
		
	};


	private void removeFromList(Object obj, ArrayList<?> list){
		if (list.contains(obj)){
			list.remove(obj);
		}
	}

	protected void update() throws SlickException{

		//Update actors and remove dead ones
		for (Iterator<Actor> iterator = actors.iterator(); iterator.hasNext();) {
			Actor nme = iterator.next();

			nme.update();
			if (nme.isDying()) {
				// Remove the current element from the iterator and the list.
				iterator.remove();
				removeFromList(nme,broadcasters);
			}
		}

		//Remove items that are not on the ground
		for (Iterator<BasicObject> iterator = basicObjects.iterator(); iterator.hasNext();){
			BasicObject obj = iterator.next();

			if (obj instanceof Item){
				if (!((Item)obj).isOnGround()){
					iterator.remove();
					removeFromList(obj,broadcasters);
				}

			}

		}

	}



	public void render(Graphics g, int offsetX, int offsetY){		

		for (BasicObject obj : basicObjects){	
			obj.render(g, offsetX,offsetY);

		}

		for (Actor actor : actors){	
			actor.render(g, offsetX,offsetY);

		}

	}


	public ArrayList<Shape> getWalls(){
		return walls;
	}
	public ArrayList<Actor> getActors(){
		return actors;
	}
	public ArrayList<Broadcaster> getBroadcasters(){
		return broadcasters;	
	}
	public ArrayList<BasicObject> getBasicObjects(){
		return basicObjects;
	}
	public int getStartX(){
		return startX;
	}
	public int getStartY(){
		return startY;
	}
	public int getHeight(){
		return height;
	}
	public int getWidth(){
		return width;
	}
	

	public void addObject(Object obj) {
		if (obj instanceof Actor){
			actors.add((Actor) obj);
		}
		if (obj instanceof BasicObject){
			basicObjects.add((BasicObject) obj);
		}
		if (obj instanceof Broadcaster){
			broadcasters.add((Broadcaster) obj);
		}

	}


	public void assignToItems(CurrentLevelData currentLevelData) {
		for (BasicObject obj : basicObjects){
			if (obj instanceof Item){
				((Item)obj).setCurrentLevelData(currentLevelData);
			}
		}

	}

}


