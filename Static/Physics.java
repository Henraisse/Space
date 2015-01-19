package Static;

import java.awt.Color;
import java.util.Random;

import Space.Planet;
import Space.Star;

public class Physics {

	private static final double VAPORIZATION_TEMPERATURE = 1500;
	private static final double PLANET_MIN_SPEED = 0.03;
	private static final double PLANET_DISTANCE_MULTIPLIER = 0.33;
	public static int PLANET_MAX_KM_RADIUS = 20000;
	public static double BOLTZMANN_CONSTANT = 5.6704*00000000.1;
	public static double SUN_AGE = 4600;
	public static double SUN_RADIUS = 696342000;
	public static double SUN_TEMP =  5778;


	public static void calculatePlanetDensity(Planet p, int magnitude, Random rand){
		double density = (magnitude - ((p.distance/(Static.MAX_SOLAR_DISTANCE*3))*magnitude) + (rand.nextDouble()*magnitude/10.0))* 1000;
		if(density < 0.7){density = 0.7;}		
		if(density < 2){p.radius *= Math.pow(3, 2*(2-density));}
		if(density < 1.5){p.gasGiant = true;}
		p.density = density;
	}
	
	public static void calculatePlanetMass(Planet p){
		//beräkna tyngd, jordmassa, gravitation, kärntemperatur
		p.kg = (p.density*(4*Math.PI*Math.pow(p.radius*1000, 3)/3));		
		p.mass = (double) (p.kg / (5.97219* Math.pow(10, 24)));
		p.gravity = (Static.GRAVITY_CONSTANT * p.kg)/((p.radius*1000)*(p.radius*1000));
		p.coreTemp = (int) Math.sqrt(Math.sqrt(p.kg) / p.radius)/3;
		
		if(p.coreTemp > p.star.temperature.kelvin){ p.coreTemp = p.star.temperature.kelvin;}
	}
	
	public static void calculatePlanetMagneticField(Planet p){
		//beräkna magnetfält
		p.magneticField = p.coreTemp - 3500;
		if(p.magneticField < 0){p.magneticField = 0;}
		p.magneticField = (int) Math.sqrt(p.magneticField);
	}

	
	public static void calculatePlanetAtmosphere(Planet p, Random rand){
		double solarWind = 1.6726 * (1.0/1000000.0) * Static.SOLAR_WIND_SPEED * Static.SOLAR_WIND_PARTICLE_INITIAL_DENSITY / (p.distance*p.distance);	
		p.atmosphere = (p.gravity - solarWind) + (p.magneticField/5);
		
		int chance = rand.nextInt(101);
		if(chance < 65 || p.atmosphere<0){
			p.atmosphere=0;
		}
	}
	
	
	public static void calculatePlanetSurfaceTemperature(Planet p) {
		Star s = p.star;
		
		double radius = s.radius*Math.pow(10, 8);
		
		//calculates the star's total luminosity, based on the size, shape and temperature of the star.
		double luminosity = 4.0*Math.PI*radius*radius*BOLTZMANN_CONSTANT*Math.pow(s.temperature.kelvin, 4.0);
		//System.out.println(luminosity);
		
		//defines the distance from the star in the correct unit for this calculation (meters from million kilometers, m * 10^9)
		double distance = p.distance * Math.pow(10, 9);
		
		//calculates the effective temperature for this planet (the temperature without any atmosphere or greenhouse effect)
		double effectiveTemp = Math.pow(((luminosity*(1-p.albedo))/(16*Math.PI*distance*distance*BOLTZMANN_CONSTANT)),0.25);
		

		double greenhouseTemp = effectiveTemp*Math.pow((1 + (0.75*p.atmosphere)), 0.25);
		p.surfaceTemp = greenhouseTemp;
	}
	
	
	public static double getMinPlanetDistance(Star s){
		double radius = s.radius*Math.pow(10, 8);
		double luminosity = 4.0*Math.PI*radius*radius*BOLTZMANN_CONSTANT*Math.pow(s.temperature.kelvin, 4.0);
		double temperature = 100000;
		double distance = 10;
		while(temperature > Physics.VAPORIZATION_TEMPERATURE){
			distance += 10;
			double dist0 =distance * Math.pow(10, 9);
			temperature = Math.pow(((luminosity*(1))/(16*Math.PI*dist0*dist0*BOLTZMANN_CONSTANT)),0.25);
			//System.out.println(temperature);
		}
		
		return distance;
	}
	
	public static double getMaxPlanetDistance(Star s){
		int distance = 0;
		double speed = 1000;
		while(speed > PLANET_MIN_SPEED){
			distance+=10;
			speed=Math.sqrt(Static.GRAVITY_CONSTANT *s.mass/(distance))*Math.sqrt(Static.TIMEFACTOR); // direction vector
		}
		return distance*PLANET_DISTANCE_MULTIPLIER;		
	}
	
	
	public static double sphereArea(double radius){
		return (4*Math.PI*radius*radius);
	}
	
	public static double sphereVolume(double radius){
		return (4*Math.PI*radius*radius*radius)/3;
	}
	
	
	
	public static Color getPlanetColor(int temp){
		if(temp > 10000){return new Color(255, 231, 145);}
		else if(temp > 9000){return new Color(251, 244, 115);}
		else if(temp > 8000){return new Color(247, 217, 98);}
		else if(temp > 7000){return new Color(245, 187, 99);}
		else if(temp > 6000){return new Color(245, 157, 99);}
		else if(temp > 5000){return new Color(242, 57, 57);}
		else if(temp > 4000){return new Color(218, 14, 14);}
		else if(temp > 3000){return new Color(176, 38, 38);}
		else if(temp > 2000){return new Color(145, 82, 70);}
		else if(temp > 1000){return new Color(116, 67, 67);}
		else if(temp > 700){return new Color(106, 90, 77);}
		else if(temp > 400){return new Color(91, 91, 91);}
		else if(temp > 300){return new Color(121, 121, 121);}
		else if(temp > 200){return new Color(152, 154, 124);}
		else if(temp > 100){return new Color(151, 125, 153);}
		else if(temp > 80){return new Color(139, 169, 109);}
		else if(temp > 60){return new Color(98, 138, 157);}
		else if(temp > 40){return new Color(65, 128, 190);}
		else if(temp > 20){return new Color(63, 222, 58);}
		else if(temp > 0){return new Color(152, 231, 235);}
		else if(temp > -30){return Color.white;}

		
		return Color.black;
	}
	
	
	/**
	 * Calculates the maximum number of orbiting planetary bodies.
	 * @param s
	 * @return
	 */
	public static int getMaxNumPlanets(Star s){
		return (int) (2*Math.sqrt(s.mass/1000)/3);
	}
}












