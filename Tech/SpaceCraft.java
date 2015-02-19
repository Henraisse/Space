package Tech;

import Space.Planet;
import Space.SpaceObject;
import Space.Star;
import Static.Position;
import Static.Static;

public class SpaceCraft {

	public int star_id;
	public int planet_id;
	public String name;
	public boolean inOrbit = false;
	
	public SpaceObject object;
	public Booster booster;
	public NavComputer navComputer;

	
	
	public SpaceCraft(String n, Site constructionSite, Position offSet){
		name = n;
		//System.out.println("Spacecraft " + name + " online.");
		Position[] starOffsetPosition = determinePosition(constructionSite);
		
		booster = new Booster(navComputer);
		navComputer = new NavComputer(booster);
		//object = new SpaceObject(0, starOffsetPosition, trajectory, currentStar, navComputer, booster);

		if(offSet == null){
			object = new SpaceObject(0, starOffsetPosition[0], starOffsetPosition[1], navComputer, booster, this);				
		}		
		else{
			object = new SpaceObject(0, offSet, starOffsetPosition[1], navComputer, booster, this);				
		}
		
		
		
	}
	
	private Position[] determinePosition(Site site) {
		Position ret[] = new Position[2];
		star_id = site.star_id;
		planet_id = site.planet_id;
		Star currentStar;
		for(int i = 0; i < site.galaxy.stars.size(); i++){
			if(site.galaxy.stars.get(i).star_id == star_id){
				//vi hämtar rätt planet
				currentStar = site.galaxy.stars.get(i);
				if(Static.getStarPlanets(currentStar).size() > planet_id){
					for(Planet planet : Static.getStarPlanets(currentStar)){
						
						//Planet p = Static.getStarPlanets(currentStar).get(planet_id);
						if(planet.planetId == planet_id){
							//System.out.println("planet id: " + planet.planetId);
							//System.out.println(planet.planetPos + "    " + planet.trajectory);
							ret[0] = planet.planetPos.minus(Static.starCenterPos);
							ret[1] = planet.trajectory;
							return ret;
						}
					}
				}				
			}
		}		
		ret[0] =  new Position(500, 500);
		ret[1] =  new Position(0,0);
		return ret;
	}


	public void launch(Position initialRocketThrust){
		inOrbit = true;
	}
	
	public void land(){
		inOrbit = false;
	}
	
	public void calculateClosestStar(){
		
	}
	
	
	
}

















