package commands;

import org.newdawn.slick.command.BasicCommand;

import actionEngines.ActionEngine;
import actionEngines.ActorActionEngine;

public class TeleportCommand extends BasicCommand implements GenericCommand {

	private int destX;
	private int destY;

	public TeleportCommand(int destX, int destY) {
		super("Teleport");
		this.destX = destX;
		this.destY = destY;
	}

	@Override
	public void execute(ActionEngine actionEngine) {
		if (actionEngine instanceof ActorActionEngine){
			((ActorActionEngine) actionEngine).Teleport(destX,destY);
		}
	}

}
