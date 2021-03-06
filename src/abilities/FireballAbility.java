package abilities;

import java.io.IOException;

import org.newdawn.slick.SlickException;

import actors.Effect;
import actors.Status;

public class FireballAbility extends Ability {

	public FireballAbility(){
		onCastEffects = new int[2][2];
		onCastEffects[0][0] = Effect.EFFECT_CASTING_ABILITY;
		onCastEffects[0][1] = 20;
		onCastEffects[1][0] = Effect.EFFECT_WINDED;
		onCastEffects[1][1] = 5;
	}
	
	@Override
	public boolean hasAbilityObject() {
		return true;
	}

	@Override
	public AbilityObject instantiateAbilityObject(Status casterStatus)
			throws SlickException, IOException {
		
	
		
		float[]  facingDirection = casterStatus.getFacingDirection();
		float initialPositionOffset = 30f;
		
		float casterX = casterStatus.getCenterX();
		float casterY = casterStatus.getCenterY();
		
		float startX = casterX + facingDirection[0]*initialPositionOffset;
		float startY = casterY + facingDirection[1]*initialPositionOffset;
		
		
		
		return new FireballAbilityObject( startX,startY, facingDirection);
		
	}

	

}
