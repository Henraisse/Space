

import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;



public class GPanel extends JPanel{
	double zoom = 1;
	
	int counter = 0;
	private ImageIcon image;
	ArrayList<Star> stars;
	
	public GPanel(ArrayList<Star> stars){
		this.stars = stars;
		image = new ImageIcon("C:\\Users\\Henraisse\\Desktop\\Space\\space.png");

	}

	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		//g.drawImage(image.getImage(), 0, 0, null); // see javadoc for more info on the parameters   
		g.drawImage(image.getImage(),0,0,1900,1000, null);
		
		
		for(Star s: stars){
			
			s.drawStar(g, zoom);

		}
	}
	
	public void update(){
		
		setVisible(false);
		setVisible(true);
	}
	
	

	
//	@Override
//	public void mouseWheelMoved(MouseWheelEvent e) {
//		mouseScrolls++;
//		if(e.getWheelRotation() > 0){zoom *= 1.2;}
//		else if(e.getWheelRotation() <= 0){zoom *= 0.7;}
//		
//		
//	}
	
}
