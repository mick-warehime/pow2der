package world;

import gameobjects.BasicObject;
import interfaces.Broadcaster;
import interfaces.Collider;
import interfaces.ObjectCreator;
import interfaces.Removeable;
import interfaces.Updater;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import actors.Actor;



/* Top level management of game objects in a sector of the level */

public class Sector {


	
	private Rectangle domain;

	private ArrayList<Shape> walls;
	private ArrayList<Actor> actors;
	private ArrayList<Broadcaster> broadcasters;
	private ArrayList<BasicObject> basicObjects;
	private ArrayList<Updater> updaters;
	private ArrayList<ObjectCreator> creators;
	private ArrayList<Collider> colliders;
	
	private ArrayList<Collider> newColliders;

	
	public Sector( int xMin, int yMin, int width, int height) throws SlickException {


		
		this.domain = new Rectangle(xMin,yMin,width,height);

		this.walls = new ArrayList<Shape>();
		this.actors = new ArrayList<Actor>(); 
		this.broadcasters = new ArrayList<Broadcaster>(); 
		this.basicObjects = new ArrayList<BasicObject>();
		this.updaters = new ArrayList<Updater>();
		this.creators = new ArrayList<ObjectCreator>();
		this.colliders = new ArrayList<Collider>();
		this.newColliders = new ArrayList<Collider>();
		
		

		
		


	};

	




	public void removeFromAllLists(Object obj){
		removeFromList(obj,actors);
		removeFromList(obj,basicObjects);
		removeFromList(obj,broadcasters);
		removeFromList(obj,walls);
		removeFromList(obj,updaters);
		removeFromList(obj,creators);
		removeFromList(obj,colliders);
	}

	private void removeFromList(Object obj, ArrayList<?> list){
		if (list.contains(obj)){
			list.remove(obj);
		}
	}

	protected void update() throws SlickException, IOException{

		for (Updater updater : updaters){
			updater.update();
		}

		ArrayList<Object> objsToAdd = new ArrayList<Object>();
		for (ObjectCreator creator : creators) {

			if (creator.hasObjects()){
				for (Object obj: creator.popObjects()){
					objsToAdd.add(obj);
				}
			}
		}		

		for(Object obj : objsToAdd){
			addObject(obj);
		}

		checkAndRemoveRemovables();

		
	}

	

	

	
	private void checkAndRemoveRemovables() {

		HashSet<Object> toRemove = new HashSet<Object>();

		for (Actor actor: actors){ 
			if (actor.shouldRemove()){
				toRemove.add(actor);
			}
		}

		for (Broadcaster bcaster : broadcasters){
			if (bcaster instanceof Removeable){
				if (((Removeable) bcaster).shouldRemove()){
					toRemove.add(bcaster);
				}
			}
		}

		for (BasicObject basic : basicObjects){
			if (basic instanceof Removeable){
				if (((Removeable) basic).shouldRemove()){
					toRemove.add(basic);
				}
			}
		}

		for (Updater obj : updaters){
			if (obj instanceof Removeable){
				if (((Removeable) obj).shouldRemove()){
					toRemove.add(obj);
				}
			}
		}

		for (ObjectCreator obj: creators){
			if (obj instanceof Removeable){
				if (((Removeable) obj).shouldRemove()){
					toRemove.add(obj);
				}
			}
		}

		for (Object obj : toRemove){
			removeFromAllLists(obj);
			((Removeable)obj).onRemoveDo();
		}



	}

	public void render(Graphics g, int offsetX, int offsetY){		

		for (BasicObject obj : basicObjects){
			obj.render(g, offsetX, offsetY);
		}

		for (Actor actor : actors){	
			actor.render(g, offsetX, offsetY);
		}

	}


	public void addObject(Object obj) throws SlickException {
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
		if (obj instanceof Collider){
			this.colliders.add((Collider) obj); //Collision detector no longer assigned!
			this.newColliders.add((Collider) obj);
		}

	}






	public  ArrayList<BasicObject> getBasicObjects() {
		// TODO Auto-generated method stub
		return this.basicObjects;
	}






	public ArrayList<Collider> popNewColliders() {
		@SuppressWarnings("unchecked")
		ArrayList<Collider> output = (ArrayList<Collider>) newColliders.clone();
		newColliders.clear();
		return output;
	}






	public ArrayList<Broadcaster> getBroadcasters() {
		
		return this.broadcasters;
	}






	



	
	
}

