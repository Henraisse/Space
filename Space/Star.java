package Space;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import GUI.GPanel;
import Static.Physics;
import Static.Position;
import Static.Static;
import Static.Temperature;


public class Star implements Serializable{
	
	public static double STAR_CENTER_SIZE_MULTIPLIER = 2.5;
	
	//GLOBAL VARIABLES
	public int age = 10000;
	public int magnitude = 7;
	
	public int star_id;		//stjärnans id
	public String nick;
	public boolean hasNickName;
	public double x;		//stjärnans globala positioner
	public double y;
	public double radius;		//stjärnans radie
	//public double mass;
	public Temperature temperature;
	public Galaxy galax;		//galaxen stjärnan finns i
	public ArrayList<Star> neighbors = new ArrayList<Star>();
	
	boolean flagged;
	boolean flag_for_removal;
	public double mass;



	
	/**
	 * Constructor. This is called when a new star object is created, and this creates a star, generate a random galaxy position,
	 * and also a random temperature and size.
	 * @param i
	 * @param n
	 * @param gax
	 */
	public Star(int i, String n, Galaxy gax){
		
		//assign direct input variables
		galax = gax;
		star_id = i;
		nick = "Adidas";
		radius = 4;
		mass = 330000;
		age = 50;
		
		//create a random generator, to randomize a position, temperatur and size
		Random rand = gax.randomGenerator;
		
		//generate specs
		temperature = setStarTemperature(rand);
		generateSize(rand);	
		age = rand.nextInt(10000);
		
		//System.out.println("Maximum distance: " + Physics.getMaxPlanetDistance(this));
		//System.out.println("Minimum distance: " + Physics.getMinPlanetDistance(this));
	}
	
	/**
	 * Generate a color for the star, based on a double corresponding to the desired intensity.
	 * This function is mainly used when drawing the star.
	 * @param a
	 * @return
	 */
	public Temperature setStarTemperature(Random rand){
		int i = rand.nextInt(21)+1;
		
		Temperature ret;
	
		//choose a color based on star Kelvin value
		switch (i) {
		case 1:  ret = Static.O5;	break;
		case 2:  ret = Static.B1;	break;
		case 3:  ret = Static.B3;	break;
		case 4:  ret = Static.B5;	break;
		case 5:  ret = Static.B8;	break;
		case 6:  ret = Static.A1;	break;
		case 7:  ret = Static.A3;	break;
		case 8:  ret = Static.A5;	break;
		case 9:  ret = Static.F0;	break;
		case 10:  ret = Static.F2;	break;
		case 11:  ret = Static.F5;	break;
		case 12:  ret = Static.F8;	break;
		case 13:  ret = Static.G2;	break;
		case 14:  ret = Static.G5;	break;
		case 15:  ret = Static.G8;	break;
		case 16:  ret = Static.K0;	break;
		case 17:  ret = Static.K4;	break;
		case 18:  ret = Static.K7;	break;
		case 19:  ret = Static.M2;	break;
		case 20:  ret = Static.M4;	break;	
		case 21:  ret = Static.M8; 	break;	
		default: ret = Static.M8; 	break;	
		}

		return ret;
	}

	/**
	 * Draws the star. Uses a Graphics object, a zoom scale, and the zoomed-in position(to determine if we zoomed in on the star or not)
	 * Draws the star only if it is within the picture, else it returns.
	 * @param g
	 * @param zoom
	 * @param zx
	 * @param zy
	 */
	public void drawStar(Graphics g, GPanel p){
		//calculate the total position
		double posX = x+p.zoom_x;
		double posY = y+p.zoom_y;
		
		//calculate the frame relative pixel position (in reference to the window)
		double factorX = posX*p.zoom_scale;
		double factorY = posY*p.zoom_scale;		

		
		//if outside the edges, do not paint the star - return instead
		if(factorX < -60 || factorX > 2000 || factorY < -60 || factorY > 1200){			
			return;
		}
		
		
		//calculate the size when zoomed-in
		double sizefactor = radius*p.zoom_scale;

		drawStarDot(g, p.zoom_scale, sizefactor, posX, posY);
	}

	
	
	public void drawStarCenter(Graphics g, double zoom, boolean printText){		
		double projectedRadius = (radius*zoom*STAR_CENTER_SIZE_MULTIPLIER);
		if(projectedRadius < 1){projectedRadius = 1;}
		drawStarDisplay(g, projectedRadius, Static.starCenterPos);
		
		
		int fontSize = 20;
		g.setFont(new Font(Static.DISTANCE_RULER_FONT, Font.PLAIN, 16));
		
		if(printText){
			g.drawString("Temp: " + temperature.kelvin + " K", (int)(Static.starCenterPos.x + projectedRadius), (int)(Static.starCenterPos.y + projectedRadius));
		}
		
		g.setColor(Color.gray);
		//g.drawOval((int)(Static.starPos.x-(150.0*zoom)), (int)(Static.starPos.y-(150.0*zoom)), (int)(2*(150.0*zoom)), (int)(2*(150.0*zoom)));
		//drawStarDotCenter(g, Static.DRAW_STAR_RADIUS_CENTER);
	}
	
	
	public void drawStarDotCenter(Graphics g, double radius){	
		drawStarDisplay(g, radius, Static.starCenterPos);
		g.setColor(Color.gray);
	}
	
	public void drawStarDisplay(Graphics g, double radius, Position pos){	
		//calculates the on-screen relative position.
		Color c1 = temperature.color;		
		
		Color c2 = Color.WHITE;  
		Color[] colors = {c2,c1};
		float[] fractions ={0.0f, 1.0f};
		Graphics2D g2 = (Graphics2D) g;
		
		Paint p;
		p= new RadialGradientPaint((float)pos.x, (float)pos.y, (float)radius, fractions, colors);

		g2.setPaint(p);	
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.fillOval((int)(pos.x-radius), (int)(pos.y-radius), (int)(2*radius), (int)(2*radius));
		g2.drawImage(Static.halo.getImage(),(int)(pos.x-radius*2.5), (int)(pos.y-radius*2.5), (int)(2*radius*2.5), (int)(2*radius*2.5), null);
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
	}
	
	
	
	/**
	 * Draws a dot corresponding to the current star.
	 * @param g
	 * @param currentZoom
	 * @param size
	 * @param x
	 * @param y
	 */
	public void drawStarDot(Graphics g, double currentZoom, double size, double x, double y){	
		//calculates the on-screen relative position.
		int posX = (int)(x*currentZoom);
		int posY = (int)(y*currentZoom);
		Color c = temperature.color;
		
		//Before painting, make sure the light isn't too weak, or else don't paint
		if(c.getRed() > 50 || c.getGreen() > 50 || c.getBlue() > 50){
			
			g.setColor(c);
			if(flagged){
				g.setColor(Color.red);
			}
			g.drawLine(posX , posY, posX , posY);
			
			
		}
	}

	/**
	 * Generates a random size for the star.
	 * Size's unit is in 100,000 KM
	 * @param rand
	 */
	public void generateSize(Random rand){
		//randomize size class
		int sizeClass = rand.nextInt(100)+1;
		
		if(sizeClass <= 80){
			radius = rand.nextInt(4)+4;
		}
		else if(sizeClass > 80 && sizeClass < 95){
			radius = rand.nextInt(7)+8;
		}
		else{
			radius = rand.nextInt(10)+15;
		}
	}
	
	
	
	

}











