package world;

import items.Item;
import items.ItemBuilder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.tiled.TiledMap;

import actors.Actor;
import actors.Enemy;
import gameobjects.BasicObject;
import gameobjects.GameObject;
import gameobjects.Broadcaster;
import gameobjects.Interactive;
import graphics.LevelGraphics;


// TODO


public class Level {




	
	private int startX;
	private int startY;
	
	private ArrayList<Actor> actors;
	private ArrayList<Broadcaster> broadcasters;
	private ArrayList<BasicObject> basicObjects;
	private ArrayList<Shape> blocks;

	private LevelGraphics levelGraphics;
	private int levelHeight;
	private int levelWidth;

	private int renderOffsetX = 0;
	private int renderOffsetY = 0;
	
	private int bufferDist = 15; // number of tiles away from edge
	private int bufferDistX; // number of tiles away from edge
	private int bufferDistY; // number of tiles away from edge
	
	private int tileSize;
	private int screenWidth;
	private int screenHeight;
	
	public Level(ItemBuilder itemBuilder, int levelWidth, int levelHeight) throws SlickException {
		
		this.screenWidth = main.Game.WIDTH;
		this.screenHeight = main.Game.HEIGHT;
		
		this.levelWidth = levelWidth;
		this.levelHeight = levelHeight;
		
		this.tileSize = World.TILE_HEIGHT;
		
		addObjects(new LevelBuilder(levelWidth,levelHeight), itemBuilder);

		levelGraphics = new LevelGraphics(this);

		// COLLISION HANDLER SHOULD TAKE LEVEL AS AN ARGUMENT AND SET THE VALUES FROM THERE!!!!
			
		
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
		
//		//Add broadcasters
//		for (Actor actor:actors){
//			if(actor instanceof Broadcaster){
//				broadcaster.add((Broadcaster) actor);
//			}
//		}
		
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



	public void render(Graphics g,int playerX, int playerY){		
		setLevelCoordinates(playerX,playerY);

		levelGraphics.render(g,renderOffsetX,renderOffsetY);
		
		for (BasicObject obj : basicObjects){
//			System.out.println(obj);	
			obj.render(g, renderOffsetX,renderOffsetY);
			
		}
	}



		private void setLevelCoordinates(int playerX, int playerY){
			
	
			// allows the player to get within bufferDistX/bufferDistY of the top/side
//			if (renderOffsetX > (playerX - bufferDistX) ){renderOffsetX = playerX-bufferDistX;}
//			if (renderOffsetX < (playerX+bufferDistX-screenWidth)){renderOffsetX = playerX+bufferDistX-screenWidth;}
//			if (renderOffsetY > (playerY - bufferDistY) ){renderOffsetY = playerY-bufferDistY;}
//			if (renderOffsetY < (playerY+bufferDistY-screenHeight)){renderOffsetY = playerY+bufferDistY-screenHeight;}
//			
			renderOffsetX = boundCoordinate(renderOffsetX,playerX-bufferDistX,playerX+bufferDistX-screenWidth);
			renderOffsetY = boundCoordinate(renderOffsetY,playerY-bufferDistY,playerY+bufferDistY-screenHeight);

			if(renderOffsetX<0){
				renderOffsetX = 0;
				bufferDistX = 0;			
			}else if(renderOffsetX>(levelWidth*tileSize-screenWidth)){
				renderOffsetX = levelWidth*tileSize-screenWidth;
				bufferDistX = 0;
			}else{
				bufferDistX = bufferDist*tileSize;
			}
	
	
			if(renderOffsetY<0){
				renderOffsetY = 0;
				bufferDistY = 0;			
			}
			else if(renderOffsetY>(levelHeight*tileSize-screenHeight)){
				renderOffsetY = levelHeight*tileSize-screenHeight;
				bufferDistY = 0;
			}
			else{
				bufferDistY = bufferDist*tileSize;
			}
		}

		
		public int boundCoordinate(int coordinate, int max, int min){
			if (coordinate<min){return min;}
			if (coordinate>max){return max;}
			return coordinate;
			
		}

		public int getRenderOffsetX(){
			return renderOffsetX;
		}
		public int getRenderOffsetY(){
			return renderOffsetY;
		}

}
		
	//
//		private void initializeMapProperties(){
//			// used for drawing (allows the dude to be outside the center of the screen)
//			tileSize = map.getTileHeight();
//			bufferDistX = tol*tileSize;
//			bufferDistY = tol*tileSize;
//			
//			mapWidthInTiles = levelWidth;
//			mapHeightInTiles = levelHeight;
//		}
	//
//		public int getrenderOffsetX(){return renderOffsetX;}
//		public int getrenderOffsetY(){return renderOffsetY;}
	//
	//
//		public CollisionHandler getCollisionHandler(){
//			return collisionHandler;
//		}
	//
//
//		evel.draw(graphics,(int) terri.getX(),(int)terri.getY());
//		terri.render(graphics, level.getrenderOffsetX(),level.getrenderOffsetY());}

	


