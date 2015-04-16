package Space;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import GUI.GPanel;
import GUI.SPanel;
import Static.BigPosition;
import Static.Physics;
import Static.Position;
import Static.Static;
import Tech.Booster;
import Tech.NavComputer;
import Tech.SpaceCraft;

public class SpaceObject {
	
	public int launchDay;
	public double orbits = 0;
	
	public Star closestStar;	
	public SpaceCraft spacecraft;
	public NavComputer navComputer;
	public Booster booster;

	public Position currentGalaxyPosition;	
	public Position currentTrajectory;	
	public Position starSpacePosition;
	public Position trajectory;
	
	public ArrayList<Integer> boosters = new ArrayList<Integer>();	
	public ArrayList<Position> trajectoryApproximation = new ArrayList<Position>();
	public ArrayList<Position> positionApproximation = new ArrayList<Position>();

		
	/**
	 * Creates a spaceObject. This object corresponds to spacecrafts and other spacefaring objects with a trajectory.
	 * @param day (Integer) - The day from day 0 that this spaceCraft launches/launched.
	 * @param p (Position) - The initial position in space (distance unit is in Light Years)
	 * @param t (position) - The initial trajectory (the amount of distance to traverse in each iteration)
	 * @param nc (NavComputer) - The spaceObjects navigational computer
	 * @param b (Booster) - the spaceObjects boosters
	 * @param sc (SpaceCraft) - the spaceObjects spaceCraft object.
	 */
	public SpaceObject(int day, Position p, Position t, NavComputer nc, Booster b, SpaceCraft sc){
		spacecraft = sc;
		booster = b;
		navComputer = nc;
		booster.object = this;		
		navComputer.object = this;
		
		launchDay = day;
		starSpacePosition = p.clone();
		currentGalaxyPosition = p.clone();
		
		trajectory = t;
		currentTrajectory = trajectory.clone();		
	}
	
	/**
	 * Adds momentum to the trajectory. For example to burn engines for awhile, use this
	 * method for some iterations.
	 * @param p (Position) - the momentum vector to add to the spaceObjects trajectory
	 */
	public void alterTrajectory(Position p){
		trajectory.add(p);
	}
	
	/**
	 * Calculates one step of the gravitational pull in a certain moment. The forces that apply here corresponds to one hour.
	 * @param closeStars (ArrayList<Star>) - the list of close Stars
	 * @param approxIndex (int) - time from "now" this iteration will occur (in hours)
	 */
	public void gravity(ArrayList<Star> closeStars, int approxIndex)
	{
		Position currentPos = positionApproximation.get(approxIndex).clone();		
		Position currentTraj = trajectoryApproximation.get(approxIndex).clone();	
		
		if(true){		
			
			Position[] data = Static.getGravityHour(this, approxIndex, currentTraj, currentPos, closeStars);
			positionApproximation.add(data[0]);
			trajectoryApproximation.add(data[1]);
		}
		else if(true){		
			
			Position[] data = Static.getGravityMinutes(this, approxIndex, currentTraj, currentPos, closeStars);
			positionApproximation.add(data[0]);
			trajectoryApproximation.add(data[1]);
		}


	}
	
	/**
	 * Retrieves the current booster force for a specific hour.
	 * @param approxIndex (int) - the current hour
	 * @param currPos	(Position) - the current space position
	 * @param currentTraj (Position) - the current trajectory
	 * @return a Position corresponding to the trajectory adding from the booster for the specific hour
	 */
	public Position getBoosterAdding(int approxIndex, Position currPos, Position currentTraj) {
		Position boostAdd = new Position(0,0);
		
		approxIndex = (approxIndex+launchDay*24);
			boostAdd = booster.getBoost(approxIndex, currPos, currentTraj);
		return boostAdd;
	}

	/**
	 * Retrieves the space sector in which the provided position is positioned in
	 * @param p (Position) the current position.
	 * @return all the stars in the current sector
	 */
	public ArrayList<Star> getStarSector(Position p){
		Galaxy g = closestStar.galax;
		ArrayList<Star> currentSector = g.getSector(p.times(1.0/Static.lightYearsToMillionKM));
		return currentSector;
	}
			
	/**
	 * Calculate a trajectory arc based on the gravitational pull from stars and planets, along with the
	 * forces of the objects own propulsion
	 */
	public void calculateTrajectoryArc(){
		positionApproximation.clear();
		trajectoryApproximation.clear();
				
		positionApproximation.add(currentGalaxyPosition.clone());
		trajectoryApproximation.add(currentTrajectory.clone());
		ArrayList<Star> vicinityStars = new ArrayList<Star>();
		for(int i = 0; i < navComputer.memory; i++){
			
			if(i%100 == 0){
				vicinityStars = getStarSector(positionApproximation.get(i));			
			}
			gravity(vicinityStars, i);
		}
		
	}
	
	
	
	
	public Position getLocalPos(Star s, Position p){
		Position starPos = new Position(s.x, s.y).times(Static.lightYearsToMillionKM);
		return p.minus(starPos);
	}
	
	
	public Position getStarPanelPaintPos(Position p, SPanel sp, double zoom){
		return getLocalPos(sp.currentStar, p).times(zoom).plus(Static.starCenterPos);
	}
	
