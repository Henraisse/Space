package Tech;

import Space.Galaxy;

public class Site {
	
	public Galaxy galaxy;
	int star_id;
	int planet_id;
	
	public Site(Galaxy g, int sid, int pid){
		galaxy = g;
		star_id = sid;
		planet_id = pid;
	}
}
