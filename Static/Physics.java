package Static;

import java.util.Random;

import Space.Planet;

public class Physics {

	public static void calculatePlanetDensity(Planet p, int magnitude, Random rand){
		double density = (magnitude - ((p.distance/Static.MAX_SOLAR_DISTANCE)*magnitude) + (rand.nextDouble()*magnitude/10.0))* 1000;
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
	}
	
	public static void calculatePlanetMagneticField(Planet p){
		//beräkna magnetfält
		p.magneticField = p.coreTemp - 3500;
		if(p.magneticField < 0){p.magneticField = 0;}
		p.magneticField = (int) Math.sqrt(p.magneticField);
	}

	
	public static void calculatePlanetAtmosphere(Planet p){
		double solarWind = 1.6726 * (1.0/1000000.0) * Static.SOLAR_WIND_SPEED * Static.SOLAR_WIND_PARTICLE_INITIAL_DENSITY / (p.distance*p.distance);		
		p.atmosphere = (p.gravity - solarWind - 20) + (p.magneticField/5);
	}
	
	
	public static void calculatePlanetSurfaceTemperature(Planet p) {
		double innerTempBonus = p.coreTemp - 8000; 		
		if(innerTempBonus < 0){innerTempBonus = 0;}
				
		double solarBonus = p.star.temperature.kelvin*60 /(p.distance*p.distance);	
		
		
		p.surfaceTemp = (innerTempBonus + solarBonus) + (innerTempBonus + solarBonus)*p.atmosphere/3;
		if(p.surfaceTemp < 0){p.surfaceTemp = 0;}
	}
}












