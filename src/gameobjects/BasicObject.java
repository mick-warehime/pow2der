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
	
	private static final int PROXIMITY = 1;
	
	public BasicObject(Shape shape) throws SlickException{
		
		this.shape = shape;
	
	}
	
	public BasicObject(Image image, int xPos, int yPos) throws SlickException{
		
		
		int h = image.getHeight();
		int w = image.getWidth();
			
		shape = new Rectangle(xPos,yPos,w,h);
	
		this.graphics = new BasicGraphics(image,shape);
		
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
		
		Rectangle slightlyBiggerRect = 
				new Rectangle(shape.getX()-PROXIMITY,
							shape.getY()-PROXIMITY,
							shape.getWidth()+2*PROXIMITY,
							shape.getHeight()+2*PROXIMITY);
		return slightlyBiggerRect.intersects(rectTest);
	}


	public Image getImage(){
		return this.graphics.getImage();
	}
	

}
