package main;



import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.newdawn.slick.*;
//import org.newdawn.slick.gui.TextField;
import org.xml.sax.SAXException;

import menus.MenuHandler;
import world.World;
import controls.GameControls;
//import controls.WiimoteJoysticks;

public class Game extends BasicGame {

	static int LOAD_STATE = 0;
	static int LEVEL_STATE = 1;
	static int PAUSE_STATE = 2;

	public final static int WIDTH = 1200;
	public final static int HEIGHT = 750;
	
	static boolean fullscreen = false;
	static boolean showFPS = true;
	static String title = "Dvir is the woooorst.";
	static int fpslimit = 59;

	private int gameState = LOAD_STATE;
	

	private GameControls gameControls;
	private MenuHandler menuHandler;

	private World world;
	

	public Game() {
		super("Israel, o Israel, oOOooOOo Israel");

	}

	
	@Override
	public void update(GameContainer gc, int t) throws SlickException {
		
		try {
			menuHandler.update();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(menuHandler.isQuitting()){gc.exit();}
		
		if (menuHandler.isMenuActive()){
			gameState = PAUSE_STATE;
		}
			
		
		if (gameState == LOAD_STATE){
			gameState = LEVEL_STATE;
		}

		if (gameState == LEVEL_STATE){
			
			int mouseX = gc.getInput().getMouseX();
			int mouseY = gc.getInput().getMouseY();
			gameControls.setMousePosition(mouseX,mouseY);
			
			try {
				world.update();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}


		if ( gameState == PAUSE_STATE){
			
			if (!menuHandler.isMenuActive()){
				gameState = LEVEL_STATE;
			}
		}
		
		if( world.gameOver()){
			newGame();
		}

	}

	@Override
	public void init(GameContainer gc) throws SlickException {

		gameControls = new GameControls(gc);

//		inputText = new TextField(gc, gc.getDefaultFont(), height/2, height/2, 100, 20);

		this.menuHandler = new MenuHandler();
		gameControls.addMenuInputProviderListener(menuHandler.getKeyboardListener());
		
		newGame();
		
	}

	private void newGame() throws SlickException{
		// initiate world
		try {
			world = new World(gameControls.getMouseScreenPosition());
		} catch (ParserConfigurationException | SAXException | IOException e1) {
			e1.printStackTrace();
		}
				
		// add the player to the game management systems
		gameControls.addAvatarInputProviderListener(world.getPlayer().getKeyboardListener());
		menuHandler.setPlayerInventory(world.getPlayer().getInventory());

	}
	
	@Override
	public void render(GameContainer gc, Graphics graphics) throws SlickException {


		
		
		if (gameState == LEVEL_STATE){

			world.render(graphics);
		}
		
		menuHandler.renderOpenMenus(graphics);
	}


	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new Game());
		app.setDisplayMode(WIDTH,HEIGHT, fullscreen);
		app.setSmoothDeltas(true);
		app.setTargetFrameRate(fpslimit);
		app.setShowFPS(showFPS);
		app.setAlwaysRender(true);
		app.start();

	}
	
	


}
