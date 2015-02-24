package Static;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;

import Space.Galaxy;
import Space.Planet;
import Space.Star;


public class Static {
	
	public static Double lightYearsToMillionKM = 9460528.4;
	public static Double Orbits = -80.4;
	
	//PHYSICS
	public static double slowDown = 590;
	public static double mult=1.43/slowDown;
	public static double TIMEFACTOR=31536000*mult;
	public static double GRAVITY_CONSTANT = 0.0000000000673;
	public static int MAX_SOLAR_DISTANCE = 400;
	public static double SOLAR_WIND_SPEED = 400000;
	public static double SOLAR_WIND_PARTICLE_INITIAL_DENSITY = 10;
	
	public static int ITERATIONS_PER_DAY = 24;
	
	//GLOBAL VARIABLES
	public static int LINEAR = 1;
	public static int EXPONENTIAL = 2;
	public static final int POLYNOMIAL = 3;
	
	private static final double NEIGHBOR_DISTANCE = 0.1;
	public static final String STAR_SPECS_LABEL = "ssl";
	public static final Position STAR_DISPLAY_POS = new Position(1585+153, 135+146);
	public static final String GALAXY_STAR_DISPLAY = "gstd";
	public static final double STAR_DISPLAY_SIZE_MULTIPLIER = 7.0;
	public static final String DISPLAY_DESCRIPTION_TEXT = "Display Descriptions";
	public static final String MANUAL_ORBIT_SWITCH_TEXT = "Manual Orbits";

	public static String DISTANCE_RULER_FONT = "BoomBox 2";
	
	public static Color STAR_NAME_COLOR = new Color(96,96,255);
	
	public static double DRAW_STAR_RADIUS_CENTER = 15;
	
	public static int STAR_NAME_NUMBER_COUNT = 3;
	public static Font ruler_font8 = new Font(Static.DISTANCE_RULER_FONT, Font.PLAIN, 8);
	public static Font ruler_font10 = new Font(Static.DISTANCE_RULER_FONT, Font.PLAIN, 10);
	public static Font ruler_font12 = new Font(Static.DISTANCE_RULER_FONT, Font.PLAIN, 12);
	public static Font ruler_font16 = new Font(Static.DISTANCE_RULER_FONT, Font.PLAIN, 16);
	public static Font ruler_font18 = new Font(Static.DISTANCE_RULER_FONT, Font.PLAIN, 18);	
	public static Font ruler_font22 = new Font(Static.DISTANCE_RULER_FONT, Font.PLAIN, 22);
	public static Font ruler_font24 = new Font(Static.DISTANCE_RULER_FONT, Font.PLAIN, 24);
	
	public static String DISTANCE_UNIT01 = " LIGHT YEARS";
	public static String DISTANCE_UNIT02 = " Million KM";
	
	public static Temperature O5 = new Temperature(54000, new Color(157, 180, 255));
	public static Temperature B1 = new Temperature(23000, new Color(162, 185, 255));
	public static Temperature B3 = new Temperature(17600, new Color(167, 188, 255));
	public static Temperature B5 = new Temperature(15200, new Color(170, 191, 255));
	public static Temperature B8 = new Temperature(12300, new Color(175, 195, 255));
	public static Temperature A1 = new Temperature(9330, new Color(186, 204, 255));
	public static Temperature A3 = new Temperature(8750, new Color(192, 209, 255));
	public static Temperature A5 = new Temperature(8310, new Color(202, 216, 255));
	public static Temperature F0 = new Temperature(7350, new Color(228, 232, 255));
	public static Temperature F2 = new Temperature(7050, new Color(237, 238, 255));
	public static Temperature F5 = new Temperature(6700, new Color(251, 248, 255));
	public static Temperature F8 = new Temperature(6300, new Color(255, 249, 249));
	public static Temperature G2 = new Temperature(5800, new Color(255, 245, 236));
	public static Temperature G5 = new Temperature(5660, new Color(255, 244, 232));
	public static Temperature G8 = new Temperature(5440, new Color(255, 241, 223));
	public static Temperature K0 = new Temperature(5240, new Color(255, 235, 209));
	public static Temperature K4 = new Temperature(4600, new Color(255, 215, 174));
	public static Temperature K7 = new Temperature(4000, new Color(255, 198, 144));
	public static Temperature M2 = new Temperature(3600, new Color(255, 190, 127));
	public static Temperature M4 = new Temperature(3400, new Color(255, 187, 123));
	public static Temperature M8 = new Temperature(2700, new Color(253, 156, 137));

	
	public static String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	

	
	public static String STARNAME_FILEPATH_PREFIX = "C:\\Users\\Henraisse\\workspace\\Stars\\STAR NAME FILES\\Prefix.txt";
	public static String STARNAME_FILEPATH_MID = "C:\\Users\\Henraisse\\workspace\\Stars\\STAR NAME FILES\\Mid.txt";
	public static String STARNAME_FILEPATH_SUFFIX = "C:\\Users\\Henraisse\\workspace\\Stars\\STAR NAME FILES\\Suffix.txt";
	
