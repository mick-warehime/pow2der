package commands;

import org.newdawn.slick.command.BasicCommand;

import actionEngines.ActionEngine;
import actionEngines.ActorActionEngine;
import actors.Effect;


public class MoveCommand extends BasicCommand implements GenericCommand{

	private char direction;
	private int value;


	public MoveCommand(char direction, int value) {
		super("Move " + direction + value);
		this.direction = direction;
		this.value = value;

		assert (direction == 'x' || direction =='y') : "Improper move direction specification";

	}


	@Override
	public void execute(ActionEngine engine){
		if (engine instanceof ActorActionEngine){
			((ActorActionEngine) engine).attemptRunTo(direction, value);
			if( direction == 'x'){
				((ActorActionEngine) engine).applyEffect(Effect.EFFECT_WALKING_X, 1);
			}else{
				((ActorActionEngine) engine).applyEffect(Effect.EFFECT_WALKING_Y, 1);
			}
		}


	}

	public char getDirection(){
		return this.direction;
	}



}
