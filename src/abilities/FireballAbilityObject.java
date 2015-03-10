package abilities;

import gameobjects.Broadcaster;

import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;

import render.ParticleRenderer;
import render.ShapeRenderer;
import commands.IncrementHPCommand;

public class FireballAbilityObject extends AbilityObject implements Broadcaster {

	private int countDown;
	private int radius= 10;
	private float[] moveDirection;
	private float speed = 2;
	private int damage = 2;
	private boolean shouldRemove;


	public FireballAbilityObject(float startX, float startY, float[] moveDirection) throws SlickException, IOException {

		this.moveDirection = moveDirection;

		this.shape = new Circle(startX,startY, radius);
		this.renderer = new ParticleRenderer("data/particle.png","data/fireball.xml",shape,moveDirection);
		
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
		
		float oldX = shape.getX();
		float oldY = shape.getY();

		shape.setX(oldX + speed*moveDirection[0]);
		shape.setY(oldY + speed*moveDirection[1]);

		countDown -=1;

		if (countDown<0){
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

}
