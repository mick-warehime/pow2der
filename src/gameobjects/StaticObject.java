package gameobjects;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import render.BasicRenderer;

/* A basic object with only a single image used for drawing. */

public class StaticObject extends BasicObject {
	
	
	
	public StaticObject(Image image, int xPos, int yPos) throws SlickException{
		
		
		int h = image.getHeight();
		int w = image.getWidth();
			
		shape = new Rectangle(xPos,yPos,w,h);
	
		this.renderer = new BasicRenderer(image,shape);
		
	}
	

	public Image getImage(){
		return ((BasicRenderer)renderer).getImage();
	}
	

}
