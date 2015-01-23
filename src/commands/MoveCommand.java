package commands;

import org.newdawn.slick.command.BasicCommand;

import actors.ActionEngine;
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
		engine.attemptRunTo(direction, value);
		if( direction == 'x'){
			engine.applyEffect(Effect.EFFECT_WALKING_X, 1);
		}else{
			engine.applyEffect(Effect.EFFECT_WALKING_Y, 1);
		}
		

	}
	
	public char getDirection(){
		return this.direction;
	}
	

	
}
