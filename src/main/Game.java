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

	private Player terri;
	private Level level;
	private int currentLevel = 0;

	private int gameState = LOAD_STATE;
	private TextField inputText;
	private GameControls controls;
	private ItemBuilder itemBuilder;
	private GameControls gameControls;
	private MenuHandler menuHandler;
	private WiimoteJoysticks jsticks;


	public Game() {
		super("Israel, o Israel, oOOooOOo Israel");

	}

	@Override
	public void update(GameContainer gc, int t) throws SlickException {
		
		menuHandler.update();
		
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
			currentLevel = 10;
			initializeLevel(currentLevel);

		}

		if (gameState == LEVEL_STATE){

			int mouseX = gc.getInput().getMouseX()+level.getMapX();
			int mouseY = gc.getInput().getMouseY()+level.getMapY();
			gameControls.setMousePosition(mouseX,mouseY);




			terri.update();
			level.update();

//			progress = level.getProgressPoint();

			if (terri.isDying()){
				initializeLevel(currentLevel);
			}

		}


		if ( gameState == PAUSE_STATE){
			if (!menuHandler.isMenuActive()){
				gameState = LEVEL_STATE;
			}
		}
		
		if ( gc.getInput().isKeyPressed(Input.KEY_P)){

			if ( gameState == LEVEL_STATE){
				gameState = PAUSE_STATE;
			}
			else if ( gameState == PAUSE_STATE){
				gameState = LEVEL_STATE;
			}
		}

		if( gc.getInput().isKeyPressed(Input.KEY_ESCAPE)){gc.exit();}
	}

	@Override
	public void init(GameContainer gc) throws SlickException {

		gameControls = new GameControls(gc);

		inputText = new TextField(gc, gc.getDefaultFont(), height/2, height/2, 100, 20);

		this.menuHandler = new MenuHandler();
		gameControls.addMenuInputProviderListener(menuHandler.getListener());
		
		// load the times from file dunno why the try catchs are required
		try {
			loadItemsFromFile();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

	private void initializeLevel(int levelNumber) throws SlickException{
		level = new Level(levelNumber, itemBuilder);

		level.setMousePosition(gameControls.getMousePos());
		// i dont like this initialization
		CollisionHandler collisionHandler = level.getCollisionHandler();

		terri = new Player(100,800,collisionHandler, gameControls.getMousePos());


		gameControls.addAvatarInputProviderListener(terri.getListener());

	

	}

//	private void initializeLevel(int levelNumber) throws SlickException{
//		level = new Level(levelNumber,itemBuilder);
//
//		level.setMousePosition(gameControls.getMousePos());
//		// i dont like this initialization
//		CollisionHandler collisionHandler = level.getCollisionHandler();
//
//		terri = new Player(100,850,collisionHandler, gameControls.getMousePos());
//
//
//		//Keyboard stuff
//		gameControls.addAvatarInputProviderListener(terri.getListener());
//
//	}

	private void loadItemsFromFile() throws FileNotFoundException, ParserConfigurationException, SAXException, IOException, SlickException{

		ItemParser parser = new ItemParser("items/items.xml");
		
		this.itemBuilder = new ItemBuilder(parser.getItemMaps(),"data/items.png");

	}

	@Override
	public void render(GameContainer gc, Graphics graphics) throws SlickException {

//		System.out.println("Left joystick: "+  jsticks.LeftXValue()+"," +jsticks.LeftYValue() );
//		System.out.println("Right joystick: "+  jsticks.RightXValue()+"," +jsticks.RightYValue() );
		
		//To be added for load screen
		//		if (gameState == LOAD_STATE){
		//			g.drawString("Enter Level: " , height/2, height/2-20);
		//			g.drawString("Press Enter to start", height/2, height*3/4);
		//			
		//			inputText.render(gc, g);
		//		}

		
		
		if (gameState == LEVEL_STATE){
			int mouseX = gc.getInput().getMouseX();
			int mouseY = gc.getInput().getMouseY();

			level.draw(graphics,(int) terri.getX(),(int)terri.getY());
			terri.render(graphics, level.getMapX(),level.getMapY());
		}
		
		menuHandler.renderActiveMenus(graphics);
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
