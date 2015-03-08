package abilities;

import org.newdawn.slick.SlickException;

import actors.Effect;
import actors.Status;

public class FireballAbility extends Ability {

	public FireballAbility(){
		onCastEffects = new int[1][2];
		onCastEffects[0][0] = Effect.EFFECT_CASTING_ABILITY;
		onCastEffects[0][1] = 20;
	}
	
	@Override
	public boolean hasAbilityObject() {
		System.out.println("Fireball!!!!");
		return true;
	}

	@Override
	public AbilityObject instantiateAbilityObject(Status casterStatus)
			throws SlickException {
		
		
		return new FireballAbilityObject(casterStatus);
	}

	

}
