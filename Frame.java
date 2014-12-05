import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;

import javax.swing.JFrame;

/**
 * Frame class is used mainly for user I/O, like zoom, scroll, and general processes concerning the GUI.
 * Also contains a call to Star and Galaxy updates.
 * @author Henraisse
 *
 */
public class Frame extends JFrame implements MouseWheelListener, MouseListener{
	
	private static final double ZOOM_SPEED_BOUNDARY = 0.01;
	private static final double ZOOM_SCALE_MAX = 10000000;
	static final double ZOOM_SCALE_MIN = 0.1;
	Galaxy gax;
	double momentum = 0;
	
	boolean mouseIsDown = false;
	double mouseDownX = 0;
	double mouseDownY = 0;
	
	double mouseScrollX = 0;
	double mouseScrollY = 0;
	
	GPanel panel;
	
	public static int ZOOM_SPEED = 30;
	
	public Frame(){		
		gax = new Galaxy(150000, 8000, 4800, 4000);	
		setUpWindow();
		while(true){	
			update();			
		}
	}
	
	/**
	 * creates the new Galaxy window.
	 */
	public void setUpWindow(){
		//sets up the frame to correct dimensions, make it visible, and shuts the program if window is closed
		setSize(1900, 1000);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		
		//creates a new panel and adds it to the frame
		panel = new GPanel(gax);
		add(panel);
		
		//Makes the Frame listen to the mouse for input
		addMouseWheelListener(this);
		addMouseListener(this);
		
	}
	
	/**
	 * Updates the Frame and screen every 20 milliseconds
	 */
	public void update(){
		updateZoomScale();
	    updateMouseDrag();		
		enforceBoundaries();
		panel.update();				
		sleep(20);
	}

	/**
	 * Called every time the mouse wheel is rolled.
	 */
	public void mouseWheelMoved(MouseWheelEvent e) {
		
			//if zooming in, add some momentum to the zooming
			if(e.getWheelRotation() < 0){
				momentum += panel.zoom_scale/10;
			}
			//if zooming out, add some momentum to the zooming
			else{
				momentum -= panel.zoom_scale/10;
			}
			//save the cursor position 
			mouseScrollX = getMouseX();
			mouseScrollY = getMouseY();									
	    }

	/**
	 * If the mouse is down and dragged, we want to move the screen
	 */
	public void updateMouseDrag(){
	    if(mouseIsDown){
	    	double x = getMouseX();
			double y = getMouseY();
			double mouseDiffX = ((x - mouseDownX)/panel.zoom_scale);
			double mouseDiffY = ((y - mouseDownY)/panel.zoom_scale);
			mouseDownX = x;
			mouseDownY = y;
							
			panel.zoom_x += mouseDiffX;
			panel.zoom_y += mouseDiffY;
	    }
	}
	
	/**
	 * Do not let the current galaxy view gallop away into infinity due to abnormal zooming. Enforces boundaries.
	 */
	public void enforceBoundaries(){
		if(panel.zoom_x > 0){
			panel.zoom_x = 0;
		}
		if(panel.zoom_y > 0){
			panel.zoom_y = 0;
		}

	}
	
	/**
	 * If we have some Zoom momentum, continue the zoom
	 */
	public void updateZoomScale(){		    
		//as long as we have a momentum worth mentioning, zoom, otherwise kill the zoom
		if ((momentum > ZOOM_SPEED_BOUNDARY || momentum < -ZOOM_SPEED_BOUNDARY)) {
			
			//take a piece from momentum
			double zoom_step = momentum / ZOOM_SPEED;
			momentum -= zoom_step;
			
			//calculate steps
			panel.zoom_scale += zoom_step;										
			panel.zoom_x -= (zoom_step*mouseScrollX)/(panel.zoom_scale*panel.zoom_scale);
			panel.zoom_y -= (zoom_step*mouseScrollY)/(panel.zoom_scale*panel.zoom_scale);			
		}
		else{
			momentum = 0;
		}	
		//make sure we do not zoom in or out too much
		if(panel.zoom_scale > ZOOM_SCALE_MAX){panel.zoom_scale = ZOOM_SCALE_MAX;momentum = 0;}
		if(panel.zoom_scale < ZOOM_SCALE_MIN){panel.zoom_scale = ZOOM_SCALE_MIN;momentum = 0;}
	}

	/**
	 * If mouse is clicked. Currently unused.
	 */
	public void mouseClicked(MouseEvent arg0) {						
		// centrera bilden där man klickat
		int x = panel.getGlobalX(arg0.getX());
		int y = panel.getGlobalY(arg0.getY());		
	}

	/**
	 * If mouse buttons are pressed.
	 */
	public void mousePressed(MouseEvent arg0) {
		//Kill of momentum
		momentum = 0;
		
		//flag that mouse is down, and record which position we clicked at
		mouseIsDown = true;
		mouseDownX = arg0.getX()-8;	//8 and 30 are to eliminate frame border from coordinates
		mouseDownY = arg0.getY()-30;		
	}

	/**
	 * If mouse buttons are released
	 */
	public void mouseReleased(MouseEvent arg0) {
		//flag that we just released mouse buttons
		mouseIsDown = false;
	}

	public int getMouseX(){
		return (((MouseInfo.getPointerInfo().getLocation().x) - (getLocationOnScreen().x))-8);
	}
	public int getMouseY(){
		return (((MouseInfo.getPointerInfo().getLocation().y) - (getLocationOnScreen().y))-30);
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
	
}
