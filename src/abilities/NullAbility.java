package abilities;

import org.newdawn.slick.SlickException;

import actors.Effect;

public class NullAbility extends Ability {

	
	

	public NullAbility() throws SlickException {		
		super();
		onCastEffects = new int[1][1];
		onCastEffects[0][0] = Effect.EFFECT_CASTING_ABILITY;
		onCastEffects[0][1] = 20;
	}

	@Override
	public AbilityObject instantiateAbilityObject(int xPos, int yPos) throws SlickException {
		return new NullAbilityObject(xPos,yPos);
	}

	

}
