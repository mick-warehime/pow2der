package gameobjects;

import graphics.TileGraphics;

import java.util.ArrayList;
import java.util.Properties;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.tiled.TiledMap;

import world.CollisionHandler;

public class GameObject {
	
	
	protected TileGraphics graphics;
	

	protected int tileSize;
	protected int proximity;

	protected String name;

	protected Shape shape;
	protected ArrayList<Image> sprites = new ArrayList<Image>(); 
	protected CollisionHandler collisionHandler;
	protected float opacity = 1;

	public GameObject(int tileX, int tileY, int widthInTiles, int heightInTiles, String name, TiledMap map, Properties args) throws SlickException{

		tileSize = map.getTileHeight();				


		this.name = name; 



		// used for collision detection		
		shape = new Rectangle(tileX*tileSize,tileY*tileSize,widthInTiles*tileSize,heightInTiles*tileSize);
		
		setGraphics(shape,map,tileX,tileY,widthInTiles,heightInTiles);
		
	}
	
	//constructor for objects not constructed from data
	public GameObject(String name) {
		this.name = name; 
		
	}
	protected void setGraphics(
			Shape rect, TiledMap map, int tileX, 
			int tileY, int widthInTiles, int heightInTiles) 
					throws SlickException{
		graphics = new TileGraphics(rect, map, tileX,tileY,widthInTiles,heightInTiles);
	}

	//For removal of objects by world
	public boolean isDying(){
		return false;
	}

	public Shape getShape(){
		return shape;
	}

	protected void setObjectDimensions(){
		throw new UnsupportedOperationException(); 
	}

	public void render(int mapX, int mapY){
		graphics.render(mapX, mapY, opacity); 
		
	}

	public boolean canCollide(){
		return true;
	}

	public void setCollisionHandler(CollisionHandler collisionHandler){
		this.collisionHandler = collisionHandler;
	}

	
	//Checks if an input shape is near the object's shape
	public boolean isNear(Rectangle rectTest) {

		Rectangle slightlyBiggerRect = new Rectangle(shape.getX()-proximity,shape.getY()-proximity,shape.getWidth()+2*proximity,shape.getHeight()+2*proximity);
		return slightlyBiggerRect.intersects(rectTest);
	}

	public String getName(){
		return name;
	}

	

}
