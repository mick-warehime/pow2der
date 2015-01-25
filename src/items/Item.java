package items;

import java.util.ArrayList;

import org.newdawn.slick.Image;

public class Item {
	
	protected String name;
	protected Image sprite;
	protected Integer value;
	protected Integer type;
	protected boolean stackable;
	protected Integer weight;
	protected ArrayList<String> properties;
	
	public Item(String name, Integer type, Image sprite, Integer weight, Integer value, boolean stackable, ArrayList<String> properties2){		
		this.type = type;
		this.sprite = sprite; 
		this.value = value;
		this.stackable = stackable;	
		
		if(properties != null){
			for (String p : properties){
				addProperty(p);
			}
		}
		
	}
	
	
	public Integer getItemType() {
		return type;
	}
	
	public Integer getValue() {
		return value;
	}
	
	public Image getSprite(){
		return sprite;
	}
	
	public void addProperty(String prop){
		properties.add(prop);
	}
	
	public ArrayList<String> getProperties(){
		return properties;
	}
	
	public boolean stackable() {
		return stackable;
	}
	
	public void equip(){		
	}
	
	public void unequip(){
	}
	
	public void use(){
	}

}
