import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;

import javax.swing.JFrame;


public class Frame extends JFrame implements MouseWheelListener, MouseListener{
	
	private static final double ZOOM_BOUNDARY = 0.01;

	double momentum = 0;
	
	boolean mouseIsDown = false;
	double mouseDownX = 0;
	double mouseDownY = 0;
	
	double mouseScrollX = 0;
	double mouseScrollY = 0;
	
	GPanel panel;
	
	public static int ZOOM_SPEED = 30;
	
	public Frame(){
		ArrayList<Star> stars = new ArrayList<Star>();
		for(int i = 0; i < 10000; i++){
			stars.add(new Star(i, ""));
		}
		
		setSize(1900, 1000);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
		panel = new GPanel(stars);
		add(panel);
		panel.setBackground(Color.BLACK);
		addMouseWheelListener(this);
		addMouseListener(this);
		
		
		while(panel.counter < 1000000){	
			update();			
		}
	}
	

	
	public void update(){
	    updateZoomScale();
	    updateMouseDrag();
		

		panel.counter++;	
		enforceBoundaries();
		panel.update();	
		

		
		sleep(20);
	}

	public void mouseWheelMoved(MouseWheelEvent e) {
		
			//zooming in
			if(e.getWheelRotation() < 0){
//					motion += panel.zoom/10;
				momentum += panel.zoom_scale/10;
			}
			//zooming out
			else{
				momentum -= panel.zoom_scale/10;
				//motion -= 10;
			}
			mouseScrollX = getMouseX();
			mouseScrollY = getMouseY();									
	    }

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
	
	public void enforceBoundaries(){
		if(panel.zoom_x > 0){
			panel.zoom_x = 0;
		}
		if(panel.zoom_y > 0){
			panel.zoom_y = 0;
		}

	}
	
	public void updateZoomScale(){		    

		if ((momentum > ZOOM_BOUNDARY || momentum < -ZOOM_BOUNDARY)) {
			
			double zoom_step = momentum / ZOOM_SPEED;

			panel.zoom_scale += zoom_step;				

			
			
			panel.zoom_x -= (zoom_step*mouseScrollX)/(panel.zoom_scale*panel.zoom_scale);
			panel.zoom_y -= (zoom_step*mouseScrollY)/(panel.zoom_scale*panel.zoom_scale);

			momentum -= zoom_step;
		}
		else{
			momentum = 0;
		}	
		if(panel.zoom_scale > 10){panel.zoom_scale = 10;}
		if(panel.zoom_scale < 0.1){panel.zoom_scale = 0.1;}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
						
		// centrera bilden där man klickat
		int x = panel.getGlobalX(arg0.getX());
		int y = panel.getGlobalY(arg0.getY());
		
		//System.out.println("mouse clicked at: " + x + ", " + y);
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent arg0) {
		momentum = 0;
		
		mouseIsDown = true;
		mouseDownX = arg0.getX()-8;
		mouseDownY = arg0.getY()-30;
		//System.out.println("mouse down at: " + mouseDownX + ", " + mouseDownY);
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		mouseIsDown = false;
	}

	public int getMouseX(){
		return (((MouseInfo.getPointerInfo().getLocation().x) - (getLocationOnScreen().x))-8);
	}
	public int getMouseY(){
		return (((MouseInfo.getPointerInfo().getLocation().y) - (getLocationOnScreen().y))-30);
	}

	
	public void sleep(int x){
		try {
		    Thread.sleep(x);                 //1000 milliseconds is one second.		    
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
	}
	
}
