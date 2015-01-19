package GUI;


import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;




import Space.Galaxy;
import Space.Star;
import Static.Static;

/**
 * This class represents the GUI of this program.
 * @author Henraisse
 *
 */
public class GPanel extends JPanel{
	public Frame frame;
	
	public double zoom_scale = Frame.ZOOM_SCALE_MIN_GP;
	public double zoom_x = 0;
	public double zoom_y = 0;
	
	public double zoom_ruler_distance = 0;
	
	public int xtest = 0;
	public int ytest = 0;
	
	public int counter = 0;
	public ImageIcon image;
	public ArrayList<Star> stars;
	public Galaxy galax;
	
	Menu menu = new Menu(this, 1410,0,1900,1200);

	public boolean active = true;
	
	public GPanel(Frame f, Galaxy gax){
		frame = f;
		setupMenu();
		setBackground(Color.BLACK);
		
		
		this.galax = gax;
		this.stars = gax.stars;
		

		image = new ImageIcon("space.png");
	}
	


	public void setupMenu(){
		menu.addButton(20, 142, 132, 50, Static.SELECT_BUTTON_TEXT, Static.ruler_font18);
		menu.addButton(20, 197, 132, 50, Static.NAME_BUTTON_TEXT, Static.ruler_font18);
		menu.addButton(20, 252, 132, 50, Static.EXAMINE_BUTTON_TEXT, Static.ruler_font18);
		menu.addDisplay();
		menu.addLabel(Static.STAR_SPECS_LABEL);
	}
		
	/**
	 * Draws everything.
	 */
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		
		//draws the background
		g.drawImage(image.getImage(),0,0,2000,1200, null);
		

		//Draws all the galaxy's stars
		for(Star s: stars){			
			s.drawStar(g, this);
		}
		
		//galax.center.paintBlackHole(g, this);
		
		if(galax.selected_star != null){
			
			drawStarSelection(g);
			//g.setColor(Color.red);
			//g.fillOval(xtest-10, ytest-10, 20, 20);

		}
		menu.paint(g);
		drawSpaceRuler(g);
	}
	
	
	public void drawStarSelection(Graphics g){
		g.setColor(Color.red);
		((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.drawOval(getLocalX(galax.selected_star.x)-5, getLocalY(galax.selected_star.y)-5, 10, 10);
		g.setColor(Color.GREEN);
		g.drawOval(getLocalX(galax.selected_star.x)-6, getLocalY(galax.selected_star.y)-6, 12, 12);
		g.setColor(Color.ORANGE);
		g.drawOval(getLocalX(galax.selected_star.x)-7, getLocalY(galax.selected_star.y)-7, 14, 14);
		//g.drawOval(getLocalX(galax.selected_star.x)-5, getLocalY(galax.selected_star.y)-5, 10, 10);
		
		((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
	}
	

	public void drawSpaceRuler(Graphics g){
		int p0x = 30;
		int p0y = 1000;
		
		int length = 300;
		int height = 30;
		
		int offset0 = 2;
		int line0 = 7;
		
		
		String text;
		if(length/zoom_scale >= 1){
			double d = (length/zoom_scale)*10;
			int distance = (int) d;			
			text = new Double((double)distance/10).toString() + Static.DISTANCE_UNIT01;
		}
		else{
			double d = (9460730.4725808)*length/zoom_scale;
			text = new Integer((int)d).toString() + Static.DISTANCE_UNIT02;
		}
				
		int textOffset = 23;
		((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(Color.red);
		g.drawLine(p0x, p0y, p0x, p0y+height);
		
		g.drawLine(p0x-offset0, p0y, p0x-offset0, p0y+line0);
		g.drawLine(p0x-offset0, p0y+height, p0x-offset0, p0y+height+line0);
		g.drawLine(p0x+length+offset0, p0y, p0x+length+offset0, p0y+line0);
		g.drawLine(p0x+length+offset0, p0y+height, p0x+length+offset0, p0y+height+line0);
		
		g.drawLine(p0x, p0y+height, p0x+length, p0y+height);
		g.drawLine(p0x, p0y+height+offset0, p0x+length, p0y+height+offset0);
		g.drawLine(p0x, p0y+height+offset0*2, p0x+length, p0y+height+offset0*2);
		g.drawLine(p0x+length, p0y+height, p0x+length, p0y);	
		
		g.setFont(Static.ruler_font24);
		g.drawString(text, (int)((p0x+offset0)), (int)((p0y+textOffset)));
		((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
	}
	
	public int getLocalX(double x){
		return (int) ((zoom_x+x)*zoom_scale);
	}
	
	public int getLocalY(double y){
		return (int) ((zoom_y+y)*zoom_scale);
	}
	

	/**
	 * Returns the "Space"-coordinate x
	 * @param x
	 * @return
	 */
	public double getGlobalX(int x){
		//inparameter x är den positionen man klickade på
		return (x/zoom_scale)-zoom_x;
	}
	
	/**
	 * Returns the "Space"-coordinate y
	 * @param y
	 * @return
	 */
	public double getGlobalY(int y){
		return (y/zoom_scale)-zoom_y;
	}
	
	
	

	
	
	
}
