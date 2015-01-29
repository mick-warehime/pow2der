package menus;

import java.util.ArrayList;

public class MainMenu extends Menu {

	
	
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
	public void render() {
		
		
	}

	
	
}
