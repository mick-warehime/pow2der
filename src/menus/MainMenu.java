package menus;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class MainMenu extends Menu {

	
	private int menuRenderX = 100;
	private int menuRenderY = 240;
	private int textLineHeight = 16;
	private int activeTextLine = 0;
	
	
	public MainMenu() {
		super(Menu.MENU_MAIN);
		defineTextLines();
	}



	private ArrayList<String> textLines;
	
	
	
	private void defineTextLines(){
		textLines = new ArrayList<String>();
		textLines.add("Continue");
		textLines.add("Exit Game");
	}
	
	

	@Override
	public void render(Graphics graphics) {
		int count = 0;
		for (String line : textLines){
			if (count == this.activeTextLine){
				graphics.setColor(Color.red);
			}else{ graphics.setColor(Color.white);}
			
			graphics.drawString(line, menuRenderX, menuRenderY + count*textLineHeight);
			count+=1;
		}
		
		
	}
	
	public void incrementActiveTextLine(){
		this.activeTextLine +=1;
		activeTextLine = activeTextLine % (textLines.size());
	}
	public void decrementActiveTextLine(){
		this.activeTextLine -=1;
		activeTextLine = activeTextLine % (textLines.size());
	}

	
	
}