	public static String SELECT_BUTTON_TEXT = "Select";
	public static String EXAMINE_BUTTON_TEXT = "Examine";
	public static String NAME_BUTTON_TEXT = "Name";
	public static String DISPLAY_ORBITS_BUTTON_TEXT = "Display Orbits";	
	
	public static ImageIcon boosters = new ImageIcon("boosters.png");
	
	public static ImageIcon halo = new ImageIcon("star_halo.png");
	public static ImageIcon planet01 = new ImageIcon("planet01.png");
	public static ImageIcon planet02 = new ImageIcon("planet02.png");
	public static ImageIcon planet03 = new ImageIcon("planet03.png");
	
	public static ImageIcon venus = new ImageIcon("planets/planets_color/desert/venus.png");
	public static ImageIcon earth = new ImageIcon("planets/planets_color/life/earth.png");
	public static ImageIcon mars = new ImageIcon("planets/planets_color/desert/mars.png");
	
	public static ImageIcon[] planetLifes = {
		new ImageIcon("planets/planets_color/life/earth.png"),
		new ImageIcon("planets/planets_color/life/earth2.png"),
		new ImageIcon("planets/planets_color/life/planet23.png"),
		new ImageIcon("planets/planets_color/life/planet25.png"),
		new ImageIcon("planets/planets_color/life/planet26.png"),
		new ImageIcon("planets/planets_color/life/planet27.png")
	};
	
	public static ImageIcon[] planetGasgiants = {
//		new ImageIcon("planets/planets_color/gasGiant/planet01"),	DESSA HAR EGEN FÄRG
//		new ImageIcon("planets/planets_color/gasGiant/planet05"),
//		new ImageIcon("planets/planets_color/gasGiant/planet07"),
//		new ImageIcon("planets/planets_color/gasGiant/planet12"),
//		new ImageIcon("planets/planets_color/gasGiant/planet18"),
//		new ImageIcon("planets/planets_color/gasGiant/planet20"),
		new ImageIcon("planets/planets_gray/gasGiant/planet01.png"),
		new ImageIcon("planets/planets_gray/gasGiant/planet05.png"),
		new ImageIcon("planets/planets_gray/gasGiant/planet07.png"),
		new ImageIcon("planets/planets_gray/gasGiant/planet12.png"),
		new ImageIcon("planets/planets_gray/gasGiant/planet18.png"),
		new ImageIcon("planets/planets_gray/gasGiant/planet20.png"),
		new ImageIcon("planets/planets_gray/gasGiant/planet29.png"),
		new ImageIcon("planets/planets_gray/gasGiant/planet31.png"),
	};
	
	public static ImageIcon[] planetIces = {
		new ImageIcon("planets/planets_color/ice/planet06.png"),
		new ImageIcon("planets/planets_color/ice/planet16.png"),
		new ImageIcon("planets/planets_color/ice/planet24.png")
	};
	
