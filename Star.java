import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.util.Random;


public class Star {
	
	//GLOBAL VARIABLES
	int star_id;		//stjärnans id
	String name;		//stjärnans namn
	double x, y;		//stjärnans globala positioner
	double radius;		//stjärnans radie
	Galaxy galax;		//galaxen stjärnan finns i
	int kelvin;			//temperaturen stjärnan har

	



	
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
		name = n;
		
		//create a random generator, to randomize a position, temperatur and size
		Random rand = new Random();
		
		//generate specs
		kelvin = rand.nextInt(6)+1;
		generateSize(rand);		
	}
	
	/**
	 * Generate a color for the star, based on a double corresponding to the desired intensity.
	 * This function is mainly used when drawing the star.
	 * @param a
	 * @return
	 */
	public Color getTempColor(double a){
		//we cant draw anything with negative intensity
		if(a <= 0){ return Color.BLACK;}
		Color ret;
	
		//choose a color based on star Kelvin value
		switch (kelvin) {
		case 1:  ret = Static.O;	break;
		case 2:  ret = Static.B;	break;
		case 3:  ret = Static.A;	break;
		case 4:  ret = Static.F;	break;
		case 5:  ret = Static.G;	break;
		case 6:  ret = Static.K;	break;
		case 7:  ret = Static.M;	break;
		default: ret = Static.M; 	break;
		}
		//apply color to intensity and calculate resulting color
		int x = (int) (ret.getRed()*a);
		int y = (int) (ret.getGreen()*a);
		int z = (int) (ret.getBlue()*a);
		
		//limit the color intensity if max is reached(255 is max)
		if(x > 255){x = 255;}
		if(y > 255){y = 255;}
		if(z > 255){z = 255;}
		
		//create a new color object and return it.
		Color ret2 = new Color(x, y, z);		
		return ret2;
	}

	/**
	 * Draws the star. Uses a Graphics object, a zoom scale, and the zoomed-in position(to determine if we zoomed in on the star or not)
	 * Draws the star only if it is within the picture, else it returns.
	 * @param g
	 * @param zoom
	 * @param zx
	 * @param zy
	 */
	public void drawStar(Graphics g, double zoom, double zx, double zy){
		//calculate the total position
		double posX = zx+x;
		double posY = zy+y;
		
		//calculate the frame relative pixel position (in reference to the window)
		double factorX = posX*zoom;
		double factorY = posY*zoom;		

		
		//if outside the edges, do not paint the star - return instead
		if(factorX < -60 || factorX > 2000 || factorY < -60 || factorY > 1200){			
			return;
		}
		
		
		//calculate the size when zoomed-in
		double sizefactor = radius*zoom;
		
		// calculate orb sizes relative size
		int t3 = (int)(radius/3);		//secondary orb size
		int t4 = (int)(radius/2);		//tertiary orb size

		drawStarDot(g, zoom, sizefactor, posX, posY);
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
		Color c = getTempColor(size);
		
		//Before painting, make sure the light isn't too weak, or else don't paint
		if(c.getRed() > 50 || c.getGreen() > 50 || c.getBlue() > 50){
			g.setColor(c);
			g.drawLine(posX , posY, posX , posY);
		}
	}

	/**
	 * Generates a random size for the star.
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











