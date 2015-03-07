package graphics;


import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Shape;

/* Draws a single image at the location of a shape */
public class BasicGraphics{

	private int sprite_margin = 0;
	protected Image image;
	private Shape shape;

	private static SpriteSheet METROIDSPRITES;
	private static SpriteSheet ITEMSPRITES;

	//	public ItemGraphics(Image image, ItemLocation location) throws SlickException{
	public BasicGraphics() throws SlickException{
		if(ITEMSPRITES == null){ITEMSPRITES = new SpriteSheet("data/items.png",48,48);}
		if(METROIDSPRITES == null){METROIDSPRITES = new SpriteSheet("DATA/METROIDTILES.PNG",16,16);}
	}

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

	public void render(Graphics g, int renderX, int renderY) {
		render(g, renderX,renderY,1f);
	}

	public void render(Graphics g, int renderX, int renderY, float scale) {

		float x = shape.getX();
		float y = shape.getY();

		image.draw(x-renderX- sprite_margin,y-renderY- sprite_margin, scale);		
	}



}
