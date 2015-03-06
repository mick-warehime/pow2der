package render;

import org.newdawn.slick.Graphics;

public class TwoStateRenderer extends Renderer {
	
	private int whichState;
	
	private Renderer renderer1;
	private Renderer renderer2;
	

	public TwoStateRenderer( Renderer renderer1, Renderer renderer2){
		this.whichState = 1;
		this.renderer1 = renderer1;
		this.renderer2 = renderer2;
	}
	
	public void toggleState( ){
		whichState = 3-whichState;
	}
	
	@Override
	public void render(Graphics g, int offsetX, int offsetY) {
		
		assert whichState==1 || whichState == 2 : "Improper renderer state!";
		
		if (whichState ==1){
			renderer1.render(g, offsetX, offsetY);
		}else{
			renderer2.render(g, offsetX, offsetY);
		}
		

	}

}
