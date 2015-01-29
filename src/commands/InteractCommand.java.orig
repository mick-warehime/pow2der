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
<<<<<<< HEAD
		((PlayerActionEngine) engine).attemptInteract(interactionType, engine.getStatus());
		engine.applyEffect(Effect.EFFECT_INTERACTING, 1);
=======
		if (engine instanceof PlayerActionEngine){
			((PlayerActionEngine) engine).attemptInteract(interactionType);
			((PlayerActionEngine) engine).applyEffect(Effect.EFFECT_INTERACTING, 1);}

>>>>>>> 509c2872fff17070d75f7375b84a6b62bdd7c8a1
	}


}
