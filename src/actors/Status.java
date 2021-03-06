package actors;

import items.Inventory;

import java.util.ArrayList;
import java.util.Iterator;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import collisions.PhysicalCollisions;

public class Status {

	private ArrayList<Effect> effects;
	private int xDirection = 1;
	private int yDirection = 1;
	
	private int hp = 10;
	
	private float[] facingDirection;
	
	protected Inventory inventory;

	private Rectangle rect;
	private PhysicalCollisions collisionDetector;

	public Status(Rectangle rect) {
		this.rect = rect;
		effects = new ArrayList<Effect>();
		facingDirection = new float[] {(float) Math.random(),(float) Math.random()};
		
		inventory = new Inventory();
	}

	public Rectangle getRect(){
		return rect;
	}

	public void setCollisionDetector( PhysicalCollisions detector){
		this.collisionDetector = detector;
	}


	
	
	public int getHP(){return hp;};

	public float getX (){return rect.getX();}
	public float getY (){return rect.getY();}
	
	public float getCenterX (){return rect.getX()+rect.getWidth()/2;}
	public float getCenterY (){return rect.getY()+rect.getHeight()/2;}


	public boolean isCollidedWithSolids(){
		if (collisionDetector==null){
			throw new NullPointerException("Collision Detector hasn't been set for this actor!");
		}
		return collisionDetector.isCollidedWithSolids(rect);

	}

	//Displaces the player 
	public void displace(float speed){

		float newX = rect.getX() + speed*facingDirection[0];
		rect.setX( newX);

		float newY = rect.getY() + speed*facingDirection[1];
		rect.setY( newY);
		
	}

	public void incrementHP(int increment){
		
		hp += increment;
		
		if (hp<=0){
			gainEffect(Effect.EFFECT_DYING,1);
		}
		if (increment<0){
			gainEffect(Effect.EFFECT_LOSTHP,1);
		}
		if (increment>0){
			gainEffect(Effect.EFFECT_GAINEDHP,1);
		}
		
	}
	
	//Displaces the player 
	public void displace(float disp, char XorY){

		if (XorY == 'x' || XorY == 'X'){
			float newX = rect.getX() + disp;
			rect.setX( newX);
			return;
		} else if (XorY == 'y' || XorY == 'Y'){
			float newY = rect.getY() + disp;
			rect.setY(newY);
			return;
		}
	}


	public Inventory getInventory(){
		return inventory;
	}


	public void setX(float x){
		this.rect.setX(x);
	}

	public void setY(float y){
		this.rect.setY(y);
	}

	public Shape getShape(){
		return rect;
	}

	

	public void updateEffects(){


		//count down on each effect, remove ones that have run down
		for (Iterator<Effect> iterator = effects.iterator(); iterator.hasNext();) {
			Effect eff = iterator.next();

			eff.countDown();
			if (eff.shouldRemove()){
				// Remove the current element from the iterator and the list.
				iterator.remove();
			}
		}



		return;
	}


	public void removeEffect(int name){
		//Iterate over all effect's elements and remove
		for (Iterator<Effect> iterator = effects.iterator(); iterator.hasNext();) {
			Effect eff = iterator.next();
			if(eff.name == name){
				// Remove the current element from the iterator and the list.
				iterator.remove();
			}
		}
	}

	public void gainEffect(int name, int duration){
		effects.add(new Effect(name,duration));
	}

	public boolean hasEffect(int name){

		for (Effect eff: effects){
			if (eff.name == name){
				return true;
			}
		}
		return false;
	}

	public boolean hasEffects( int[] effectList){



		try{
			for (Integer effectName : effectList){
				if (hasEffect(effectName)){ return true;}
			}
			return false;

		} catch (NullPointerException e){
			System.out.println("Tried to determine status of "
					+ "a null array of effect names...");

		}




		return false;



	}



	public float[] getFacingDirection(){
		return facingDirection;

	}
	
	public void setFacingDirection(float[] newDirection){
		this.facingDirection = newDirection;
	}

	public int getDirection(char xOrY){
		assert (xOrY == 'x' || xOrY == 'y') : "x or y inputs only";
		if (xOrY == 'x'){ return xDirection;}
		else {return yDirection;}

	}






	public void setDirection(char xOrY, int direction) {
		assert (xOrY == 'x' || xOrY == 'y') : "x or y inputs only";
		assert (direction*direction ==1): "+1 or -1 values only";

		if (xOrY == 'x'){ xDirection = direction;}
		else {yDirection = direction;}
	}

	public PhysicalCollisions getPhysicalCollisions() {
		// TODO Auto-generated method stub
		return this.collisionDetector;
	}


}
