package gameobjects;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import render.Renderer;

public class BasicObject {

	protected Renderer renderer;
	protected Shape shape;
	protected boolean canCollide;
	private static final int PROXIMITY = 1;

	

	public Shape getShape() {
		return shape;
	}

	public void render(Graphics g, int offsetX, int offsetY) {
		renderer.render(g, offsetX, offsetY);		
	}

	public boolean canCollide() {
		return canCollide;
	}

	public boolean isNear(Rectangle rectTest) {

		Rectangle slightlyBiggerRect = 
				new Rectangle(shape.getX()-PROXIMITY,
						shape.getY()-PROXIMITY,
						shape.getWidth()+2*PROXIMITY,
						shape.getHeight()+2*PROXIMITY);
		return slightlyBiggerRect.intersects(rectTest);
	}

}