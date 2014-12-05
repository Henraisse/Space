import java.awt.Color;
import java.util.Random;


public class Static {
	
	
	//GLOBAL VARIABLES
	public static int LINEAR = 1;
	public static int EXPONENTIAL = 2;
	public static final int POLYNOMIAL = 3;
	
	static Color O = new Color(155, 176, 255);
	static Color B = new Color(170, 191, 255);
	static Color A = new Color(202, 215, 255);
	static Color F = new Color(248, 247, 255);
	static Color G = new Color(255, 244, 234);
	static Color K = new Color(255, 210, 161);
	static Color M = new Color(255, 204, 111);
	
	//GLOBAL STATIC FUNCTIONS
	
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
	static double distribution(Random rand, int dis, double min, double max, double polynomial) {
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
		
}
