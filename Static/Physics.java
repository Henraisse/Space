package Static;

import java.awt.Color;
import java.util.Random;

import Space.Planet;

public class Physics {

	public static int PLANET_MAX_KM_RADIUS = 20000;


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
		
		if(p.coreTemp > p.star.temperature.kelvin){ p.coreTemp = p.star.temperature.kelvin;}
	}
	
	public static void calculatePlanetMagneticField(Planet p){
		//beräkna magnetfält
		p.magneticField = p.coreTemp - 3500;
		if(p.magneticField < 0){p.magneticField = 0;}
		p.magneticField = (int) Math.sqrt(p.magneticField);
	}

	
	public static void calculatePlanetAtmosphere(Planet p){
		double solarWind = 1.6726 * (1.0/1000000.0) * Static.SOLAR_WIND_SPEED * Static.SOLAR_WIND_PARTICLE_INITIAL_DENSITY / (p.distance*p.distance);	
		//System.out.println("solar wind: " + solarWind);
		//System.out.println("gravity: " + p.gravity);
		//p.atmosphere = (p.gravity - solarWind - 20) + (p.magneticField/5);
		p.atmosphere = (p.gravity - solarWind) + (p.magneticField/5);
		if(p.atmosphere<0){p.atmosphere=0;}
		//System.out.println("atmosphere: " + p.atmosphere);
	}
	
	
	public static void calculatePlanetSurfaceTemperature(Planet p) {
		double innerTempBonus = p.coreTemp - 8000; 		
		if(innerTempBonus < 0){innerTempBonus = 0;}
		if(innerTempBonus > 0){innerTempBonus = Math.pow(innerTempBonus, 0.65);}
		double minTemp = 30 + Math.pow(243, (1000 - p.distance)/1000);	
		
		
		double solarBonus = p.star.temperature.kelvin*10 /(Math.pow(p.distance, 1.4));	
		double solarBonus2 = (sphereArea(p.star.radius/10) / sphereArea(p.distance)) * p.star.temperature.kelvin;
		
		
		p.surfaceTemp = minTemp + (innerTempBonus + solarBonus2 + solarBonus);
		if(p.surfaceTemp < 0){p.surfaceTemp = 0;}
		
		System.out.println("ID: " + p.planetId);
		System.out.println(minTemp + " " + innerTempBonus + " " + solarBonus + "\n");
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
}












