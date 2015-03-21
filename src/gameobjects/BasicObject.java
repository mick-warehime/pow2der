package gameobjects;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import render.Renderer;

public class BasicObject {

	protected Renderer renderer;
	protected Shape shape;
	protected boolean canCollide;
	private static final int NEAR_RANGE = 1;

	

	public Shape getShape() {
		return shape;
	}

	public void render(Graphics g, int offsetX, int offsetY) {
		renderer.render(g, offsetX, offsetY);		
	}

	public boolean canCollide() {
		return canCollide;
	}

	public boolean isNear(Shape shape2) {

		Rectangle slightlyBiggerRect = 
				new Rectangle(shape.getX()-NEAR_RANGE,
						shape.getY()-NEAR_RANGE,
						shape.getWidth()+2*NEAR_RANGE,
						shape.getHeight()+2*NEAR_RANGE);
		return slightlyBiggerRect.intersects(shape2);
	}

}