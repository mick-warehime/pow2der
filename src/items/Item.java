package items;

import java.util.ArrayList;
import java.util.Map;

import org.newdawn.slick.Image;

public class Item {
	
	protected String name;
	protected Image sprite;
	protected Integer value;
	protected String type;
	protected boolean stackable;
	protected Integer weight;
	protected ArrayList<String> properties;
	
	public Item(Map<String, String> itm){		
		
		this.type = itm.get("itemType");
//		this.sprite = itm.get("sprite"); 
//		this.value = Integer.parseInt(itm.get("value"));
//		this.stackable = itm.get("stackable");	
		
//		if(properties != null){
//			for (String p : properties){
//				addProperty(p);
//			}
//		}
		
	}
	
	


	public String getItemType() {
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
