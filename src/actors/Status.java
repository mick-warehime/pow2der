package actors;

import gameobjects.Interactive;
import items.Inventory;

import java.util.ArrayList;
import java.util.Iterator;

import org.newdawn.slick.geom.Rectangle;

import world.CollisionHandler;

public class Status {

	private boolean isDying;
	private ArrayList<Effect> effects;
	private int xDirection = 1;
	private int yDirection = 1;
	
	private float[] facingDirection;
	
	protected Inventory inventory;

	private Rectangle rect;
	private CollisionHandler collisionHandler;

	public Status(Rectangle rect) {
		this.isDying = false;
		this.rect = rect;
		effects = new ArrayList<Effect>();
		facingDirection = new float[] {(float) Math.random(),(float) Math.random()};
		
		inventory = new Inventory();
	}

	public Rectangle getRect(){
		return rect;
	}

	public void setCollisionHandler(CollisionHandler collisionHandler){
		this.collisionHandler = collisionHandler;
	}


	public ArrayList<Interactive> nearbyInteractives(){

		return collisionHandler.interactiveObjectsNearRect(this.rect);
	}


	public float getX (){return rect.getX();}
	public float getY (){return rect.getY();}


	public boolean isCollided(){

		return collisionHandler.isCollided(rect);

	}

	//Displaces the player 
	public void displace(float speed){

		float newX = rect.getX() + speed*facingDirection[0];
		rect.setX( newX);

		float newY = rect.getY() + speed*facingDirection[1];
		rect.setY( newY);
		
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

	public void setDying(boolean b) {
		this.isDying = b;		
	}

	public boolean isDying(){
		return isDying;
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


	void removeEffect(int name){
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


}
