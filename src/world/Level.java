package world;

import items.Item;
import items.ItemBuilder;

import java.util.ArrayList;
import java.util.Iterator;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Shape;

import actors.Actor;
import actors.Enemy;
import actors.Player;
import gameobjects.BasicObject;
import gameobjects.Broadcaster;
import gameobjects.Door;
import gameobjects.Removeable;



/* Top level management of all game objects. */

public class Level {


	private int startX;
	private int startY;
	
	private int height;
	private int width;

	private int[][] map;
	
	private ArrayList<Actor> actors;
	
	private ArrayList<Broadcaster> broadcasters;
	private ArrayList<BasicObject> basicObjects;
	
	private ArrayList<Shape> walls;
	private ArrayList<Shape> doors;
	private ArrayList<Shape> floors;
	private ArrayList<Shape> halls;
	
	private ArrayList<Updater> updaters;
	private ArrayList<ObjectCreator> creators;

	

	public Level(ItemBuilder itemBuilder, int width, int height, Player player) throws SlickException {
		
		
		this.width = width;
		this.height = height;
		
		buildNewLevel(itemBuilder, player);
		
	};
	
	private void buildNewLevel(ItemBuilder itemBuilder, Player player) throws SlickException{
		
		this.actors = new ArrayList<Actor>(); 
		this.broadcasters = new ArrayList<Broadcaster>(); 
		this.basicObjects = new ArrayList<BasicObject>();
		this.updaters = new ArrayList<Updater>();
		this.creators = new ArrayList<ObjectCreator>();
		
		// build a new Level
		LevelBuilder levelBuilder = new LevelBuilder(width,height);
		
		// store the shapes for doors/walls/floors
		walls = levelBuilder.getWalls();
		doors = levelBuilder.getDoors();
		floors = levelBuilder.getFloors();
		halls = levelBuilder.getHalls();

		map = levelBuilder.getMap();
		
		

		// poop out the starting position
		int[] startPosition = levelBuilder.getStartingPosition();
		startX = startPosition[0];
		startY = startPosition[1];
		
		// build items using the levelbuilder to get the random locations
		for(int[] itemLoc : levelBuilder.randomRoomLocations(0.75,3)){
			addObject(itemBuilder.newItem(itemLoc[0],itemLoc[1]));
		}
		
		for(int[] enemyLoc : levelBuilder.randomRoomLocations(2,1)){
			addObject(new Enemy(enemyLoc[0],enemyLoc[1],this,player));
		}
		
		
		for(Shape doorShape : doors){
			basicObjects.add(new Door(doorShape,  actors));
		}
		
				
	}
	
	

	
	public void removeFromAllLists(Object obj){
		removeFromList(obj,actors);
		removeFromList(obj,basicObjects);
		removeFromList(obj,broadcasters);
		removeFromList(obj,walls);
		removeFromList(obj,updaters);
		removeFromList(obj,creators);
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
		
		for (ObjectCreator creator : creators){
			if (creator.hasObjects()){
				for (Object obj: creator.popObjects()){
					addObject(obj);
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
	
	public ArrayList<Shape> getClosedDoors(){
		ArrayList<Shape> closedDoors = new ArrayList<Shape>();
		for(BasicObject obj: basicObjects){
			if(obj instanceof Door)
				if(!((Door) obj).isOpen()){
					closedDoors.add(obj.getShape());	
				}
			
		}
		return closedDoors;
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
	
	public int[][] getMap(){
		return map;
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
		if (obj instanceof ObjectCreator){
			creators.add((ObjectCreator) obj);
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


