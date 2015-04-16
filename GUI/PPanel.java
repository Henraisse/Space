package GUI;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import Space.Galaxy;
import Space.Planet;
import Static.Game;

public class PPanel extends JPanel{

	Planet currentPlanet;
	public ImageIcon image;
	
	public PPanel(Frame f, Galaxy gax, Game g){
		image = new ImageIcon("space.png");
	}
	
	
	public void viewPlanet(Planet p){
		
	}
	
	
	/**
	 * Draws everything.
	 */
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(image.getImage(),0,0,2000,1200, null);
	}
	
	
}
