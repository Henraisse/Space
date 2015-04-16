package Space;

import java.util.ArrayList;

import Static.Position;
import Static.Static;

public class StarPlanetFamily {
	
	Star star;
	ArrayList<Planet> planets = new ArrayList<Planet>();
	ArrayList<Double> planetDistances = new ArrayList<Double>();
	
	public StarPlanetFamily(Star s){
		star = s;
		planets = Static.getStarPlanets(s);

		//räkna ut min-avståndet till alla planeter
		for(Planet planet : planets){
			
			ArrayList<Double> distances = new ArrayList<Double>();
			double distance = 0;
			
			//för alla möjliga avstånd
			for(Position p : planet.orbitPositions){
				double dis0 = p.minus(Static.starCenterPos).length();
				
				if(dis0 > distance){
					distance = dis0;
				}
			}
			planets.add(planet);
			planetDistances.add(distance+Static.PLANET_GRAVITY_MAX_DISTANCE);
		}
	}
	
	
	
	public ArrayList<Planet> getPlanetsWithinRange(double distance){
		ArrayList<Planet> closePlanets = new ArrayList<Planet>();

		for(int i = 0; i < planets.size(); i++){
			if(planetDistances.get(i) > distance){
				closePlanets.add(planets.get(i));
			}
		}
		return closePlanets;
	}
	//funktion: mät avstånd, hämta de planeter som är nära nog för gravitation
	
	
	
}
