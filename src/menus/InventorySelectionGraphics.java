package menus;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

//Either shows an item or is blank. Also has an active

public class InventorySelectionGraphics implements MenuSelectionGraphics{

	private static Image BACKGROUND_IMAGE ;

	private Image image;
	private int xPos;
	private int yPos;

	public InventorySelectionGraphics(Image image) throws SlickException{
		//Loading of background image should only be called once
		if (BACKGROUND_IMAGE == null){
			InventorySelectionGraphics.BACKGROUND_IMAGE=new Image("data/InventoryBackground.jpg");
		}

		this.image = image;
	}
	
	public void setDrawPosition(int[] posVec){
		this.xPos = posVec[0];
		this.yPos = posVec[1];
	}
	
	public int[] getDrawPosition(){
		return new int[] {xPos,yPos};
	}
	
	
	@Override
	public void render(Graphics graphics, boolean isActiveSelection) {

		BACKGROUND_IMAGE.draw(xPos,yPos);

		renderSelectionBorder(graphics,isActiveSelection);

		if (image != null){
			image.draw(xPos,yPos);
		}

		return;

	}

	private void renderSelectionBorder(Graphics graphics, 
			boolean isActiveSelection){

		int imageSize = BACKGROUND_IMAGE.getHeight();
		float oldWidth =  graphics.getLineWidth();
		
		int borderThickness = 2;
		int rectSize = imageSize - 2*borderThickness;
		//The line width and rect drawing positions are chosen so different rects
		// dont overlap
		graphics.setLineWidth(borderThickness);
		if (isActiveSelection){	
			graphics.setColor(Color.red);
			
		}else{ 
			graphics.setColor(Color.white);}		
		graphics.drawRect(xPos+borderThickness, yPos+borderThickness, rectSize,rectSize  );
		graphics.setLineWidth(oldWidth);
	}



}
