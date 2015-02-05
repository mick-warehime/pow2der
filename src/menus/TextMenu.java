package menus;

import org.newdawn.slick.Graphics;

public abstract class TextMenu extends Menu {

	
	protected int textLineHeight = 16;

	public TextMenu(int menuType, int menuRenderX, int menuRenderY) {
		super(menuType,menuRenderX, menuRenderY);
		

		defineMenuSelections();
	}


	protected abstract void defineMenuSelections();
	
	

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

}