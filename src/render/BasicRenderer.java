package render;


import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;

/* Draws a single image at the location of a shape */

public class BasicRenderer extends Renderer{

	private int sprite_margin = 0;
	protected Image sprite;
	private Shape shape;
	
	//	public ItemGraphics(Image image, ItemLocation location) throws SlickException{
	public BasicRenderer(Image sprite, Shape shape) throws SlickException{	
		this.sprite = sprite;
		this.shape = shape;		

	}
	
	public void setSpriteMargin(int marg){
		this.sprite_margin = marg;
	}


	public void render(Graphics g, int offsetX, int offsetY) {

		render(g, offsetX,offsetY,1f);

	}

	@SuppressWarnings("unused")
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
