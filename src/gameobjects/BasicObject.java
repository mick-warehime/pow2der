package gameobjects;

import graphics.BasicGraphics;
import graphics.TileGraphics;

import java.util.ArrayList;
import java.util.Properties;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.tiled.TiledMap;

import world.CollisionHandler;

/* An ingame object with rendering and collision functionality */

public class BasicObject {
	
	protected BasicGraphics graphics;	
	protected Shape shape;	
	protected boolean canCollide = true;
	
	private static final int PROXIMITY = 10;

	public BasicObject(Image image, int xPos, int yPos) throws SlickException{
		
		
		int h = image.getHeight();
		int w = image.getWidth();
		
//		items are a bit smaller than their bounding boxes
		
		int hCrop = (int) h/4;
		int wCrop = (int) w/4;
		
		// used for collision detection		
		shape = new Rectangle(xPos+wCrop,yPos+hCrop,w-2*wCrop,h-2*hCrop);
		
		this.graphics = new BasicGraphics(image,xPos,yPos);
		
	}
	

	public Shape getShape(){
		return shape;
	}


	public void render(Graphics g, int renderX, int renderY){
		graphics.render(g, renderX, renderY);		
	}

	public boolean canCollide(){
		return canCollide;
	}

	//Checks if an input shape is near the object's shape
	public boolean isNear(Rectangle rectTest) {
		
		Rectangle slightlyBiggerRect = new Rectangle(shape.getX()-PROXIMITY,shape.getY()-PROXIMITY,shape.getWidth()+2*PROXIMITY,shape.getHeight()+2*PROXIMITY);
		return slightlyBiggerRect.intersects(rectTest);
	}


	public Image getImage(){
		return this.graphics.getImage();
	}
	

}
