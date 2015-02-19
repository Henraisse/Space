package GUI;
//import gui.Fullscreen_Window;

import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.MouseInfo;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JFrame;

import Space.Galaxy;
import Space.Planet;
import Space.Star;
import Static.Game;
import Static.Static;

/**
 * Frame class is used mainly for user I/O, like zoom, scroll, and general processes concerning the GUI.
 * Also contains a call to Star and Galaxy updates.
 * @author Henraisse
 *
 */
public class Frame extends JFrame implements MouseWheelListener, MouseListener, KeyListener{
	
	private static final double ZOOM_SPEED_BOUNDARY = 0.01;
	private static final double ZOOM_SCALE_MAX_GP = 10000000;
	static final double ZOOM_SCALE_MIN_GP = 0.1;
	private static final double ZOOM_SCALE_MAX_SP = 10;
	static final double ZOOM_SCALE_MIN_SP = 0.1;
	Galaxy gax;
	Game game;
	double momentum = 0;
	
	boolean mouseIsDown = false;
	
	double mouseDownX = 0;
	double mouseDownY = 0;
	
	double mouseScrollX = 0;
	double mouseScrollY = 0;
	
	GPanel gpanel;
	SPanel spanel;
	
    public GraphicsDevice device;
    public DisplayMode originalDM;

	GraphicsEnvironment env;
	GraphicsDevice[] devices;
	
	boolean terminating = false;
	private double warp = 1.0;
	public static int ZOOM_SPEED = 30;
	
	public Frame(Game g){		
		super(GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0].getDefaultConfiguration()); 
		
		
		gax = new Galaxy(150000, 6900, 5200, 4000, 1000);	
		
		game = g;
		game.setTest(gax);
		
