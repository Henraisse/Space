import java.awt.Color;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;

import javax.swing.JFrame;


public class Frame extends JFrame implements MouseWheelListener{
	int rolls = 0;
	GPanel panel;
	
	public Frame(){
		ArrayList<Star> stars = new ArrayList<Star>();
		for(int i = 0; i < 300; i++){
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
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}		
	}
	
	
	public void mouseWheelMoved(MouseWheelEvent e) {
	       rolls++;
	       System.out.println(rolls);
	       panel.zoom = rolls*0.1;
	       panel.update();
	    }
	
	
}
