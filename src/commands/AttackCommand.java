package commands;

import org.newdawn.slick.command.BasicCommand;

import actionEngines.ActionEngine;
import actionEngines.ActorActionEngine;
import actionEngines.PlayerActionEngine;
import actors.Effect;


public class AttackCommand extends BasicCommand implements GenericCommand{

	private int attackType;
	
	public AttackCommand(int attackType) {
		super("Attack Command");
		this.attackType = attackType;
		
		// 
	}

	@Override
	public void execute(ActionEngine engine) {
		System.out.println("I ATTACKED YOU, B#*$!");
		((ActorActionEngine) engine).attemptAttack();
	}

	
}
