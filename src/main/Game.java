package main;

import org.newdawn.slick.*;
import org.newdawn.slick.gui.TextField;
import controls.GameControls;
import gameobjects.ProgressPoint;
import actors.Player;

// Figure out how PlayerGraphics.render is putting the hover flame in the
// right place... 

//   platforms that have a limited number of times you can be on them?

//  find a way to skip empty objects on .tmx

//  weird bug when you jump ether objects tend to lag behind in drawing?? 
//  maybe check the order things are drawn check int vs float

//  what happens when a timed door needs to come back and there is an ether object that was put in the way?

// refactor action engine a bit (w.r.t. jump timers, etc.)

//mick to do
//
//  put enemy move direction in status. make lemming behavior get move direction from status
//  add wait timer and multiple enemies to the spawnlocation

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
	private ProgressPoint progress;
	private int gameState = LOAD_STATE;
	private TextField inputText;
	private GameControls controls;




	public Game() {
		super("Mick is not a nice guy");
	}

	@Override
	public void update(GameContainer gc, int t) throws SlickException {
		if (gameState == LOAD_STATE){
			////The following should be returned for a load screen
			//			if (gc.getInput().isKeyPressed(Input.KEY_ENTER)){
			//				gameState = LEVEL_STATE;
			//				currentLevel = Integer.parseInt(inputText.getText());
			//				initializeLevel(currentLevel);
			//			}
			gameState = LEVEL_STATE;
			currentLevel = 0;
			initializeLevel(currentLevel);

		}

		if (gameState == LEVEL_STATE){

			int mouseX = gc.getInput().getMouseX()+level.getMapX();
			int mouseY = gc.getInput().getMouseY()+level.getMapY();
			controls.setMousePosition(mouseX,mouseY);

			


			terri.update();
			level.update();

			progress = level.getProgressPoint();

			if (terri.isDying()){
				initializeLevel(currentLevel);
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

		controls = new GameControls(gc);

		inputText = new TextField(gc, gc.getDefaultFont(), height/2, height/2, 100, 20);



	}

	private void initializeLevel(int levelNumber) throws SlickException{
		level = new Level(levelNumber);

		if(progress==null){
			progress = level.getProgressPoint();
		}

		level.setProgressPoint(progress);
		level.setMousePosition(controls.getMousePos());
		// i dont like this initialization
		CollisionHandler collisionHandler = level.getCollisionHandler();

		terri = new Player(level.getProgressX(),level.getProgressY(),collisionHandler, controls.getMousePos());


		//Keyboard stuff
		controls.addPlayerListener(terri.getListener());

	}





	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {

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

			level.draw(g,(int) terri.getX(),(int)terri.getY());
			terri.render(g, level.getMapX(),level.getMapY());
		}
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
