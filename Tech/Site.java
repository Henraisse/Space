package Tech;

import java.util.ArrayList;

import Space.Galaxy;

public class Site {
	
	public Galaxy galaxy;
	public int star_id;
	public int planet_id;
	public ArrayList<SpaceCraft> presentCrafts = new ArrayList<SpaceCraft>();
	
	public Site(Galaxy g, int sid, int pid){
		galaxy = g;
		star_id = sid;
		planet_id = pid;
	}
}
