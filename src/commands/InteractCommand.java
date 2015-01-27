package commands;

import org.newdawn.slick.command.BasicCommand;

import actors.ActionEngine;
import actors.ActorActionEngine;
import actors.Effect;
import actors.PlayerActionEngine;

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
