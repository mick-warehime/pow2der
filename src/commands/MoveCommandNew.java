package commands;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.command.BasicCommand;

import actionEngines.ActionEngine;
import actionEngines.ActorActionEngine;
import actors.Effect;


public class MoveCommandNew extends BasicCommand implements GenericCommand{

	private float[] moveDirection;
	private float speed;

	public MoveCommandNew(float moveDirection, float speed) {
		super("Move dir: " + moveDirection+", speed: " + speed);


	}


	@Override
	public void execute(ActionEngine engine){
		if (engine instanceof ActorActionEngine){
			((ActorActionEngine) engine).attemptMoveTo(moveDirection, speed);

			((ActorActionEngine) engine).applyEffect(Effect.EFFECT_WALKING, 1);

		}


	}
	//
	//	public char getDirection(){
	//		return this.direction;
	//	}



}
