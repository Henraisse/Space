package Space;
import java.awt.Color;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import Static.Position;
import Static.Static;

/**
 * This class represents a galaxy in space, containing stars.
 * @author Henraisse
 *
 */
public class Galaxy implements Serializable{
	public static final int SPACE_BOUNDS_X = 40000;
	public static final int SPACE_BOUNDS_Y = 30000;
	public static final double GALAXY_ARM_ROTATION = 6;
	public static final double STAR_ARM_BONUS_OFFSET = 650;
	public static final double STAR_ARM_MAX_OFFSET   = 300;
	public static final double WILDCARD_MULTIPLIER = 1.5;
	public static int NUMBER_OF_STARS = 150000;
	public static int NUMBER_OF_GALAXY_ARMS = 6;	
	public static int RADIUS = 0;	
	
	public Star sun;
	public Star selected_star = null;
	public static double x = 0;
	public static double y = 0;
	public ArrayList<Star> stars;
	public int id;
	
	public ArrayList<Star>[][] sectors;
	ArrayList<Star> selectedStars = new ArrayList<Star>();
	public int sector_size = 100;
	public Random randomGenerator;
	public BlackHole center;
	
	@SuppressWarnings("unchecked")
	public Galaxy(int starCount, int galaxy_center_x, int galaxy_center_y, int galaxy_radius, int id){
		this.id = id;
		NUMBER_OF_STARS = starCount;				
		RADIUS = galaxy_radius;
		x = galaxy_center_x;
		y = galaxy_center_y;
		
		sectors = new ArrayList[SPACE_BOUNDS_X/200][SPACE_BOUNDS_Y/200];
		for(int i = 0; i < sectors.length; i++){
			for(int j = 0; j < sectors[i].length; j++){
				sectors[i][j] = new ArrayList<Star>();
			}
		}
		
		stars = new ArrayList<Star>();
		randomGenerator = new Random(id);
		
		Static.setUpSun(this);
						
		for(int i = 1; i < NUMBER_OF_STARS; i++){
			Star new_star = new Star(i, "", this);
			generatePosition(randomGenerator, new_star);
			//placeInMatrix(new_star);
			stars.add(new_star);		
		}
						
		center = new BlackHole(starCount, galaxy_center_x, galaxy_center_y, this);
		
//		for(Star s: stars){
//			Static.calculateNeighbors(s);
//		}
		
		
	}

	/**
	 * Places the star in a spiral
	 * @param star
	 * @param rand
	 * @param wildcard_size
	 * @param wildcard_frequency
	 * @param offset0
	 * @param offset1
	 * @param dist
	 * @param minL
	 * @param maxL
	 * @param polynomial
	 */
	public void generatePositionSpiral(Star star, Random rand, double wildcard_size, int wildcard_frequency, int offset0, int offset1, int dist, double minL, double maxL, double polynomial ){					
		//calculates the distance from galaxy center, according to correct distribution algorithm
		double length = Static.distribution(rand, dist, minL, maxL, polynomial);
		
		//if desired, the star can get a wildcard galaxy radial distance (one chance in 10 or anything like it)
		if( wildcard_frequency != 0 && rand.nextInt(wildcard_frequency) == 0){
			length = length*wildcard_size;			
		}
		//randomizes which galaxy spiral arm to be placed in, and calculates that arms rotation
		int arm = rand.nextInt(NUMBER_OF_GALAXY_ARMS+3);	
		double cakePart = 360/(NUMBER_OF_GALAXY_ARMS);	

		//places the new position to the right of the galaxy center
		double[] pt = {x+RADIUS, y};		
		
		//calculates the rotation and rotates the star around the axis according to arm and distance from center
		double rotation = (arm*cakePart)+length*cakePart*GALAXY_ARM_ROTATION;
		AffineTransform.getRotateInstance(Math.toRadians(rotation), x, y).transform(pt, 0, pt, 0, 1);
		
		//calculates the new global position for the star
		double armpos_x = (length*(pt[0] - x)) + x;
		double armpos_y = (length*(pt[1] - y)) + y;
		
		//calculates a random offset (based partly on distance from galaxy center
		double random_x = (0.5-rand.nextDouble())*(offset0 + offset1*(1-length));
		double random_y = (0.5-rand.nextDouble())*(offset0 + offset1*(1-length));
		
		//give the star its new coordinates
		star.x = armpos_x + random_x;
		star.y = armpos_y + random_y;	
		
		placeInSector(star);
	}
	
