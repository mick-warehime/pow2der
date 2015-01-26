package gameobjects;


//An object the player can interact with
public interface Interactive {
	
	public static final int INTERACTION_TOGGLE = 0;
	public static final int INTERACTION_PICKUP = 1;
	
	public void interact(int interactionType);

}
