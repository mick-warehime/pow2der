package actors;

import knowledge.Knowledge;
import commands.AttackCommand;
import commands.CommandProvider;
import commands.DieCommand;
import commands.MoveCommand;
import commands.MoveCommandNew;

//Gives commands to an actor based on world conditions
public class EnemyBehavior extends ActorBehavior implements CommandProvider{

	private Knowledge knowledge;

	private BehaviorProfile behaviorProfile;

	public EnemyBehavior(Status status, Knowledge knowledge) {
		super(status);
		this.knowledge = knowledge;

		behaviorProfile  = new BehaviorProfile();

	}

	public void determine(){

		commandStack.clear();

		// if not already in the process of attacking check for an attack

		decideAttack();


		decideMovement();


	}

	private void decideAttack(){
		if(canAttack()){
			commandStack.add(new AttackCommand(0));
			status.gainEffect(Effect.EFFECT_ATTACKING, behaviorProfile.getAttackCooldown());
		}
	}

	private boolean canAttack(){

		if(!knowledge.playerIsVisible()){
			return false;
		}
		if(knowledge.distToPlayer() > behaviorProfile.getAttackDistance()){
			return false;
		}
		if(status.hasEffect(Effect.EFFECT_ATTACKING)){
			return false;
		}

		return true;

	}

	private boolean getsAgro(){
		return knowledge.playerIsVisible() 
				& knowledge.distToPlayer() < behaviorProfile.getAgroDistance();
	}


	private void decideMovement(){


		// get direction to player and increment current direction towards player
		

		// if the dude can see the player chase him, otherwise keep going in same direction
		float[] currentDirection = status.getFacingDirection();

		if(getsAgro()){

			// only update 
			if(!status.hasEffect(Effect.EFFECT_AGRO)){
				
				
				currentDirection = knowledge.directionToPlayer();

				status.gainEffect(Effect.EFFECT_AGRO, behaviorProfile.getAgroTime());
			}

		}else{

			if (status.hasEffect(Effect.EFFECT_X_COLLISION)){
				currentDirection[0] = -currentDirection[0];			
			}

			if (status.hasEffect(Effect.EFFECT_Y_COLLISION)){
				currentDirection[1] = -currentDirection[1];
			}
		}
		
		
		status.setFacingDirection(currentDirection);
		
		commandStack.add(new MoveCommandNew(currentDirection));

		return;
	}
 


}
