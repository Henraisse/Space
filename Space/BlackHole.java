package Space;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;

import GUI.GPanel;
import Static.Static;


public class BlackHole {
	double x, y;
	double mass;
	Galaxy galaxy;
	
	private static Robot rb;
	public static int SWALLOW_DISTANCE = 10;
	
	public BlackHole(int i, int x, int y, Galaxy g){

		mass = Math.sqrt(i);
		galaxy = g;
		
		this.x = x;
		this.y = y;
		
		swallowCloseStars();
	}
	
	public void paintBlackHole(Graphics g, GPanel p){
		int size = (int) (Math.sqrt(Math.sqrt(mass))*p.zoom_scale);
		int x = p.getLocalX(this.x);
		int y = p.getLocalY(this.y);
		g.setColor(Color.black);
		
		if(Math.sqrt(mass)*p.zoom_scale < 1){
			g.drawLine(x, y, x, y);
		}
		else{
			g.fillOval(x-(size/2),y-(size/2),size,size);
//			g.setColor(Color.white);
//			g.drawOval(x-((size/2)+1),y-((size/2)+1),size+2,size+2);
		}	
		
//		BufferedImage image = new BufferedImage(300, 300, BufferedImage.TYPE_4BYTE_ABGR);
//		Graphics2D g2 = image.createGraphics();
//		p.paint(g2);
//		PixelGrabber pg = new PixelGrabber(image, 300, 300, 300, 300, false);
//		pg.grabPixels();
//		pg.getColorModel()
		//image.getColorModel().getRGB(i, j);

		

	}
	

	
	
	
	
	
	
	public void swallowCloseStars(){
		for(Star s : galaxy.stars){
			double d = Static.distance(x, y, s.x, s.y);
			if(d < SWALLOW_DISTANCE){
				s.flag_for_removal = true;
			}
		}
		for(int i = 0; i < galaxy.stars.size(); i++){
			if(galaxy.stars.get(i).flag_for_removal){
				//EDIT: not remove, but reposition
				double dist = galaxy.randomGenerator.nextDouble()*galaxy.RADIUS+15;
				double rot = galaxy.randomGenerator.nextDouble()*Math.PI;
				
				galaxy.stars.get(i).x += dist*Math.cos(rot);
				galaxy.stars.get(i).y += dist*Math.sin(rot);
			}			
		}
	}
}
