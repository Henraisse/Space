import java.awt.Color;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;

import javax.swing.JFrame;


public class Frame extends JFrame implements MouseWheelListener{
	int rolls = 0;
	double motion = 0;
	
	GPanel panel;
	
	public Frame(){
		ArrayList<Star> stars = new ArrayList<Star>();
		for(int i = 0; i < 20000; i++){
			stars.add(new Star(i, ""));
		}
		
		setSize(1900, 1000);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
		panel = new GPanel(stars);
		add(panel);
		panel.setBackground(Color.BLACK);
		addMouseWheelListener(this);
		
		while(panel.counter < 1000000){	
			update();			
		}
	}
	
	
	
	public void update(){
		panel.counter++;			
		panel.update();	
		

		
		try {
		    Thread.sleep(10);                 //1000 milliseconds is one second.
		    
		    if((motion >= 0.001 || motion < 0.001)){
		    	double zoomement = motion/20;
		    	if((panel.zoom < 10 && motion > 0) || (panel.zoom > 0.1 && motion < 0)){
		    		panel.zoom += zoomement;
		    	}
		    	else{
		    		zoomement = 0;
		    		motion = 0;
		    	}
		    	
		    	
		    	motion -= zoomement;
		    }
		    
		    
		    
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}		
	}
	
	
	public void mouseWheelMoved(MouseWheelEvent e) {
			if(e.getWheelRotation() < 0){
				motion -= panel.zoom/10;
			}
			else{
				motion += panel.zoom/10;
			}
		
	       
	       
	       //System.out.println(rolls);
	       //panel.zoom = rolls*0.1;
	       //panel.update();
	    }
	
	
}
