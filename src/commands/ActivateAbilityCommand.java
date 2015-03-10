package commands;

import java.io.IOException;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.command.BasicCommand;

import actionEngines.ActionEngine;
import actionEngines.ActorActionEngine;


public class ActivateAbilityCommand extends BasicCommand implements GenericCommand{

	


	private int abilitySlot;

	public ActivateAbilityCommand(int abilitySlot) {
		super("Activate ability slot " +abilitySlot );
		this.abilitySlot = abilitySlot;

		
	}


	@Override
	public void execute(ActionEngine engine) throws SlickException, IOException{
		if (engine instanceof ActorActionEngine){
			((ActorActionEngine) engine).attemptActivateAbility(abilitySlot);
			
		}


	}

	


}
