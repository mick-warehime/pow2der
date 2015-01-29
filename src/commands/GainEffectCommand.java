package commands;

import org.newdawn.slick.command.BasicCommand;

import actionEngines.ActionEngine;
import actionEngines.ActorActionEngine;

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
		if (actionEngine instanceof ActorActionEngine){
			((ActorActionEngine)actionEngine).applyEffect(effectName,duration);
		}
	}

}
