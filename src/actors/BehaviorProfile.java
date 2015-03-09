package actors;


public class BehaviorProfile {
	
	private float agroDistance;
	private int agroTime;
	
	private float attackDistance;
	private int attackCooldown;
	private int updateSearchDirectionTime;
	
	public BehaviorProfile(){
		agroDistance = 200;
		attackDistance = 150;
		attackCooldown = 120;
		agroTime = 60;
		updateSearchDirectionTime = 10;
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
	
	

}
