package actors;

import gameobjects.Removeable;

public class Effect implements Removeable {

	public static final int EFFECT_X_COLLISION = 0;
	public static final int EFFECT_Y_COLLISION = 1;
	public static final int EFFECT_COLLIDED_WITH_PLAYER = 2;
	public static final int EFFECT_WALKING_X = 3;
	public static final int EFFECT_WALKING_Y = 4;
	public static final int EFFECT_INTERACTING = 5;
	public static final int EFFECT_CASTING_ABILITY = 6;
	public static final int EFFECT_RUNNING = 7;
	public static final int EFFECT_WALKING = 8;
	public static final int EFFECT_DYING = 9;
	public static final int EFFECT_ATTACKING = 10;
	
	public static final int[] EFFECTS_PREVENTING_ACTION = new int[]
		{EFFECT_INTERACTING,EFFECT_CASTING_ABILITY};
	
	
	
	
	public int name;
	public int timer;

	public Effect(int name, int duration){
		this.name = name;
		this.timer = duration;
	}

	//Count down to effect end
	public void countDown(){
		timer -=1;
		
	}

	@Override
	public boolean shouldRemove() {
		// TODO Auto-generated method stub
		return (timer <= 0);
	}


	



}
