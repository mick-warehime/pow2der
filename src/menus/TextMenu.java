package menus;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public abstract class TextMenu extends Menu {

	protected int menuRenderX ;
	protected int menuRenderY ;
	protected int textLineHeight = 16;

	public TextMenu(int menuType, int menuRenderX, int menuRenderY) {
		super(menuType);
		this.menuRenderX = menuRenderX;
		this.menuRenderY = menuRenderY;

		defineMenuSelections();
	}


	protected abstract void defineMenuSelections();
	
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

}