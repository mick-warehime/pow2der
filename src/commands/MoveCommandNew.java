package commands;

import org.newdawn.slick.command.BasicCommand;

import actionEngines.ActionEngine;
import actionEngines.ActorActionEngine;
import actors.Effect;


public class MoveCommandNew extends BasicCommand implements GenericCommand{

	private float[] moveDirection;

	public MoveCommandNew(float[] moveDirection) {
		super("Move dir: " + moveDirection);
		this.moveDirection = moveDirection;
	}

	@Override
	public void execute(ActionEngine engine){
		if (engine instanceof ActorActionEngine){
			((ActorActionEngine) engine).attemptMove(moveDirection);
		}


	}

	public float[] getDirection(){
		return moveDirection;
	}



}
