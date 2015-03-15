package gameobjects;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import render.Renderer;

public class BasicObject {

	protected Renderer renderer;
	protected Shape shape;
	protected boolean canCollide;
	private static final int INTERACTION_RANGE = 1;

	

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
				new Rectangle(shape.getX()-INTERACTION_RANGE,
						shape.getY()-INTERACTION_RANGE,
						shape.getWidth()+2*INTERACTION_RANGE,
						shape.getHeight()+2*INTERACTION_RANGE);
		return slightlyBiggerRect.intersects(shape2);
	}

}