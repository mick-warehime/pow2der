package actionEngines;

import org.newdawn.slick.SlickException;

import abilities.Ability;
import abilities.NullAbility;

/* 
 * Stores abilities for actionEngines to implement
 */

public class AbilitySlots {
	
	private int size = 3;
	Ability[] abilitySlots;
	
	public AbilitySlots() throws SlickException{
		abilitySlots = new Ability[size];
		
		for (int i = 0; i< size; i++ ){
			abilitySlots[i] = new NullAbility();
		}
		
	}

	public Ability getAbility(int slotIndex) throws NullPointerException{
		Ability output = abilitySlots[slotIndex];
		
		if (output == null){
			System.out.println("Attempted to access an empty ability slot!");
			throw new NullPointerException();
		}
		
		return output;
		
		
	}
	
	public void setAbility(Ability ability, int slotIndex) throws NullPointerException,IndexOutOfBoundsException{
		
		
		if (ability == null){
			System.out.println("Can't set a null vector as an ability!");
			throw new NullPointerException();
		}
		
		if (slotIndex<0 || slotIndex>= size){
			System.out.println("slot Index" + slotIndex + 
					" out of range of ability Slots! (size: "  + size+")");
			throw new IndexOutOfBoundsException();
		}
		
		abilitySlots[slotIndex] = ability;
		
		
	}
	
	

}
