package Static;
import java.util.ArrayList;

import Space.Galaxy;
import Tech.Site;
import Tech.SpaceCraft;


public class Game {

	
	//These sites are the known sites, which we can build, construct and act at.
	public ArrayList<Site> knownSites = new ArrayList<Site>();
	public ArrayList<SpaceCraft> spacecrafts = new ArrayList<SpaceCraft>();
	public SpaceCraft currentSpacecraft;
	public Galaxy currGalaxy;
	
	
	
	public Game(){}
	
	public void setTest(Galaxy g){
		
		
		
		Site test0 = new Site(g, 42571, 0);
		Site test1 = new Site(g, 51029, 0);
		Site test2 = new Site(g, 119749, 0);
		knownSites.add(test0);
		knownSites.add(test1);
		knownSites.add(test2);
		
		SpaceCraft enterprise0 = new SpaceCraft("Drone1", test0, null);
		spacecrafts.add(enterprise0);
		
		SpaceCraft enterprise1 = new SpaceCraft("Drone2",test1, new Position(668613000+7389 - 1097650, 904033750+1782-293000));
		spacecrafts.add(enterprise1);
		
		SpaceCraft enterprise2 = new SpaceCraft("Drone3",test2, null);
		spacecrafts.add(enterprise2);
		
		SpaceCraft enterprise3 = new SpaceCraft("Drone4",test1, null);
		spacecrafts.add(enterprise3);
	}
	
}
