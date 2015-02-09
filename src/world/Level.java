package world;

import items.ItemBuilder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Shape;

import actors.Actor;
import actors.Enemy;
import gameobjects.BasicObject;
import gameobjects.Broadcaster;


// TODO


public class Level {





	private int startX;
	private int startY;

	private ArrayList<Actor> actors;
	private ArrayList<Broadcaster> broadcasters;
	private ArrayList<BasicObject> basicObjects;
	private ArrayList<Shape> blocks;

	public Level(ItemBuilder itemBuilder, int levelWidth, int levelHeight) throws SlickException {

		
		//  load a random map and populate the items 
		addObjects(new LevelBuilder(levelWidth,levelHeight), itemBuilder);

	};


	private void addObjects(LevelBuilder levelBuilder, ItemBuilder itemBuilder) throws SlickException {
		List<Integer> objectTypes = levelBuilder.getObjectTypes();
		List<Shape> objectShapes = levelBuilder.getObjectShapes();

		this.actors = new ArrayList<Actor>(); 
		this.broadcasters = new ArrayList<Broadcaster>(); 
		this.basicObjects = new ArrayList<BasicObject>();
		this.blocks = new ArrayList<Shape>();

		for(int i=0; i<objectTypes.size(); i++){
			Integer type = objectTypes.get(i);
			Shape shape = objectShapes.get(i);
			if(type == LevelBuilder.OBJECT_BLOCK){
				blocks.add(shape);				
			}else if(type == LevelBuilder.OBJECT_ITEM){
				basicObjects.add(itemBuilder.newItem(shape));
			}else if(type == LevelBuilder.START_PT){
				startX = (int) shape.getX();
				startY = (int) shape.getY();
			}

		}


	}

	

	private void removeFromList(Object obj, ArrayList<?> list){
		if (list.contains(obj)){
			list.remove(obj);
		}
	}

	public void update() throws SlickException{

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

	}



	public void render(Graphics g, int offsetX, int offsetY){		

		for (BasicObject obj : basicObjects){
			//			System.out.println(obj);	
			obj.render(g, offsetX,offsetY);

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
	
}


