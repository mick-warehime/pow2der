package menus;

import org.newdawn.slick.Graphics;

public class textSelectionGraphics implements MenuSelectionGraphics {

	private String text;
	private int xPos;
	private int yPos;
	
	public textSelectionGraphics(String text, int xPos, int yPos){
		this.text = text;
		this.xPos = xPos;
		this.yPos = yPos;
	}
	@Override
	public void render(Graphics graphics) {
		graphics.drawString(text, xPos, yPos);

	}

}
