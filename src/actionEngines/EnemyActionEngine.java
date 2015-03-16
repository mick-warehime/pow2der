package actionEngines;

import java.util.ArrayList;

import actors.Effect;
import actors.Status;
import commands.CommandProviderAggregator;

public class EnemyActionEngine extends ActorActionEngine {

	
	
	public EnemyActionEngine(CommandProviderAggregator listener, Status status, AbilitySlots slots, ArrayList<Object>objectsToCreate) {
		super(listener, status,slots,objectsToCreate);
		
		this.walkSpeed = (float) 1.5;
		this.acceleration = (float) 0.5;
		this.runSpeed = 3;
		
	}
	
	@Override
	public void incrementHP(int increment) {
		int agroAttackTime = 50000;
		status.gainEffect(Effect.AGROED,agroAttackTime);

		status.incrementHP(increment);
	}


}
