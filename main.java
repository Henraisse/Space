

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class main {

	public static void main(String[] args) {

		ArrayList<Star> stars = new ArrayList<Star>();
		
		JFrame frame = new JFrame();
		frame.setSize(1900, 1000);
		frame.setVisible(true);
		
		GPanel panel = new GPanel(stars);
		frame.add(panel);

		panel.setBackground(Color.BLACK);
		
		for(int i = 0; i < 1000; i++){
			stars.add(new Star());
		}

		
		while(panel.counter < 1000000){	
			
			panel.counter++;			
			panel.update();						
			try {
			    Thread.sleep(10);                 //1000 milliseconds is one second.
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
		}		
	}
}
