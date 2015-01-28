package menus;

import java.util.ArrayList;

public class MainMenu extends Menu {

	
	
	private ArrayList<String> textLines;
	
	public MainMenu(){
		defineTextLines();
	}
	
	private void defineTextLines(){
		textLines = new ArrayList<String>();
		textLines.add("Continue");
		textLines.add("Exit Game");
	}
	
	@Override
	public void draw() {
		System.out.println("Drawing Main Menu...");
	}

	
	
}
