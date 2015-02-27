package commands;

import org.newdawn.slick.command.BasicCommand;

import abilities.AbilityObject;
import actionEngines.ActionEngine;
import actionEngines.ActorActionEngine;
import actors.Effect;


public class ActivateAbilityCommand extends BasicCommand implements GenericCommand{

	


	private AbilityObject ability;

	public ActivateAbilityCommand(AbilityObject ability) {
		super("Activate ability " + ability);
		this.ability = ability;

		
	}


	@Override
	public void execute(ActionEngine engine){
		if (engine instanceof ActorActionEngine){
			((ActorActionEngine) engine).activateAbility(ability);
			
		}


	}

	


}
