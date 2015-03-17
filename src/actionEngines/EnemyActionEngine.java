package actionEngines;

import java.util.ArrayList;

import actors.BehaviorProfile;
import actors.Effect;
import actors.EnemyBehavior;
import actors.Status;
import commands.CommandProviderAggregator;

public class EnemyActionEngine extends ActorActionEngine {

	
	private BehaviorProfile behaviorProfile;
	
	public EnemyActionEngine(CommandProviderAggregator listener, Status status, AbilitySlots slots, 
			ArrayList<Object>objectsToCreate, BehaviorProfile behaviorProfile) {
		super(listener, status,slots,objectsToCreate);
		
		this.walkSpeed = (float) 1.5;
		this.acceleration = (float) 0.5;
		this.runSpeed = 3;
		this.behaviorProfile = behaviorProfile;
		
	}


}
