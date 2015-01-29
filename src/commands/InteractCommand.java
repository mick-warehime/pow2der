package commands;

import org.newdawn.slick.command.BasicCommand;

import actionEngines.ActionEngine;
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
		PlayerActionEngine playerEngine = (PlayerActionEngine) engine; 
		playerEngine.attemptInteract(interactionType, playerEngine.getStatus());
		playerEngine.applyEffect(Effect.EFFECT_INTERACTING, 1);
		
		
	}

	
}
