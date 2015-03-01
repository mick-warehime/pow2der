package commands;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.command.BasicCommand;

import abilities.AbilityObject;
import actionEngines.ActionEngine;
import actionEngines.ActorActionEngine;
import actors.Effect;


public class ActivateAbilityCommand extends BasicCommand implements GenericCommand{

	


	private int abilitySlot;

	public ActivateAbilityCommand(int abilitySlot) {
		super("Activate ability slot " +abilitySlot );
		this.abilitySlot = abilitySlot;

		
	}


	@Override
	public void execute(ActionEngine engine) throws SlickException{
		if (engine instanceof ActorActionEngine){
			((ActorActionEngine) engine).activateAbility(abilitySlot);
			
		}


	}

	


}
