package items;

public class ItemLocation {

	private Integer xPos;
	private Integer yPos;
	private Inventory inventory; // should point to an inventory object
	private boolean onGround; 
	
	public ItemLocation(int xPos, int yPos){
		this.xPos = xPos;
		this.yPos = yPos;
		this.inventory = null;
		onGround = true;
	}
	
	public ItemLocation(Inventory inventory){		
		this.xPos = null;
		this.yPos = null;		
		this.inventory = inventory;
		onGround = false;
	}
	
	public boolean onGround(){
		return onGround;
	}
	
	public void pickedUp(Inventory inventory){
		onGround = false;
		this.inventory = inventory;
	}
	
	public void drop(int xPos, int yPos){
		// inventory.removeItem(this);
		this.inventory = null;
		onGround = true;
		this.xPos = xPos;
		this.yPos = yPos;
		
	}
	
	public int getX(){
		if(onGround){
			return xPos;
		}
		return -1;
	}
	
	public int getY(){
		if(onGround){
			return yPos;
		}
		return -1;
	}
	
}
