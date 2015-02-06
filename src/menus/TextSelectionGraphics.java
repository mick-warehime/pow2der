package menus;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class TextSelectionGraphics implements MenuSelectionGraphics {

	private String text;
	private int xPos;
	private int yPos;
	
	public TextSelectionGraphics(String text, int xPos, int yPos){
		this.text = text;
		this.xPos = xPos;
		this.yPos = yPos;
	}
	@Override
	public void render(Graphics graphics, boolean isActiveSelection) {
		if (isActiveSelection){	
			graphics.setColor(Color.red);
		}else{ 
			graphics.setColor(Color.white);}
		
		graphics.drawString(text, xPos, yPos);

	}

}
