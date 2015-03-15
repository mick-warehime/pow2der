package abilities;

import java.io.IOException;

import org.newdawn.slick.SlickException;

import actors.Effect;
import actors.Status;

public class LightningAbility extends Ability {

	private int range = 200;
	
	public LightningAbility(){
		onCastEffects = new int[2][2];
		onCastEffects[0][0] = Effect.EFFECT_CASTING_ABILITY;
		onCastEffects[0][1] = 20;
		onCastEffects[1][0] = Effect.EFFECT_WINDED;
		onCastEffects[1][1] = 10;
	}
	
	
	
	@Override
	public boolean hasAbilityObject() {
		return true;
	}

	@Override
	public AbilityObject instantiateAbilityObject(Status casterStatus)
			throws SlickException, IOException {
		
		float[]  facingDirection = casterStatus.getFacingDirection();
		float initialPositionOffset = 10f;
		
		
		float casterX = casterStatus.getCenterX();
		float casterY = casterStatus.getCenterY();
		
		float startX = casterX + facingDirection[0]*initialPositionOffset;
		float startY = casterY + facingDirection[1]*initialPositionOffset;
		
		float[] startPt = new float[] {startX, startY};
		
		return new LightningGenerator( startPt, facingDirection);
//		float[] endPt = new float[] {startX+range*facingDirection[0],startY+range*facingDirection[1]};
//		return new LightningAbilityObject( startPt, endPt);
	}

	

}
