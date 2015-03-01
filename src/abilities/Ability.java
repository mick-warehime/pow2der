package abilities;

import org.newdawn.slick.SlickException;

/*
 * Represents an actor's ingame Ability, including on cast effects and instantiation
 * of an ability object
 * 
 */

public abstract class Ability {
	
	protected int[][] onCastEffects;
	
	public Ability(){
		
	}
	
	public int[][] getOnCastEffects(){
		return this.onCastEffects;
	}
	
	public abstract AbilityObject instantiateAbilityObject(int xPos,int yPos) throws SlickException;

}
