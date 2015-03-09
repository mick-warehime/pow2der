package commands;

import org.newdawn.slick.command.BasicCommand;

import actionEngines.ActionEngine;
import actionEngines.ActorActionEngine;


public class MoveCommand extends BasicCommand implements GenericCommand{

	private float[] moveDirection;

	public MoveCommand(float[] moveDirection) {
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
