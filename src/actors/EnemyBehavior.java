package actors;

import commands.CommandProvider;
import commands.DieCommand;
import commands.MoveCommand;

//Gives commands to an actor based on world conditions
public class EnemyBehavior extends ActorBehavior implements CommandProvider{

	
	
	
	public EnemyBehavior(Status status) {
		super(status);

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
		
		if (status.hasEffect(Effect.EFFECT_X_COLLISION)){
			int oldXDir = status.getDirection('x');
			status.setDirection('x',-oldXDir);
		}
		
		commandStack.add(new MoveCommand('x', status.getDirection('x')));
		
		if (status.hasEffect(Effect.EFFECT_Y_COLLISION)){
			int oldYDir = status.getDirection('y');
			status.setDirection('y',-oldYDir);
		}
		
		commandStack.add(new MoveCommand('y', status.getDirection('y')));
		
		return;
	}
	
	
}
