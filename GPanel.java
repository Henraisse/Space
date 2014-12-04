

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
		this.galax = gax;
		this.stars = gax.stars;

		image = new ImageIcon("space.png");
	}
	

		
	/**
	 * Draws everything.
	 */
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(image.getImage(),0,0,1900,1000, null);

		for(Star s: stars){			
			s.drawStar(g, zoom_scale, zoom_x, zoom_y);
		}
	}
	
	/**
	 * Initiates the redraw of everything.
	 */
	public void update(){		
		setVisible(false);
		setVisible(true);
	}
	
	/**
	 * Returns the "Space"-coordinate x
	 * @param x
	 * @return
	 */
	public int getGlobalX(int x){
		return (int)(((x-8)/zoom_scale)+zoom_x);
	}
	
	/**
	 * Returns the "Space"-coordinate y
	 * @param y
	 * @return
	 */
	public int getGlobalY(int y){
		return (int)(((y-30)/zoom_scale)+zoom_y);
	}
	
	
	

	
	
	
}