	public static ImageIcon[] planetLava = {
		new ImageIcon("planets/planets_color/lava/planet15.png")
	};
	
	public static ImageIcon[] planetBarren = {
		new ImageIcon("planets/planets_gray/desert/planet02.png"),
		new ImageIcon("planets/planets_gray/desert/planet13.png"),
		new ImageIcon("planets/planets_gray/desert/planet14.png"),
		new ImageIcon("planets/planets_gray/desert/planet28.png"),
		new ImageIcon("planets/planets_gray/desert/planet30.png"),
		new ImageIcon("planets/planets_gray/rock/planet04.png"),
		new ImageIcon("planets/planets_gray/rock/planet08.png"),
		new ImageIcon("planets/planets_gray/rock/planet09.png"),
		new ImageIcon("planets/planets_gray/rock/planet10.png"),
		new ImageIcon("planets/planets_gray/rock/planet11.png"),
		new ImageIcon("planets/planets_gray/rock/planet17.png"),
		new ImageIcon("planets/planets_gray/rock/planet19.png"),
		new ImageIcon("planets/planets_gray/rock/planet21.png"),
		new ImageIcon("planets/planets_gray/rock/planet22.png"),		
	};

	
	
	public static ImageIcon StarSystemBackground01 = new ImageIcon("StarSystemBackground01.png");

	//GLOBAL STATIC FUNCTIONS
	public static Position starCenterPos=new Position(700, 540);
	
