package commands;

import org.newdawn.slick.command.BasicCommand;

import actors.ActionEngine;

public class GainEffectCommand extends BasicCommand implements GenericCommand{

	
	private int effectName;
	private int duration;

	public GainEffectCommand(int effectName, int duration) {
		super("Gain effect " + effectName);
		this.effectName = effectName;
		this.duration = duration;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(ActionEngine actionEngine) {
		actionEngine.applyEffect(effectName,duration);
		
	}

}
