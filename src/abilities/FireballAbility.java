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
		return true;
	}

	@Override
	public AbilityObject instantiateAbilityObject(Status casterStatus)
			throws SlickException {
		
		float dx = 0.5f*casterStatus.getRect().getWidth();
		float dy = 0.5f*casterStatus.getRect().getHeight();
		
		float[]  facingDirection = casterStatus.getFacingDirection();
		float initialPositionOffset = 50f;
		float startX = casterStatus.getX() + dx + facingDirection[0]*initialPositionOffset;
		float startY = casterStatus.getY() + dy + facingDirection[1]*initialPositionOffset;
		
		
		
		return new FireballAbilityObject( startX,startY, facingDirection);
	}

	

}
