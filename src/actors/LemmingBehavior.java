package actors;

import java.util.ArrayList;

import org.newdawn.slick.command.Command;

import world.CollisionHandler;
import commands.CommandProvider;
import commands.MoveCommand;

//Gives commands to an actor based on world conditions
public class LemmingBehavior extends Behavior implements CommandProvider{

	
	
	
	public LemmingBehavior(Status status) {
		super(status);

	}

	public void determine(){
		
		commandStack.clear();


		if (status.hasEffect(Effect.EFFECT_COLLIDED_WITH_PLAYER)){
			resolvePlayerCollision();
		}

		//Resolve collision with interactives
		resolveInteractiveCollisions();

		decideMovement();
	}
	
	
	//Apply these reactions on Player Collision
	private void resolvePlayerCollision(){
//		commandStack.add(new DieCommand());

	}
	
	private void decideMovement(){
		
		if (status.hasEffect(Effect.EFFECT_X_COLLISION)){
			int oldXDir = status.getDirection('x');
			status.setDirection('x',-oldXDir);
		}
		
		commandStack.add(new MoveCommand('x', status.getDirection('x')));
		
		return;
	}
	
	private void resolveInteractiveCollisions(){
		ArrayList<Command> newCommands = collisionHandler.resolveInteractiveCollisions(status.getRect(), Enemy.class);
		commandStack.addAll(newCommands);
		return;
	}

}
