package commands;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.command.BasicCommand;

import actionEngines.ActionEngine;
import actionEngines.ActorActionEngine;
import actors.Effect;


public class MoveCommandNew extends BasicCommand implements GenericCommand{

	private float xDisp;
	private float yDisp;

	public MoveCommandNew(float xDisp, float yDisp) {
		super("Move x: " + xDisp +", y: " + yDisp);
		
	}

	@Override
	public void execute(ActionEngine actionEngine) throws SlickException {
		// TODO Auto-generated method stub
		
	}

//
//	@Override
//	public void execute(ActionEngine engine){
//		if (engine instanceof ActorActionEngine){
//			((ActorActionEngine) engine).attemptMoveTo(direction, value);
//			if( direction == 'x'){
//				((ActorActionEngine) engine).applyEffect(Effect.EFFECT_WALKING_X, 1);
//			}else{
//				((ActorActionEngine) engine).applyEffect(Effect.EFFECT_WALKING_Y, 1);
//			}
//		}
//
//
//	}
//
//	public char getDirection(){
//		return this.direction;
//	}



}
