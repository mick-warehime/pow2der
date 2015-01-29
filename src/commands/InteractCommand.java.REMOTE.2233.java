package commands;

import org.newdawn.slick.command.BasicCommand;

import actionEngines.ActionEngine;
import actionEngines.ActorActionEngine;
import actionEngines.PlayerActionEngine;
import actors.Effect;

public class InteractCommand extends BasicCommand implements GenericCommand{

	private int interactionType;

	public InteractCommand(int interactionType) {
		super("Interact Command");
		this.interactionType = interactionType;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(ActionEngine engine) {
		if (engine instanceof PlayerActionEngine){
			((PlayerActionEngine) engine).attemptInteract(interactionType);
			((PlayerActionEngine) engine).applyEffect(Effect.EFFECT_INTERACTING, 1);}

	}


}
