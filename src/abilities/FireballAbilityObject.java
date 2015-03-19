package abilities;

import interfaces.Broadcaster;
import interfaces.CollidesWithSolids;

import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;

import render.ParticleRenderer;
import collisions.PhysicalCollisions;

import commands.IncrementHPCommand;

public class FireballAbilityObject extends AbilityObject implements Broadcaster, CollidesWithSolids {

	private int countDown;
	private int radius= 5;
	private float[] moveDirection;
	private float speed = 5;
	private int damage = 2;
	private boolean shouldRemove;
	private PhysicalCollisions detector;
	
	


	public FireballAbilityObject(float startX, float startY, float[] moveDirection) throws SlickException, IOException {

		this.moveDirection = moveDirection;

		this.shape = new Circle(startX,startY, radius);
		this.renderer = new ParticleRenderer("data/particle.png","data/fire.xml",shape,moveDirection,5);
//		this.renderer = new LineRenderer();
		
		this.countDown = 50;
		
		this.canCollide = false;
		shouldRemove = false;
		
	}



	@Override
	public boolean shouldRemove() {
		return shouldRemove;
	}

	@Override
	public void update() {
		
		if (detector == null) {
			throw new NullPointerException("Attempted to update a fireball that hasn't yet been assigned a physical collisions instance");
		}
		
		float oldX = shape.getX();
		float oldY = shape.getY();

		shape.setX(oldX + speed*moveDirection[0]);
		shape.setY(oldY + speed*moveDirection[1]);

		countDown -=1;

		if (countDown<0){
			shouldRemove = true;
		}
		if (detector.isCollidedWithSolids(shape)){
			shouldRemove = true;
		}

	}



	@Override
	public void onCollisionDo(Class<?> collidingObjectClass,
			Shape collidingObjectShape) {

		//		if (collidingObjectClass.equals(Actor.class)){
		shouldRemove = true;			
		//		}

	}



	@Override
	public ArrayList<Command> onCollisionBroadcast(
			Class<?> collidingObjectClass, Shape collidingObjectShape) {
		ArrayList<Command> output = new ArrayList<Command>();

		//		if (collidingObjectClass.equals(Actor.class)){
		output.add( new IncrementHPCommand(-damage));
		//		}
		return output;
	}



	@Override
	public Shape getInteractionRange() {
		// TODO Auto-generated method stub
		return this.shape;
	}



	@Override
	public void onRemoveDo() {
		this.moveDirection = new float[] {0f,1f};
		
	}



	@Override
	public void assignCollisionDetector(PhysicalCollisions detector) {
		this.detector = detector;
		
	}

}
