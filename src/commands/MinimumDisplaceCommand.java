package commands;

import org.newdawn.slick.command.BasicCommand;

import actors.ActionEngine;
import actors.ActorActionEngine;

//Displaces an actor in a direction, by the minimum required amount to 
//undo a collision
public class MinimumDisplaceCommand extends BasicCommand implements GenericCommand {

	
	private char direction;
	private float disp;

	public MinimumDisplaceCommand(float disp, char direction) {
		super("Legal displace in " + direction + " direction"); //Gives command name
		this.direction = direction;
		this.disp = disp;
		
		
		
	}

	@Override
	public void execute(ActionEngine engine) {
		if (engine instanceof ActorActionEngine){
		((ActorActionEngine) engine).attemptDisplacement(disp, direction);
		((ActorActionEngine) engine).attemptDisplacement(-disp, direction);
		}
		

	}

}
