package actionEngines;

import java.util.ArrayList;

import actors.Status;
import commands.CommandProviderAggregator;

public class EnemyActionEngine extends ActorActionEngine {

	
	
	public EnemyActionEngine(CommandProviderAggregator listener, Status status, AbilitySlots slots, ArrayList<Object>objectsToCreate) {
		super(listener, status,slots,objectsToCreate);
		this.acceleration = (float ) 0.5;
		this.walkSpeed = (float) 1.5;
	}

}
