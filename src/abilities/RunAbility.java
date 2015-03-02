package abilities;

import org.newdawn.slick.SlickException;

import actors.Effect;

public class RunAbility extends Ability {

	
	

	public RunAbility() throws SlickException {		
		super();
		onCastEffects = new int[1][2];
		onCastEffects[0][0] = Effect.EFFECT_RUNNING;
		onCastEffects[0][1] = 2;
	}

	@Override
	public AbilityObject instantiateAbilityObject(int xPos, int yPos) throws SlickException {
		throw new UnsupportedOperationException("This ability should not be instantiating an object!");
		
//		return new NullAbilityObject(xPos,yPos);
	}

	@Override
	public boolean hasAbilityObject() {
		return false;
	}

	

}
