package render;


import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.ShapeFill;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Shape;

/* Draws a single image at the location of a shape */

public class ShapeRenderer extends Renderer{

	private ShapeFill fill;
	
	private Shape shape;
	
	//	public ItemGraphics(Image image, ItemLocation location) throws SlickException{
	public ShapeRenderer(Shape shape, Color color) throws SlickException{	
		
		this.shape = shape;		
		this.fill = new GradientFill(0,0,color,1,1,color);
		
	}
	
	


	public void render(Graphics g, int offsetX, int offsetY) {

		render(g, offsetX,offsetY,1f);

	}

	
	private void renderShape(Graphics g, int renderX, int renderY){
		float x = shape.getX();
		float y = shape.getY();
		shape.setX(x - renderX);
		shape.setY(y -renderY);
		g.fill(shape,fill);
		shape.setX(x);
		shape.setY(y);
		

	}
	
	public void render(Graphics g, int renderX, int renderY, float scale) {
		
		this.renderShape(g, renderX, renderY);
	
		
	}


	

}