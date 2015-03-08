package abilities;

import org.newdawn.slick.SlickException;

import actors.Effect;
import actors.Status;

public class NullAbility extends Ability {

	
	

	public NullAbility() throws SlickException {		
		super();
		onCastEffects = new int[1][2];
		onCastEffects[0][0] = Effect.EFFECT_CASTING_ABILITY;
		onCastEffects[0][1] = 20;
	}

	@Override
	public AbilityObject instantiateAbilityObject(Status casterStatus) throws SlickException {
		return new NullAbilityObject((int) casterStatus.getX(), (int) casterStatus.getY());
	}

	@Override
	public boolean hasAbilityObject() {
		return true;
	}

	

}
