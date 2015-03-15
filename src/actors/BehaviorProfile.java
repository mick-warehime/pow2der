package actors;

import knowledge.Knowledge;


public class BehaviorProfile {
	
	private float agroDistance;
	private int agroTime;
	
	private float attackDistance;
	private int attackCooldown;
	private int updateSearchDirectionTime;
	private boolean agroOnSight;
	
	public BehaviorProfile(int enemyID){
		switch(enemyID){
		case 0:
			agroDistance = 200;
			attackDistance = 150;
			attackCooldown = 120;
			agroTime = 60;
			updateSearchDirectionTime = 10;
			agroOnSight = false;
			break;
		case 1:
			agroDistance = 200;
			attackDistance = 150;
			attackCooldown = 120;
			agroTime = 60;
			updateSearchDirectionTime = 10;
			agroOnSight = true;
			break;
		}
	}
	

	public int getAgroTime(){
		return agroTime;
	}
	
	public int getChaseTime(){
		return updateSearchDirectionTime;
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


	public boolean getsAgro(Knowledge knowledge) {

		return knowledge.playerIsVisible() 
				& knowledge.distToPlayer() < agroDistance
				& agroOnSight;
	}
	
	

}
