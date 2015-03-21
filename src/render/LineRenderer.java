package render;

import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;


/* Draws a single image at the location of a shape */

public class LineRenderer extends Renderer{

	private ArrayList<LineObject> lines;

	//	public ItemGraphics(Image image, ItemLocation location) throws SlickException{
	public LineRenderer(ArrayList<LineObject> lines) throws SlickException, IOException{	
		this.lines = lines;
	}

	public void render(Graphics g, int offsetX, int offsetY) {
		for(int line = 0; line < lines.size(); line++){
			lines.get(line).render(g, offsetX, offsetY);
		}

	}






}