package gameobjects;

import java.util.Properties;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

//Generic item that can be picked up in the game
public class Item extends GameObject {

	public Item(int tileX, int tileY, int widthInTiles, int heightInTiles,
			String name, TiledMap map, Properties args) throws SlickException {
		super(tileX, tileY, widthInTiles, heightInTiles, name, map, args);
		// TODO Auto-generated constructor stub
	}
	
	public boolean canCollide(){
		return false;
	}

}
