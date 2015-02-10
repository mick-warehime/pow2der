package graphics;


import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Shape;

import actors.Effect;
import actors.Status;

/* Draws a single image at the location of a shape */

public class BasicGraphics {

	protected Image sprite;
	private Shape shape;
	
	//	public ItemGraphics(Image image, ItemLocation location) throws SlickException{
	public BasicGraphics(Image sprite, Shape shape) throws SlickException{	
		this.sprite = sprite;
		this.shape = shape;		

	}


	public void render(Graphics g, int renderX, int renderY) {

		render(g, renderX,renderY,1f);

	}

	public void render(Graphics g, int renderX, int renderY, float scale) {

		sprite.draw(shape.getX()-renderX,shape.getY()-renderY, scale);
		
	}


	public Image getImage() {
		// TODO Auto-generated method stub
		return sprite;
	}

}
