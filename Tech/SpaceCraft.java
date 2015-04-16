package Tech;

import Space.Planet;
import Space.SpaceObject;
import Space.Star;
import Static.Game;
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
	public Site launchSite;
	
	public Game game;
	
	
	public SpaceCraft(String n, Site constructionSite, Position offSet, Game g){
		game = g;
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
		object.closestStar = game.currGalaxy.stars.get(constructionSite.star_id);
		
		
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
							ret[0] = planet.planetPos.minus(Static.starCenterPos).plus(new Position(currentStar.x, currentStar.y).times(Static.lightYearsToMillionKM));
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


	public void launch(Position initialRocketThrust, int day){
		Position[] starOffsetPosition = determinePosition(launchSite);
		launchSite.presentCrafts.remove(this);
		inOrbit = true;
		object.launchDay = day;
		object.currentGalaxyPosition = starOffsetPosition[0];
		object.currentTrajectory = starOffsetPosition[1];
	}
	
	public void land(Site site){
		if(isLandingPossible(site)){
			launchSite = site;
			site.presentCrafts.add(this);
			inOrbit = false;
		}

	}
	
	public void calculateClosestStar(){
		
	}
	
	public boolean isLandingPossible(Site site){
		
		//avgör fart
		
		return false;
	}
	
}

















