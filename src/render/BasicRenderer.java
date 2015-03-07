package render;


import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;

/* Draws a single image at the location of a shape */
public class BasicGraphics{

public class BasicRenderer extends Renderer{

	private int sprite_margin = 0;
	protected Image image;
	private Shape shape;

	private static SpriteSheet METROIDSPRITES;
	private static SpriteSheet ITEMSPRITES;

	//	public ItemGraphics(Image image, ItemLocation location) throws SlickException{
	public BasicRenderer(Image sprite, Shape shape) throws SlickException{	
		this.sprite = sprite;
		this.shape = shape;		

	public void setImage(Image image){
		this.image = image;
	}
	public Image getImage() {
		return image;
	}
	public void setShape(Shape shape){
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

		image.draw(x-renderX- sprite_margin,y-renderY- sprite_margin, scale);		
	}



}