		setUpWindow();
		while(!terminating){	
			update();			
		}
	}
	

	
	/**
	 * creates the new Galaxy window.
	 */
	public void setUpWindow(){

    	env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        devices = env.getScreenDevices();
        device = devices[0];
        setUndecorated(true);
        device.setFullScreenWindow(this);
        setVisible(true);
			//sets up the frame to correct dimensions, make it visible, and shuts the program if window is closed
//		setSize(1900, 1000);
//		setVisible(true);
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		
		//creates a new panel and adds it to the frame
		gpanel = new GPanel(this, gax, game);
		add(gpanel);

		spanel = new SPanel(this, gax, game);

		
		//Makes the Frame listen to the mouse for input
		addMouseWheelListener(this);
		addMouseListener(this);
		addKeyListener(this);
		
	}
	
	/**
	 * Updates the Frame and screen every 20 milliseconds
	 */
	public void update(){
		if(spanel.active){
			spanel.Update();
		}
		
		
		getContentPane().validate();
		getContentPane().repaint();
		
		if(gpanel.active){
			updateZoomScaleGP();
		}
		else{
			updateZoomScaleSP();
		}
		
	    updateMouseDrag();		
		enforceBoundaries();
		
		
				
		sleep(20);
	}

	/**
	 * Called every time the mouse wheel is rolled.
	 */
	public void mouseWheelMoved(MouseWheelEvent e) {
		if(!gpanel.menu.withinBounds(e.getX(),e.getY())){
		
			//if zooming in, add some momentum to the zooming
			if(e.getWheelRotation() < 0){
				if(gpanel.active){
					momentum += gpanel.zoom_scale/10;
				}
				else if(spanel.active){
					momentum += spanel.zoom_scale/10;
				}
				
			}
			//if zooming out, add some momentum to the zooming
			else{
				if(gpanel.active){
					momentum -= gpanel.zoom_scale/10;
				}
				else if(spanel.active){
					momentum -= spanel.zoom_scale/10;
				}
			}
			//save the cursor position 
			mouseScrollX = getMouseX();
			mouseScrollY = getMouseY();									
		}
	}

	/**
	 * If the mouse is down and dragged, we want to move the screen
	 */
	public void updateMouseDrag(){
	    if(mouseIsDown){
	    	double x = getMouseX();
			double y = getMouseY();
			double mouseDiffX = ((x - mouseDownX)/gpanel.zoom_scale);
			double mouseDiffY = ((y - mouseDownY)/gpanel.zoom_scale);
			mouseDownX = x;
			mouseDownY = y;
							
			gpanel.zoom_x += mouseDiffX;
			gpanel.zoom_y += mouseDiffY;
	    }
	}
	
	/**
	 * Do not let the current galaxy view gallop away into infinity due to abnormal zooming. Enforces boundaries.
	 */
	public void enforceBoundaries(){
		if(gpanel.zoom_x > 0){
			gpanel.zoom_x = 0;
		}
		if(gpanel.zoom_y > 0){
			gpanel.zoom_y = 0;
		}

		if(gpanel.zoom_x  < -(15000 - 1500/gpanel.zoom_scale)){
			gpanel.zoom_x = -(15000 - 1500/gpanel.zoom_scale);
		}
		if(gpanel.zoom_y  < -(11000 - 1100/gpanel.zoom_scale)){
			gpanel.zoom_y = -(11000 - 1100/gpanel.zoom_scale);
		}		

	}
	
	/**
	 * If we have some Zoom momentum, continue the zoom
	 */
	public void updateZoomScaleGP(){		    
		//as long as we have a momentum worth mentioning, zoom, otherwise kill the zoom
		if ((momentum > ZOOM_SPEED_BOUNDARY || momentum < -ZOOM_SPEED_BOUNDARY)) {
			double zoom_step = 0;
			//take a piece from momentum
			if(momentum > 0){
				zoom_step = momentum / ZOOM_SPEED;
			}
			else{
				if(momentum/gpanel.zoom_scale < -1){
					momentum = -1*gpanel.zoom_scale;
				}
				
				zoom_step = momentum / (ZOOM_SPEED);
			}
			momentum -= zoom_step;
			
			//calculate steps
			gpanel.zoom_scale += zoom_step;										
			gpanel.zoom_x -= (zoom_step*mouseScrollX)/(gpanel.zoom_scale*gpanel.zoom_scale);
			gpanel.zoom_y -= (zoom_step*mouseScrollY)/(gpanel.zoom_scale*gpanel.zoom_scale);			
		}
		else{
			momentum = 0;
		}	
		//make sure we do not zoom in or out too much
		if(gpanel.zoom_scale > ZOOM_SCALE_MAX_GP){gpanel.zoom_scale = ZOOM_SCALE_MAX_GP;momentum = 0;}
		if(gpanel.zoom_scale < ZOOM_SCALE_MIN_GP){gpanel.zoom_scale = ZOOM_SCALE_MIN_GP;momentum = 0;}
	}
	
	public void updateZoomScaleSP(){		    
		//as long as we have a momentum worth mentioning, zoom, otherwise kill the zoom
		if ((momentum > ZOOM_SPEED_BOUNDARY || momentum < -ZOOM_SPEED_BOUNDARY)) {
			double zoom_step = 0;
			//take a piece from momentum
			if(momentum > 0){
				zoom_step = momentum / ZOOM_SPEED;
			}
			else{
				if(momentum/gpanel.zoom_scale < -1){
					momentum = -1*spanel.zoom_scale;
				}
				
				zoom_step = momentum / (ZOOM_SPEED);
			}
			momentum -= zoom_step;
			
			//calculate steps
			spanel.zoom_scale += zoom_step;										
//			spanel.zoom_x -= (zoom_step*mouseScrollX)/(spanel.zoom_scale*spanel.zoom_scale);
//			spanel.zoom_y -= (zoom_step*mouseScrollY)/(spanel.zoom_scale*spanel.zoom_scale);			
		}
		else{
			momentum = 0;
		}	
		//make sure we do not zoom in or out too much
		if(spanel.zoom_scale > ZOOM_SCALE_MAX_SP){spanel.zoom_scale = ZOOM_SCALE_MAX_SP;momentum = 0;}
		if(spanel.zoom_scale < ZOOM_SCALE_MIN_SP){spanel.zoom_scale = ZOOM_SCALE_MIN_SP;momentum = 0;}
	}

	/**
	 * If mouse is clicked.
	 */
	public void mouseClicked(MouseEvent arg0) {			
		if(!gpanel.menu.withinBounds(arg0.getX(),arg0.getY())){
		// centrera bilden där man klickat
		double x = gpanel.getGlobalX(arg0.getX());
		double y = gpanel.getGlobalY(arg0.getY());	

		int id = gax.getStarId(x, y);
		Star s = gax.stars.get(id);
		
		gpanel.menu.currentStarName = Static.getStarName(s);
		
		Static.flagSector(gax, x, y);
		
		
		}

	}

	/**
	 * If mouse buttons are pressed.
	 */
	public void mousePressed(MouseEvent arg0) {

		if(!gpanel.menu.withinBounds(arg0.getX(),arg0.getY()) || !spanel.menu.withinBounds(arg0.getX(),arg0.getY())){
			//Kill of momentum
			momentum = 0;
			
			//flag that mouse is down, and record which position we clicked at
			mouseIsDown = true;
			mouseDownX = arg0.getX();	//8 and 30 are to eliminate frame border from coordinates
			mouseDownY = arg0.getY();	
		}
		else{
			if(gpanel.active){
				gpanel.menu.downAt(arg0.getX(), arg0.getY());
			}
			else if(spanel.active){
				spanel.menu.downAt(arg0.getX(), arg0.getY());
			}
		}
	}

	
	/**
	 * If mouse buttons are released
	 */
	public void mouseReleased(MouseEvent arg0) {
		spanel.menu.releasedAt(arg0.getX(), arg0.getY());
		gpanel.menu.releasedAt(arg0.getX(), arg0.getY());
		
		//flag that we just released mouse buttons
		mouseIsDown = false;
	}
	
	public int getMouseX(){
		return (((MouseInfo.getPointerInfo().getLocation().x) - (getLocationOnScreen().x)));
	}
	
	public int getMouseY(){
		return (((MouseInfo.getPointerInfo().getLocation().y) - (getLocationOnScreen().y)));
	}

	/**
	 * Sleeps the Frame x milliseconds.
	 * @param x
	 */
	public void sleep(int x){
		try {
		    Thread.sleep(x);                 //1000 milliseconds is one second.		    
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
	}
	
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}




	public void keyPressed(KeyEvent arg0) {
		if (arg0.getKeyCode() == KeyEvent.VK_ESCAPE) {
			if(spanel.active){
				spanel.currentStar = null;
				spanel.planets.clear();
				
				momentum = 0;
				spanel.active = false;
				gpanel.active = true;
				remove(spanel);
				add(gpanel);
			}
			else{
				System.exit(0);
			}
            
        }
		else if(arg0.getKeyCode() == KeyEvent.VK_UP){
			if(spanel.manual_orbits){
				spanel.rotationSpeed += 0.5;		
				
			}
			else{
				spanel.days+=2.0;
			}
			spanel.Update();
			//
			
		}
		else if(arg0.getKeyCode() == KeyEvent.VK_DOWN){
			if(spanel.manual_orbits){
				spanel.rotationSpeed -= 0.5;
			}
			else{
				if(spanel.days >= 2.0){
					spanel.days-=2.0;
				}
				
			}
			spanel.Update();
		}
		
		else if(arg0.getKeyCode() == KeyEvent.VK_W){
			double days0 = spanel.days;
			

			if(spanel.currentSpaceCraft.object.navComputer != null){
			spanel.currentSpaceCraft.object.navComputer.setBoost(spanel.days, warp, 'a');
			spanel.currentSpaceCraft.object.calculateTrajectoryArc();
			spanel.days = days0;
			}
		}
		else if(arg0.getKeyCode() == KeyEvent.VK_S){
			double days0 = spanel.days;
			if(spanel.currentSpaceCraft.object.navComputer != null){
			spanel.currentSpaceCraft.object.navComputer.setBoost(spanel.days, warp, 'b');
			spanel.currentSpaceCraft.object.calculateTrajectoryArc();
			spanel.days = days0;
			}
		}
		else if(arg0.getKeyCode() == KeyEvent.VK_A){
			double days0 = spanel.days;
			if(spanel.currentSpaceCraft.object.navComputer != null){
			spanel.currentSpaceCraft.object.navComputer.setBoost(spanel.days, warp, 'c');
			spanel.currentSpaceCraft.object.calculateTrajectoryArc();
			spanel.days = days0;
			}
		}
		else if(arg0.getKeyCode() == KeyEvent.VK_D){
			double days0 = spanel.days;
			if(spanel.currentSpaceCraft.object.navComputer != null){
			spanel.currentSpaceCraft.object.navComputer.setBoost(spanel.days, warp , 'd');
			spanel.currentSpaceCraft.object.calculateTrajectoryArc();
			spanel.days = days0;
			}
		}
		else if(arg0.getKeyCode() == KeyEvent.VK_1){
			double days0 = spanel.days;
			if(spanel.currentSpaceCraft.object.navComputer != null){
			spanel.currentSpaceCraft.object.navComputer.setBoost(spanel.days, warp , 'e');
			spanel.currentSpaceCraft.object.calculateTrajectoryArc();
			spanel.days = days0;
			}
		}
		else if(arg0.getKeyCode() == KeyEvent.VK_2){
			double days0 = spanel.days;
			if(spanel.currentSpaceCraft.object.navComputer != null){
			spanel.currentSpaceCraft.object.navComputer.setBoost(spanel.days, warp , 'f');
			spanel.currentSpaceCraft.object.calculateTrajectoryArc();
			spanel.days = days0;
			}
		}
		else if(spanel.active && arg0.getKeyCode() == KeyEvent.VK_C){
			double days0 = spanel.days;
			if(spanel.currentSpaceCraft.object.navComputer != null){
			spanel.currentSpaceCraft.object.navComputer.resetNavInstructions();
			spanel.currentSpaceCraft.object.calculateTrajectoryArc();
			spanel.days = days0;
			}
		}
		else if(spanel.active && arg0.getKeyCode() == KeyEvent.VK_T){
			spanel.days = 1;
			spanel.Update();
		}
		else if(arg0.getKeyCode() == KeyEvent.VK_U){
			spanel.currentSpaceCraft.object.orbits -= 1.0;
			System.out.println(spanel.currentSpaceCraft.object.orbits);
			spanel.Update();
			spanel.currentSpaceCraft.object.calculateTrajectoryArc();
			spanel.Update();
		}
		
		else if(arg0.getKeyCode() == KeyEvent.VK_H){
			spanel.currentSpaceCraft.object.jumpToOrbit();
			spanel.currentSpaceCraft.object.calculateTrajectoryArc();
			spanel.Update();
		}
		
		
		else if(arg0.getKeyCode() == KeyEvent.VK_P){
			if(warp == 1){
				warp = 100;
			}
			else if(warp == 100){
				warp = 10000;
			}
			else if(warp == 10000){
				warp = 1;
			}
			
			spanel.Update();
		}
		
		else if(spanel.active && arg0.getKeyCode() == KeyEvent.VK_Q){
			//System.out.print("Tab used. ");
			if(spanel.currentSpaceCraft != null && spanel.presentSpacecrafts.size() > 0){
				for(int i = 0; i < spanel.presentSpacecrafts.size(); i++){
					if(spanel.presentSpacecrafts.get(i).equals(spanel.currentSpaceCraft)){
						int currentIndex = i;
						
						int index = (currentIndex+1)%spanel.presentSpacecrafts.size();
						
						System.out.println("Current index is " + currentIndex + ", next index is " + index);
						spanel.currentSpaceCraft = spanel.presentSpacecrafts.get(index);	
						break;
					}
					
				}
			}
			
			else if(spanel.presentSpacecrafts.size() > 0){
				spanel.currentSpaceCraft = spanel.presentSpacecrafts.get(0);
			}
			//System.out.println(spanel.currentSpaceCraft.name + " Drone name.");
		}

		
	}




	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}




	public void keyTyped(KeyEvent arg0) {
     		
	}
	
}
