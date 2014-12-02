import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;

import javax.swing.JFrame;


public class Frame extends JFrame implements MouseWheelListener, MouseListener{
	
	double motion = 0;
	
	boolean mouseIsDown = false;
	int mouseDownX = 0;
	int mouseDownY = 0;
	
	int mouseScrollX = 0;
	int mouseScrollY = 0;
	//double motion_x = 0;
	//double motion_y = 0;
	
	GPanel panel;
	
	public Frame(){
		ArrayList<Star> stars = new ArrayList<Star>();
		for(int i = 0; i < 2000; i++){
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
		
			//zooming out
			if(e.getWheelRotation() < 0){
				motion -= panel.zoom/10;	
			}
			//zooming in
			else{
				motion += panel.zoom/10;
				
			}
			System.out.println("motion: " + motion);
			
			mouseScrollX = getMouseX();
			mouseScrollY = getMouseY();									
	    }

	public void updateMouseDrag(){
	    if(mouseIsDown){
	    	int x = getMouseX();
			int y = getMouseY();
			int mouseDiffX = (int)((x - mouseDownX)/panel.zoom);
			int mouseDiffY = (int)((y - mouseDownY)/panel.zoom);
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
		
		if((motion > 0.01 || motion < -0.01)){
			double zoomement = motion/20;

			//if((panel.zoom < 10 && motion > 0) || (panel.zoom > 0.1 && motion < 0)){
					panel.zoom += zoomement;				
				
				//if(motion > 0.01 || motion < -0.01){
					panel.zoom_x -= (zoomement*mouseScrollX)/(panel.zoom*panel.zoom);
					panel.zoom_y -= (zoomement*mouseScrollY)/(panel.zoom*panel.zoom);
				//}


			//}
	    			    	
			motion -= zoomement;
		}
		else{
			motion = 0;
		}	
		if(panel.zoom > 10){panel.zoom = 10;}
		if(panel.zoom < 0.1){panel.zoom = 0.1;}
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
		motion = 0;
		
		mouseIsDown = true;
		mouseDownX = arg0.getX()-8;
		mouseDownY = arg0.getY()-30;
		System.out.println("mouse down at: " + mouseDownX + ", " + mouseDownY);
		
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
