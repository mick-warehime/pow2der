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

	private ArrayList<Actor> actors;
	private ArrayList<Broadcaster> broadcasters;
	private ArrayList<BasicObject> basicObjects;
	private ArrayList<Shape> blocks;

	public Level(ItemBuilder itemBuilder, int levelWidth, int levelHeight) throws SlickException {

		
		//  load a random map and populate the items 
		initializeObjects(new LevelBuilder(levelWidth,levelHeight), itemBuilder);

	};

	

	private void initializeObjects(LevelBuilder levelBuilder, ItemBuilder itemBuilder) throws SlickException {
		List<Integer> objectTypes = levelBuilder.getObjectTypes();
		List<Integer[]> objectPositions = levelBuilder.getObjectPositions();

		this.actors = new ArrayList<Actor>(); 
		this.broadcasters = new ArrayList<Broadcaster>(); 
		this.basicObjects = new ArrayList<BasicObject>();
		this.blocks = new ArrayList<Shape>();

		for(int i=0; i<objectTypes.size(); i++){
			Integer type = objectTypes.get(i);
			Integer[] pos = objectPositions.get(i);
			
			if(type == LevelBuilder.OBJECT_BLOCK){
				blocks.add(new Rectangle(pos[0],pos[1], World.TILE_WIDTH, World.TILE_HEIGHT));				
			}else if(type == LevelBuilder.OBJECT_ITEM){
				basicObjects.add(itemBuilder.newItem(pos[0],pos[1]));
			}else if(type == LevelBuilder.START_PT){
				startX = pos[0];
				startY = pos[1];
			}else if (type == LevelBuilder.OBJECT_ENEMY){
				Enemy nme = new Enemy(pos[0],pos[1]);
				addObject(nme);
				
			}
			

		}


	}

	
	private void removeFromAllLists(Object obj){
		removeFromList(obj,actors);
		removeFromList(obj,basicObjects);
		removeFromList(obj,broadcasters);
		removeFromList(obj,blocks);
		
	}
	
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
			if (nme.shouldRemove()) {
				// Remove the current element from the iterator and the list.
				iterator.remove();
				removeFromAllLists(nme);
			}
		}
		
		//Remove items that are not on the ground
		for (Iterator<BasicObject> iterator = basicObjects.iterator(); iterator.hasNext();){
			BasicObject obj = iterator.next();
			
			if (obj instanceof Removeable){
				if (((Removeable)obj).shouldRemove()){
					iterator.remove();
					removeFromAllLists(obj);
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
	
	
	public ArrayList<Shape> getBlocks(){
		return blocks;
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


