package menus;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.command.Command;

import commands.CloseAllMenusCommand;
import commands.InputListenerAggregator;

public class MainMenu extends Menu {


	private int menuRenderX = 100;
	private int menuRenderY = 240;
	private int textLineHeight = 16;
	private int activeSelection = 0;
	private boolean selectionActivated = false;


	private ArrayList<MenuSelection> selections;
	


	public MainMenu() {
		super(Menu.MENU_MAIN);
		defineMenuSelections();
	}






	private void defineMenuSelections(){
		
		
		selections = new ArrayList<MenuSelection>();
		
		Command closeAll = new CloseAllMenusCommand();
		//new MenuCommandAction(listenerAggregator,closeAll),
		MenuSelection cnt = new MenuSelection(closeAll,
				new textSelectionGraphics("Continue", this.menuRenderX,this.menuRenderY));
		
		
		MenuSelection options = new MenuSelection( null,
				new textSelectionGraphics("Options", this.menuRenderX,this.menuRenderY + this.textLineHeight));
		
		MenuSelection quit = new MenuSelection( null, 
				new textSelectionGraphics("Quit", this.menuRenderX,this.menuRenderY + 2*this.textLineHeight));
		
		selections.add(cnt);
		selections.add(options);
		selections.add(quit);
		
	}
	
	



	@Override
	public void render(Graphics graphics) {
		
		for (MenuSelection selection : selections){
			if (selection == selections.get(activeSelection)){
				graphics.setColor(Color.red);
			}else{ graphics.setColor(Color.white);}
			selection.render(graphics);
		}
		


	}




	@Override
	public void incrementActiveSelection(char xOrY, int direction) {
		if (xOrY == 'y'){
			if (direction>0){
				this.activeSelection +=1;
			}
			else {
				this.activeSelection -=1;
			}
			activeSelection = activeSelection % (selections.size());
			if (activeSelection <0){ 
				activeSelection = selections.size()+activeSelection;
			}
			
			

		}



	}

	public void activateSelection() {
		this.selectionActivated = true;
		
	}



	@Override
	public Command getSelectionCommand() {
		this.selectionActivated = false;
		return selections.get(activeSelection).getCommand();
	}




	@Override
	public boolean isSelectionActivated() {
		// TODO Auto-generated method stub
		return selectionActivated;
	}




}
