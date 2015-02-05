package main;

import items.Item;
import items.ItemBuilder;

import java.util.ArrayList;
import java.util.Iterator;

import level.LevelObjects;

import org.newdawn.slick.*;
import org.newdawn.slick.tiled.TiledMap;

import actors.Actor;
import actors.Enemy;
import gameobjects.BasicObject;
import gameobjects.GameObject;
import gameobjects.Broadcaster;
import gameobjects.Interactive;


// TODO


public class Level {


	private static int tileSize; // size of a single tile in tilemap
	private int width = 640;
	private int height = 480;
	private int mapX = 0;
	private int mapY = 0;
	private int tol = 18; // number of tiles away from edge
	private int tolX;
	private int tolY;
	
	private int mapWidthInTiles;
	private int mapHeightInTiles;
	private TiledMap map;

	private int tileLayerId;
	private CollisionHandler collisionHandler;

	//	private TileData tileData;
	private ArrayList<Actor> actors = new ArrayList<Actor>();
	private ArrayList<Broadcaster> collideables = new ArrayList<Broadcaster>();
	private int[] mousePos;
	private ItemBuilder itemBuilder;
	private ArrayList<Item> items = new ArrayList<Item>();
	private ArrayList<BasicObject> basicObjects = new ArrayList<BasicObject>();


	public Level(int levelNumber, ItemBuilder itemBuilder) throws SlickException {

		// load map
		String fileName= "data/Level" + levelNumber + ".tmx";
		map = new TiledMap(fileName);
		TileData tileData = new TileData(map);

		// use in rendering
		initializeMapProperties();

		//Creates the collisionHandler with just game blocks
		collisionHandler = new CollisionHandler(tileData.getBlocks());
	
//		this.actors = tileData.getActors();
		
		this.itemBuilder = itemBuilder;

		// build a test door
		basicObjects.add(itemBuilder.buildDoor(100,750));
		
		// test item
		basicObjects.add(itemBuilder.newItem(400,800));
		basicObjects.add(itemBuilder.newItem(300,800));
		basicObjects.add(itemBuilder.newItem(350,800));
		
		// LevelObjects blockMatrix = new LevelObjects();
		
		actors.add(new Enemy(400,750,collisionHandler));
		
		
		//Add interactive Collideables
		for (Actor actor:actors){
			if(actor instanceof Broadcaster){
				collideables.add((Broadcaster) actor);
			}
		}

	
		// Give the objects to the collisionHandler
		collisionHandler.receiveObjects(actors, collideables, basicObjects);		
		
	};


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
				removeFromList(nme,collideables);
			}
		}




	}

//	//Add a new object to lists and pass it necessary objects from level
//	private void incorporateNewObject(Object obj){
//
//		if(obj instanceof Actor){
//			actors.add((Actor)obj);
//			((Actor) obj).incorporateCollisionHandler(collisionHandler);
//		}
//		if(obj instanceof Broadcaster){
//			collideables.add((Broadcaster)obj);
//		}
//
//	}

	public void draw(Graphics g,int x, int y){		


		// min/max sets the submatrix of tiles to draw		
		int tXmin = (int) mapX/tileSize;
		int tYmin = (int) mapY/tileSize;

		// dX/dY are the offsets from the submatrix to the actual screen position
		int dX = mapX - tXmin*tileSize;
		int dY = mapY - tYmin*tileSize;

		// allows the player to get within tolX/tolY of the top/side
		if (mapX > (x - tolX) ){mapX = x-tolX;}
		if (mapX < (x+tolX-width)){mapX = x+tolX-width;}
		if (mapY > (y - tolY) ){mapY = y-tolY;}
		if (mapY < (y+tolY-height)){mapY = y+tolY-height;}

		// see if we are close to the edge of a map inwhich case dont let mapx<0 or mapx>size of map in pixels
		mapCheck();


		// map.render(-mapX,-mapY);
		map.render(-dX,-dY,tXmin,tYmin,mapWidthInTiles,mapHeightInTiles+1,tileLayerId,false);

		for (Actor nme: actors){
			nme.render(g, mapX,mapY);
		}

		for (BasicObject obj: basicObjects){
			
			obj.render(g,mapX,mapY);

		}

	}

	private void mapCheck(){
		if(mapX<0){
			mapX = 0;
			tolX = tileSize;			
		}else if(mapX>map.getWidth()*tileSize-width){
			mapX = map.getWidth()*tileSize-width;
			tolX = tileSize;
		}else{
			tolX = tol*tileSize;
		}


		if(mapY<0){
			mapY = 0;
			tolY = tileSize;			
		}
		else if(mapY>map.getHeight()*tileSize-height){
			mapY = map.getHeight()*tileSize-height;
			tolY = tileSize;
		}
		else{
			tolY = tol*tileSize;
		}
	}

	private void initializeMapProperties(){
		// used for drawing (allows the dude to be outside the center of the screen)
		tileSize = map.getTileHeight();
		tolX = tol*tileSize;
		tolY = tol*tileSize;
		mapWidthInTiles = map.getWidth();
		mapHeightInTiles = map.getHeight();
	}

	public int getMapX(){return mapX;}
	public int getMapY(){return mapY;}


	public CollisionHandler getCollisionHandler(){
		return collisionHandler;
	}


	public void setMousePosition(int[] mousePos) {
		this.mousePos = mousePos;




	}

}