	/**
	 * Create a distribution between 1 and 0. Uses the parameters
	 * LINEAR or POLYNOMIAL, along with the exponent of the polynomial.
	 * @param rand			- the random generator used by this function
	 * @param dis			- the desired distribution algoritm
	 * @param min			- the minimum galaxy center distance (times radius)
	 * @param max			- the maximum galaxy center distance (times radius)
	 * @param polynomial	- when using POLYNOMIAL, this polynomial determines the power of the expression.
	 * @return
	 */
	public static double distribution(Random rand, int dis, double min, double max, double polynomial) {
		double ret = min;
		
		if(dis == Static.LINEAR){
			ret += rand.nextDouble();
		}
		
		else if(dis == Static.EXPONENTIAL){
			double r = rand.nextDouble(); 
			ret += r*r*r;
		}
		
		else if(dis == Static.POLYNOMIAL){
			double r = rand.nextDouble(); 
			ret += Math.pow(r, polynomial);
		}
		
		if(ret > max){
			ret %= max;
			ret += min;
			}
		return ret;
	}
	
	
	/**
	 * Stores the galaxy to file. This will save the galaxy as a galaxy object file, placing it in the Space catalogue
	 * and naming it galaxy_original.sgx.
	 */
	public static void storeGalaxy(Galaxy gax){		
		try {
			FileOutputStream fout;
			fout = new FileOutputStream("C:\\Users\\Henraisse\\Desktop\\Space\\galaxy_original.sgx");
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(gax);
			oos.close();
			fout.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * reads a galaxy from memory, the file which is saved in storeGalaxy.
	 * @return
	 */
	public static Galaxy readGalaxy(){
		ObjectInputStream objectinputstream = null;
		 try {
			 	FileInputStream streamIn = new FileInputStream("C:\\Users\\Henraisse\\Desktop\\Space\\galaxy_original.sgx");
		        objectinputstream = new ObjectInputStream(streamIn);
		        Galaxy newgax = (Galaxy) objectinputstream.readObject();	
		        objectinputstream.close();
		        streamIn.close();
		        return newgax;
		    } catch (Exception e) {

		        e.printStackTrace();
		 }
		 return null;
	}
	
	
	public static ArrayList<Star> getSector(Galaxy g, double x, double y){
		int x0 = (int) (x/g.sector_size);
		int y0 = (int) (y/g.sector_size);
		return g.sectors[x0][y0];
	}
	
	public static void flagSector(Galaxy g, double x, double y){
		ArrayList<Star> sector = getSector(g, x, y);
		for(Star s : sector){
			//s.flagged = true;
		}
	}
	
	/**
	 * Searches the stars sector and neighboring sectors for possible twin stars
	 * @param s
	 */
//	public static void calculateNeighbors(Star s){
////		int c = 0;
//		for(int i = (int) (s.x-
//				s.galax.sector_size); 
//				i <= s.x+s.galax.sector_size; 
//				i+= s.galax.sector_size){
//			for(int j = (int) (s.y-s.galax.sector_size); j <= s.y+s.galax.sector_size; j+= s.galax.sector_size){
//				ArrayList<Star> sector = getSector(s.galax, i, j);
//				for(Star n: sector){
//					double d = distance(s.x, s.y, n.x, n.y);
//					//if distance to other star is less than one thousandth of a light year
//					if(d < NEIGHBOR_DISTANCE){
//						
//						//add that star to neighbors
//						if(!s.equals(n)){
//							s.neighbors.add(n);
//						}
//					}
//				}
//			}
//		}
////		if(c > 0){
////			//System.out.println(c + " neighbors");
////		}
//	}

	
	public static double distance(double x0, double y0, double x1, double y1){
		return Math.sqrt(((x0 - x1)*(x0 - x1) + (y0 - y1)*(y0 - y1)));
	}
	
	
	
	public static String getStarName(Star s){

		String s1 = String.format(("%0" + STAR_NAME_NUMBER_COUNT + "d"), s.star_id % 1000);
		
		int part2 = (s.star_id / 1000) % 15;
		char s2 = letters.charAt(part2);
		
		int part3 = ((s.star_id / 1000) - part2) / 15;
		char s3 = letters.charAt(part3);

		String n = Character.toString(s3) + Character.toString(s2) + "-" + s1;		
		return n;		
	}
		
	
	public static ArrayList<String> readTextFile(String fileName) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		ArrayList<String> file = new ArrayList<String>();
		
		//StringBuilder sb = new StringBuilder();
		String line = br.readLine();
		file.add(line);
		while (line != null) {
			
			//sb.append(line);
			//sb.append(System.lineSeparator());
			line = br.readLine();
			if(line != null){
				file.add(line);
			}
			
		}
		//String everything = sb.toString();

		br.close();

		return file;
	}

		
	public static void calculatePlanetColor(Planet p){
		//System.out.println(p.surfaceTemp);
//		int color = new Random().nextInt(10);
//		switch(color){
//		case 0: p.color = Color.red; break;
//		case 1: p.color = Color.blue; break;
//		case 2: p.color = Color.yellow; break;
//		case 3: p.color = Color.green; break;
//		case 4: p.color = Color.pink; break;
//		case 5: p.color = Color.white; break;
//		case 6: p.color = Color.black; break;
//		case 7: p.color = Color.cyan; break;
//		case 8: p.color = Color.magenta; break;
//		case 9: p.color = Color.orange; break;
//		
//		}
		//p.color = Color.red;
		//if(p.surfaceTemp < 0){p.color = new Color(118,98,98);}
		//else if(p.surfaceTemp < 20){p.color = new Color(218,98,98);}
		//else if(p.surfaceTemp < 100){p.color = new Color(118,98,98);}
		//else if(p.surfaceTemp < 200){p.color = new Color(0,0,98);}
		
		//p.image = planetImages[p.star.galax.randomGenerator.nextInt(33)];
		p.color = randColor(p.star.galax.randomGenerator);
		
		if(p.gasGiant){
			p.image = planetGasgiants[p.star.galax.randomGenerator.nextInt(8)];
		}
		else if(p.surfaceTemp < 50 || (p.surfaceTemp < 250 && p.water)){
			p.image = planetIces[p.star.galax.randomGenerator.nextInt(3)];
		}
		else if(p.life){
			p.image = planetLifes[p.star.galax.randomGenerator.nextInt(6)];
		}
		else{
			p.image = planetBarren[p.star.galax.randomGenerator.nextInt(14)];
		}
	}
	
	public static double roundOff(double x, int decimals){
		int x0 = (int) (x * Math.pow(10, decimals));
		double x1 = (double)(x0)/Math.pow(10, decimals);
		return x1;
	}
	
	public static double randDouble(double a, double b){
		Random rand = new Random();
		return (rand.nextDouble()*a)+b;
	}
	
	public static int randInt(int a, int b){
		Random rand = new Random();
		return (rand.nextInt(a))+b;
	}
	
	public static int randInt(int a, int b, Random rand){
		return (rand.nextInt(a))+b;
	}
	
	public static Color randColor(){
		int red = randInt(255, 1);
		int green = randInt(255, 1);
		int blue = randInt(255, 1);
		return new Color(red, green, blue);
	}
	
	public static Color randColor(Random rand){
		int red = randInt(255, 1, rand);
		int green = randInt(255, 1, rand);
		int blue = randInt(255, 1, rand);
		return new Color(red, green, blue);
	}
	
	
	public static String yesNo(boolean yes){
		if(yes){
			return "Yes";
		}
		return "No";
	}
	
	
	public static ArrayList<Planet> getStarPlanets(Star s){
		Random rand = new Random(s.star_id);
		ArrayList<Planet> newPlanets = new ArrayList<Planet>();
		
		if(s.star_id == 0){
			for (int i=0; i<3; i++)
			{
				Planet p = new Planet(s, rand, i, true, newPlanets);
				newPlanets.add(p);
			}
		}
		else{
			int numPlanets = rand.nextInt(Physics.getMaxNumPlanets(s));		
			for(int i = 0; i < numPlanets; i++){
				Planet p = new Planet(s, rand, i, false, newPlanets);
				newPlanets.add(p);
			}
		}
		return newPlanets;
	}
	
	public static void setUpSun(Galaxy g){
		
		Star sun = new Star(0, "", g);
		//g.generatePosition(g.randomGenerator, sun);
		g.sun = sun;
		g.stars.add(sun);
		
		sun.x = 6950;
		sun.y = 8100;
		sun.temperature = Static.G2;
		sun.radius = 7;
		//sun.mass = 330000;

		g.selected_star = sun;
	}
	
	public static void createSpaceCraft(){
		
	}
	
	
	public static ArrayList<Point> neighboringPoints(Point p, int x, int y){
		ArrayList<Point> points = new ArrayList<Point>();
		//System.out.println("neighboringPoints");
		Point p0 = new Point(p.x+1, p.y+1);
		Point p1 = new Point(p.x+1, p.y);
		Point p2 = new Point(p.x+1, p.y-1);
		Point p3 = new Point(p.x, p.y+1);
		Point p4 = new Point(p.x, p.y);
		Point p5 = new Point(p.x, p.y-1);
		Point p6 = new Point(p.x-1, p.y+1);
		Point p7 = new Point(p.x-1, p.y);
		Point p8 = new Point(p.x-1, p.y-1);
		Point[] ps = {p0, p1, p2, p3, p4, p5, p6, p7, p8};
		for(Point pp : ps){
			if(pp.x >= 0 && pp.x < x){
				if(pp.y >= 0 && pp.y < y){
					points.add(pp);
				}
			}
		}
		
		return points;
	}
	
	public static ArrayList<Star> getNeighbors(Star s){
		Galaxy g = s.galax;
		ArrayList<Star> stars = new ArrayList<Star>();
		ArrayList<Point> points = neighboringPoints(s.neighborSector, g.sectors.length, g.sectors[0].length);
		
		for(Point p : points){
			stars.addAll(g.sectors[p.x][p.y]);
		}

		for(Star ss : stars){
			//System.out.println(ss.star_id);
		}
		//System.out.println("-------------");
		stars.remove(s);
		
		
		return stars;
	}
	
	
}