	/**
	 * Generate a completely random galaxy-relative star position for the current star.
	 * @param galaxyCenter_x
	 * @param galaxyCenter_y
	 * @param rand
	 */
	public void generatePositionRandom(Star star, Random rand, int dist, double minL, double maxL, double polynomial, double radmult){			
		//calculates the distance from galaxy center, according to correct distribution algorithm
		double length = Static.distribution(rand, dist, minL, maxL, polynomial);
		
		//preliminary star positioned to the right of galaxy center, with a distance of RADIUS times a chosen multiplier (ex. RADIUS*1.5)
		double[] pt = {x+(RADIUS*radmult), y};		
		
		//calculates a random rotation between 0-360 degrees.
		double rotation = rand.nextDouble()*360;
		
		//rotates the star according to rotation
		AffineTransform.getRotateInstance(Math.toRadians(rotation), x, y).transform(pt, 0, pt, 0, 1); // specifying to use this double[] to hold coords
		
		//shortens the radial distance down to the correct one
		double armpos_x = ((length)*(pt[0] - x)) + x;
		double armpos_y = ((length)*(pt[1] - y)) + y;

		star.x = armpos_x;
		star.y = armpos_y;
		
		placeInSector(star);
	}
		
	/**
	 * Places the star in one several categories, to get an even spread of stars and get a neat looking galaxy
	 * @param galaxyCenter_x
	 * @param galaxyCenter_y
	 * @param rand
	 */
	public void generatePosition(Random rand, Star s){

		int category = rand.nextInt(16)+1;
				
		if(category >= 0 && category <= 5){
			generatePositionSpiral(s, rand, 0, 0, 550, 10, Static.POLYNOMIAL, 0.1, 1.5, 2.0);			
		}
		else if(category > 5 && category <= 6){
			generatePositionSpiral(s, rand, 0, 0, 150, 150, Static.LINEAR, 0.1, 1.5, 2.0);
		}
		else if(category > 6 && category <= 7){
			generatePositionRandom(s, rand, Static.LINEAR, 0.1, 1.5, 1, 1);
		}
		else if(category > 7 && category <= 8){
			generatePositionRandom(s, rand, Static.POLYNOMIAL, 0.0, 1.5, 2, 1.3);
		}
		else if(category > 8 && category <= 9){
			generatePositionRandom(s, rand, Static.POLYNOMIAL, 0.0, 1.5, 2, 1.1);
		}
		else if(category > 9 && category <= 10){
			generatePositionSpiral(s, rand, 0, 0, 950, 150, Static.LINEAR, 0.0, 1.5, 2.0);
		}
		else if(category > 10 && category <= 15){
			generatePositionRandom(s, rand, Static.POLYNOMIAL, 0.0, 1.5, 2, 0.8);
		}
		else if(category > 15 && category <= 16){
			generatePositionRandom(s, rand, Static.POLYNOMIAL, 0.7, 1.0, 2, 0.8);
		}

	}
	
	
	public int getStarId(double x, double y){
		Star closest = stars.get(0);
		double least = 9999999;
		for(Star s: stars){
			double distance = Math.sqrt(((x - s.x)*(x - s.x) + (y - s.y)*(y - s.y)));
			if (distance < least){
				least = distance;
				closest = s;
			}
		}
		selected_star = closest;
		closest.neighbors = Static.getNeighbors(closest);
		
		for(Star s : selectedStars){
			s.flagged = false;
		}
		selectedStars.clear();
		
		
		selectedStars.add(selected_star);
		for(Star s : selected_star.neighbors){
			selectedStars.add(s);
			s.flagged = true;
		}
		
		return closest.star_id;

	}
	
	public void placeInSector(Star s){
		int x = (int)(s.x/sector_size);
		int y = (int)(s.y/sector_size);
		
		if(x < 0 || y < 0){
			//System.out.println(s.x + " " + s.y);
		}
		
		sectors[x][y].add(s);		
		s.neighbors = sectors[x][y];
		s.neighborSector = new Point(x,y);
//		if(s.id){}
		//System.out.println("Star-" + s.star_id + " is added to (" + x + "," + y + ")");
	}
	
	public ArrayList<Star> getSector(Star s){
		int x = (int)(s.x/sector_size);
		int y = (int)(s.y/sector_size);
		
//		if(x < 0 || y < 0){
//			System.out.println(s.x + " " + s.y);
//		}
		
		return sectors[x][y];
	}
	
	
}



