package actors;

import knowledge.Knowledge;
import commands.CommandProvider;
import commands.DieCommand;
import commands.MoveCommand;
import commands.MoveCommandNew;

//Gives commands to an actor based on world conditions
public class EnemyBehavior extends ActorBehavior implements CommandProvider{

	private Knowledge knowledge;

	public EnemyBehavior(Status status, Knowledge knowledge) {
		super(status);
		this.knowledge = knowledge;

	}

	public void determine(){

		commandStack.clear();


		//		if (status.hasEffect(Effect.EFFECT_COLLIDED_WITH_PLAYER)){
		//			resolvePlayerCollision();
		//		}


		decideMovement();
	}



	private void resolvePlayerCollision(){
		commandStack.add(new DieCommand());

	}

	private void decideMovement(){

		// if the dude can see the player chase him, otherwise keep going in same direction
		float[] currentDirection = status.getFacingDirection();
		if(knowledge.playerIsVisible()){
			currentDirection = knowledge.directionToPlayer();
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
