package abilities;

import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;

import render.ShapeRenderer;

public class FireballAbilityObject extends AbilityObject {

	private int countDown;
	private int radius= 10;
	private float[] moveDirection;
	private float speed = 4;
		
	
	public FireballAbilityObject(float startX, float startY, float[] moveDirection) throws SlickException {
		
		this.moveDirection = moveDirection;
		
		this.shape = new Circle(startX,startY, radius);
		this.renderer = new ShapeRenderer(shape, Color.red);
		
		this.countDown= 50;
		this.canCollide = false;
	}



	@Override
	public boolean shouldRemove() {
		return countDown<0;
	}

	@Override
	public void update() {
		float oldX = shape.getX();
		float oldY = shape.getY();
		
		shape.setX(oldX + speed*moveDirection[0]);
		shape.setY(oldY + speed*moveDirection[1]);
		
		countDown -=1;

	}

}
