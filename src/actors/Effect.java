package actors;

public class Effect {

	public static final int EFFECT_X_COLLISION = 0;
	public static final int EFFECT_Y_COLLISION = 1;
	public static final int EFFECT_COLLIDED_WITH_PLAYER = 2;
	
	public int name;
	public int duration;
	public int timer;

	public Effect(int name, int duration){
		this.name = name;
		this.duration = duration;
		this.timer = duration;
	}

	//Count down to effect end
	public boolean countDown(){
		timer -=1;
		return (timer <= 0);
	}





}
