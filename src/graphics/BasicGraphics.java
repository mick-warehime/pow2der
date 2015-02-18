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

	private int sprite_margin = 0;
	protected Image sprite;
	private Shape shape;
	
	//	public ItemGraphics(Image image, ItemLocation location) throws SlickException{
	public BasicGraphics(Image sprite, Shape shape) throws SlickException{	
		this.sprite = sprite;
		this.shape = shape;		

	}
	
	public void setSpriteMargin(int marg){
		this.sprite_margin = marg;
	}


	public void render(Graphics g, int renderX, int renderY) {

		render(g, renderX,renderY,1f);

	}

	private void renderShape(Graphics g, int renderX, int renderY){
		float x = shape.getX();
		float y = shape.getY();
		shape.setX(x - renderX);
		shape.setY(y -renderY);
		g.draw(shape);
		shape.setX(x);
		shape.setY(y);

	}
	
	public void render(Graphics g, int renderX, int renderY, float scale) {
		
//		this.renderShape(g, renderX, renderY);
		
		float x = shape.getX();
		float y = shape.getY();
		
		sprite.draw(x-renderX- sprite_margin,y-renderY- sprite_margin, scale);
		
	}


	public Image getImage() {
		// TODO Auto-generated method stub
		return sprite;
	}

}
