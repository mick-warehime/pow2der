package abilities;

import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;

import render.ShapeRenderer;
import actors.Status;

public class FireballAbilityObject extends AbilityObject {

	private int countDown;
	private int radius= 10;
	private float[] moveDirection;
	private float speed = 4;
		
	
	public FireballAbilityObject(Status casterStatus) throws SlickException {
		
		this.moveDirection = new float[] {0f,1f};
		
		this.shape = new Circle(casterStatus.getX(),casterStatus.getY(), radius);
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
		
		System.out.println(shape.getCenterX() + "," + shape.getCenterY());
		countDown -=1;

	}

}
