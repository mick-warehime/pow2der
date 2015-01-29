package actionEngines;

import actors.Status;
import commands.InputListenerAggregator;

public class EnemyActionEngine extends ActorActionEngine {

	
	
	public EnemyActionEngine(InputListenerAggregator listener, Status status) {
		super(listener, status);
		this.runAcc = (float ) 0.5;
		this.maxSpeed = (float) 1.5;
	}

}
