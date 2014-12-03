

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
	double zoom_scale = 0.1;
	double zoom_x = 0;
	double zoom_y = 0;
	
	int counter = 0;
	private ImageIcon image;
	ArrayList<Star> stars;
	Galaxy galax;
	
	public GPanel(Galaxy gax){
		galax = gax;
		this.stars = gax.stars;
		image = new ImageIcon("space.png");

	}

	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(image.getImage(),0,0,1900,1000, null);
		
		g.setColor(Color.red);
		g.drawOval((int)(zoom_x-5), (int)(zoom_y-5), 10, 10);

		for(Star s: stars){
			
			s.drawStar(g, zoom_scale, zoom_x, zoom_y);

		}
	}
	
	public void update(){
		
		setVisible(false);
		setVisible(true);
	}
	
	public int getGlobalX(int x){
		return (int)(((x-8)/zoom_scale)+zoom_x);
	}
	
	public int getGlobalY(int y){
		return (int)(((y-30)/zoom_scale)+zoom_y);
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
