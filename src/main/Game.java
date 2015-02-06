package main;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.newdawn.slick.*;
import org.newdawn.slick.gui.TextField;
import org.xml.sax.SAXException;

import menus.MainMenu;
import menus.Menu;
import menus.MenuHandler;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

import org.newdawn.slick.*;
import org.newdawn.slick.gui.TextField;

import world.Level;
import world.World;
import controls.GameControls;
import controls.Joystick;
import controls.WiimoteJoysticks;
import actors.Player;
import items.ItemBuilder;
import items.ItemParser;

public class Game extends BasicGame {

	static int LOAD_STATE = 0;
	static int LEVEL_STATE = 1;
	static int PAUSE_STATE = 2;

	static int width = 640;
	static int height = 480;
	static boolean fullscreen = false;
	static boolean showFPS = true;
	static String title = "Dvir is the woooorst.";
	static int fpslimit = 59;

//	private Player terri;
//	private Level level;
//	private int currentLevel = 0;

	private int gameState = LOAD_STATE;
	private TextField inputText;
	private GameControls controls;
//	private ItemBuilder itemBuilder;
	private GameControls gameControls;
	private MenuHandler menuHandler;
	private WiimoteJoysticks jsticks;

	private World world;
	

	public Game() {
		super("Israel, o Israel, oOOooOOo Israel");

	}

	
	@Override
	public void update(GameContainer gc, int t) throws SlickException {
		
		menuHandler.update();
		if(menuHandler.isQuitting()){gc.exit();}
		
		if (menuHandler.isMenuActive()){
			gameState = PAUSE_STATE;
		}
			
		
		if (gameState == LOAD_STATE){
			////The following should be returned for a load screen
			//			if (gc.getInput().isKeyPressed(Input.KEY_ENTER)){
			//				gameState = LEVEL_STATE;
			//				currentLevel = Integer.parseInt(inputText.getText());
			//				initializeLevel(currentLevel);
			//			}
			gameState = LEVEL_STATE;
//			currentLevel = 10;
//			initializeLevel(currentLevel);

		}

		if (gameState == LEVEL_STATE){

			
			
			
			int mouseX = gc.getInput().getMouseX()+world.getMapX();
			int mouseY = gc.getInput().getMouseY()+world.getMapY();
			
			gameControls.setMousePosition(mouseX,mouseY);
			
			world.update();

		}


		if ( gameState == PAUSE_STATE){
			if (!menuHandler.isMenuActive()){
				gameState = LEVEL_STATE;
			}
		}
		
		

	}

	@Override
	public void init(GameContainer gc) throws SlickException {

		gameControls = new GameControls(gc);

		inputText = new TextField(gc, gc.getDefaultFont(), height/2, height/2, 100, 20);

		this.menuHandler = new MenuHandler();
		gameControls.addMenuInputProviderListener(menuHandler.getKeyboardListener());
		
		// initiate world
		try {
			world = new World();
		} catch (ParserConfigurationException | SAXException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		// add the player to the game management systems
		gameControls.addAvatarInputProviderListener(world.getPlayer().getListener());
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
		app.setDisplayMode(width, height, fullscreen);
		app.setSmoothDeltas(true);
		app.setTargetFrameRate(fpslimit);
		app.setShowFPS(showFPS);
		app.setAlwaysRender(true);
		app.start();

	}
	
	


}