	public void Paint(Graphics g, double zoom, double zoom_x, double zoom_y, GPanel gp, SPanel sp){		
		double starScale = Static.lightYearsToMillionKM;
		g.setColor(Color.red);
		Position starPos = Static.starCenterPos;	
		
		if(gp != null){

			//calculates the on-screen relative position.
			//int posX = (int)(x*currentZoom);
			//int posY = (int)(y*currentZoom);

			int x = (int)(((starSpacePosition.x/Static.lightYearsToMillionKM)+zoom_x)*zoom);
			int y = (int)(((starSpacePosition.y/Static.lightYearsToMillionKM)+zoom_y)*zoom);


			
			//int x = (int)(((starSpacePosition.x/Static.lightYearsToMillionKM)+zoom_x)*zoom);
			//int y = (int)(((starSpacePosition.y/Static.lightYearsToMillionKM)+zoom_y)*zoom);
			g.fillOval(x-5 , y-5, 10 , 10);
			
			
			
//			starScale = (1.0/(Static.lightYearsToMillionKM));
//			starPos = new Position(((closestStar.x+zoom_x)*zoom), ((closestStar.y+zoom_y)*zoom));						
//			Position p = getLocalPos(sp.currentStar).times(zoom*starScale);
//			g.fillOval((int)(((closestStar.x+zoom_x)*zoom)+p.x-5), (int)(((closestStar.y+zoom_y)*zoom)+p.y-5), (int)(2*5), (int)(2*5));
			
		}
		else if(sp != null){					
			starScale = 1.0;
			//Position paintPos = starSpacePosition.minus(Static.starCenterPos).times(zoom).plus(Static.starCenterPos);	
			Position paintPos = getLocalPos(sp.currentStar, starSpacePosition).times(zoom).plus(Static.starCenterPos);	
			g.fillOval((int)( paintPos.x-5), (int)( paintPos.y-5), (int)(2*5), (int)(2*5));			
			//g.drawString(paintPos.toString(), (int)(400), (int)(200));
		}

		

		

		if(positionApproximation.size() == 0){
			calculateTrajectoryArc();
		}
		if(true){
			//first calculate number of prints
			int counter = 0;
			double dotValue = 1000.0/positionApproximation.size();
			double dotIter = 0;
			//Position lastPos = positionApproximation.get(0).times(zoom*starScale);
			Position lastPos = new Position(-9999, -9999);
			if(sp != null){lastPos = getLocalPos(sp.currentStar, positionApproximation.get(0)).times(zoom).plus(Static.starCenterPos);}
			else if(gp != null){
				lastPos = 
						new Position(
								(((positionApproximation.get(0).x/Static.lightYearsToMillionKM)+zoom_x)*zoom),
								(((positionApproximation.get(0).y/Static.lightYearsToMillionKM)+zoom_y)*zoom)
								);
			}
			for(Position dotp : positionApproximation){
				counter++;
				dotIter += dotValue;
				if(dotIter > 1){
					g.setColor(Color.gray);
					if(navComputer != null){
						if(navComputer.getBoost(counter).hour != -1){
							g.setColor(Color.RED);
							}					
					}
					dotIter -= 1;
					Position p2 = dotp.times(zoom*starScale);
					if(sp != null){p2 = getLocalPos(sp.currentStar, dotp).times(zoom).plus(Static.starCenterPos);}
					else if(gp != null){
						p2 = 
								new Position(
										(((dotp.x/Static.lightYearsToMillionKM)+zoom_x)*zoom),
										(((dotp.y/Static.lightYearsToMillionKM)+zoom_y)*zoom)
										);
					}
					g.drawLine((int)p2.x, (int)p2.y, (int)lastPos.x, (int)lastPos.y);
					lastPos = p2;
				}


			}
		}

		
		if(starSpacePosition.x < 0){
			g.drawString("X IS: " + starSpacePosition.x, (int)(Static.starCenterPos.x)+100, (int)(Static.starCenterPos.y+400));
			
		}
	}
	
	
	
	
	/**
	 * Set the current spaceCraft position to the one it will have at specified time from the start of time.
	 * @param hours (double) the time of which to set the spaceCraft at.
	 */
	public void setDatePos(double hours){
		hours -= launchDay*24;
		if(positionApproximation.size() > 10){
			if(hours >= 0 && hours < positionApproximation.size()){
				starSpacePosition = positionApproximation.get((int)hours).clone();
				trajectory = trajectoryApproximation.get((int)hours).clone();
			}
			else{
				starSpacePosition = new Position(-999, -999);
			}
		}
	}
	

}













