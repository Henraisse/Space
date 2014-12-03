import java.util.ArrayList;
import java.util.Random;


public class Galaxy {
	public static final double GALAXY_ARM_ROTATION = 6;
	public static final double STAR_ARM_BONUS_OFFSET = 300;
	public static final double STAR_ARM_MAX_OFFSET   = 350;
	public static int NUMBER_OF_STARS = 10000;
	public static int NUMBER_OF_GALAXY_ARMS = 6;
	
	public static int RADIUS = 4000;
	
	public static double x = 8000;
	public static double y = 4000;
	ArrayList<Star> stars;
	
	public Galaxy(){
		stars = new ArrayList<Star>();
		
		for(int i = 0; i < NUMBER_OF_STARS; i++){
			stars.add(new Star(i, "", this));
		}
		
		
//		Random rand = new Random();
//		NUMBER_OF_GALAXY_ARMS = rand.nextInt(8) + 3;
		
	}
	
}
