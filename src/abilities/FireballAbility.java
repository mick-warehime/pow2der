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
		
		float dx = 0.5f*casterStatus.getRect().getWidth();
		float dy = 0.5f*casterStatus.getRect().getHeight();
		
		float startX = casterStatus.getX() + dx;
		float startY = casterStatus.getY() + dy;
		
		
		
		return new FireballAbilityObject( startX,startY, casterStatus.getFacingDirection());
	}

	

}
