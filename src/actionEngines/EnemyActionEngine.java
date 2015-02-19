package actionEngines;

import actors.Status;
import commands.CommandProviderAggregator;

public class EnemyActionEngine extends ActorActionEngine {

	
	
	public EnemyActionEngine(CommandProviderAggregator listener, Status status) {
		super(listener, status);
		this.runAcc = (float ) 0.5;
		this.maxSpeed = (float) 1.5;
	}

}
