package commands;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.command.BasicCommand;

import actionEngines.ActionEngine;
import actionEngines.ActorActionEngine;


public class FaceDirectionCommand extends BasicCommand implements GenericCommand{

	




	private float[] newDirection;


	public FaceDirectionCommand(float[] newDirection) {
		super("Face Direction" + newDirection);
		this.newDirection = newDirection;

		
	}


	@Override
	public void execute(ActionEngine engine) throws SlickException{
		if (engine instanceof ActorActionEngine){
			((ActorActionEngine) engine).setFacingDirection(newDirection);
			
		}


	}

	


}
