package actors;

import commands.AttackCommand;

public class BehaviorProfile {
	
	private float agroDistance;
	private int agroTime;
	
	private float attackDistance;
	private int attackCooldown;
	
	public BehaviorProfile(){
		agroDistance = 150;
		attackDistance = 20;
		attackCooldown = 60;
		agroTime = 15;
		
	}
	

	public int getAgroTime(){
		return agroTime;
	}
	
	public float getAttackDistance(){
		return attackDistance;
	}
	
	public float getAgroDistance(){
		return agroDistance;
	}

	public int getAttackCooldown() {
		return attackCooldown;
	}
	
	

}
