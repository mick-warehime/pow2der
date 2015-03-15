package interfaces;

import actors.Status;


//An object the player can interact with
public interface Interactive {
	
	public static final int INTERACTION_TOGGLE = 0;
	public static final int INTERACTION_PICKUP = 1;

	
	public void interact(int interactionType, Status status);
	public boolean isAccessible(Status status);

}
