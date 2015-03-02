package actors;

import items.Inventory;
import items.Item;

import java.util.ArrayList;
import java.util.Iterator;

import org.newdawn.slick.geom.Rectangle;

import world.CollisionHandler;
import gameobjects.BasicObject;
import gameobjects.GameObject;

public class Status {

	private boolean isDying;
	private ArrayList<Effect> effects;
	private int xDirection = -1;
	private int yDirection = 1;
	protected Inventory inventory = new Inventory();
	
	private Rectangle rect;
	private CollisionHandler collisionHandler;

	public Status(Rectangle rect) {
		this.isDying = false;
		this.rect = rect;
		effects = new ArrayList<Effect>();

	}

	public Rectangle getRect(){
		return rect;
	}

	public void setCollisionHandler(CollisionHandler collisionHandler){
		this.collisionHandler = collisionHandler;
	}



	


	public ArrayList<BasicObject> nearbyInteractives(){

		return collisionHandler.interactiveObjectsNearRect(this.rect);
	}


	public float getX (){return rect.getX();}
	public float getY (){return rect.getY();}


	public boolean isCollided(){
		
		return collisionHandler.isCollided(rect);

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
		
//		throw new UnsupportedOperationException("Improper input arguments!");
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
				

			if (eff.countDown()){
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
	
	public boolean hasEffects( int[] names){
		
		try{
			for (Integer name : names){
				if (hasEffect(name)){ return true;}
			}
			return false;
			
		} catch (NullPointerException e){
			System.out.println("Tried to determine status of "
					+ "a null array of effect names...");
			
		}
		
		return false;
		
		
		
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
