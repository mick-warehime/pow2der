package interfaces;


/* represents a basicObject that can be removed from the game world
 * 
 */
public interface Removeable {
	
	public boolean shouldRemove();
	public void onRemoveDo();

}
