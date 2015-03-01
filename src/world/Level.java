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
import gameobjects.Removeable;



/* Top level management of all game objects. */

public class Level {


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
	private ArrayList<Shape> halls;
	private ArrayList<Updater> updaters;

	

	public Level(ItemBuilder itemBuilder, int width, int height) throws SlickException {
		
		
		this.actors = new ArrayList<Actor>(); 
		this.broadcasters = new ArrayList<Broadcaster>(); 
		this.basicObjects = new ArrayList<BasicObject>();
		
		this.width = width;
		this.height = height;
		
		buildNewLevel(itemBuilder);
		
	};
	
	private void buildNewLevel(ItemBuilder itemBuilder) throws SlickException{
		
		this.actors = new ArrayList<Actor>(); 
		this.broadcasters = new ArrayList<Broadcaster>(); 
		this.basicObjects = new ArrayList<BasicObject>();
		this.updaters = new ArrayList<Updater>();
		
		// build a new Level
		LevelBuilder levelBuilder = new LevelBuilder(width,height);
		
		// store the shapes for doors/walls/floors
		walls = levelBuilder.getWalls();
		doors = levelBuilder.getDoors();
		floors = levelBuilder.getFloors();
		halls = levelBuilder.getHalls();

		// tri
		ArrayList<int[]> itemLocations = levelBuilder.generateRandomItemLocations(0.5,3);
		
		for(int[] itemLoc : levelBuilder.generateRandomItemLocations(0.75,3)){
			addObject(itemBuilder.newItem(itemLoc[0],itemLoc[1]));

		}
		
		// poop out the starting position
		int[] startPosition = levelBuilder.getStartingPosition();
		startX = startPosition[0];
		startY = startPosition[1];
		
		addObject(new Enemy(startX + 50, startY));
	}
	
	

	
	public void removeFromAllLists(Object obj){
		removeFromList(obj,actors);
		removeFromList(obj,basicObjects);
		removeFromList(obj,broadcasters);
		removeFromList(obj,walls);
		removeFromList(obj,updaters);
	}
	
	private void removeFromList(Object obj, ArrayList<?> list){
		if (list.contains(obj)){
			list.remove(obj);
		}
	}

	protected void update() throws SlickException{

		for (Iterator<Updater> iterator = updaters.iterator(); iterator.hasNext();) {
			Updater updater = iterator.next();

			updater.update();
			if (updater instanceof Removeable){
				if (((Removeable)updater).shouldRemove()) {
					// Remove the current element from the iterator and the list.
					iterator.remove();
					removeFromAllLists(updater);
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
	public ArrayList<Shape> getFloors(){
		return floors;
	}
	public ArrayList<Shape> getHalls(){
		return halls;
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
		if (obj instanceof Updater){
			updaters.add((Updater) obj);
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


