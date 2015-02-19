package Space;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import GUI.GPanel;
import GUI.SPanel;
import Static.Physics;
import Static.Position;
import Static.Static;
import Tech.Booster;
import Tech.NavComputer;
import Tech.SpaceCraft;

public class SpaceObject {
	
	int insertDay;
	
	public Star closestStar;
	
	public SpaceCraft spacecraft;
	public NavComputer navComputer;
	Booster booster;
	Position currentPosition;
	Position currentTrajectory;
	
	
	
	public Position starSpacePosition;
	
	
	Position trajectory;
	public ArrayList<Integer> boosters = new ArrayList<Integer>();
	
	public ArrayList<Position> trajectoryApproximation = new ArrayList<Position>();
	public ArrayList<Position> positionApproximation = new ArrayList<Position>();

	public double orbits = 0;
	
	//public SpaceObject(int day, Position p, Position t, Star s, NavComputer nc, Booster b){
	public SpaceObject(int day, Position p, Position t, NavComputer nc, Booster b, SpaceCraft sc){
		spacecraft = sc;
		booster = b;
		navComputer = nc;
		booster.object = this;		
		navComputer.object = this;
		
		
		//System.out.println("navcomputer set. " + navComputer.name + ", now SpaceObject have a navcomputer who isn't null.");
		//closestStar = s;
		insertDay = day;
		starSpacePosition = p.clone();
		currentPosition = p.clone();
		
		//trajectory = new Position(0, 0.18338908242143742);
		trajectory = t;
		currentTrajectory = trajectory.clone();
		
	}
	
	/**
	 * Adds a momentary acceleration to the trajectory. For example to burn engines for awhile, use this
	 * method for some iterations.
	 * @param p
	 */
	public void alterTrajectory(Position p){
		trajectory.add(p);
	}
	
	

	
	public void gravity(double speed, ArrayList<Position> objPoss, double mass, int approxIndex, boolean print)
	{
		Position currentPos = positionApproximation.get(approxIndex).clone();		//vi läser av var vi står just nu
		Position currentTraj = trajectoryApproximation.get(approxIndex).clone();	//vi läser av vilken riktning vi åker i just nu
		if(print){System.out.print("\nDistance to stars: ");}
		for(Position p : objPoss){
			if(print){System.out.print(", " + currentPos.distance(p));}
			if(currentPos.minus(p).length() < 5000){	
				
				double distance=currentPos.distance(p);								//vi räknar ut avståndet till stjärnan						
				double acc=(Static.GRAVITY_CONSTANT*mass)/(distance*distance);				//vi räknar ut accelerationen till objektet
				Position delta=p.minus(currentPos);
				double theta=Math.atan2(delta.y, delta.x);
				Position direction = new Position(acc*Math.cos(theta), acc*Math.sin(theta));
				Position gravityVector = direction.times(Static.TIMEFACTOR*speed);
				currentTraj.add(gravityVector);
			}
		}
		
		currentTraj.add(getBoosterAdding(approxIndex, currentPos, Static.starCenterPos));
		positionApproximation.add(currentPos.plus(currentTraj.times(speed)));
		trajectoryApproximation.add(currentTraj);
	}
	


	
	private Position getBoosterAdding(int approxIndex, Position currPos, Position objPos) {
		Position boostAdd = new Position(0,0);
		
		approxIndex = (approxIndex+insertDay*24);
			boostAdd = booster.getBoost(approxIndex, currPos, objPos, trajectoryApproximation.get(approxIndex));
		return boostAdd;
	}

	
	
	
	
	public void jumpToOrbit(){
		Star s = closestStar.neighbors.get(0);
		Position starOffset = new Position(s.x, s.y).minus(new Position(closestStar.x, closestStar.y)).times(Static.lightYearsToMillionKM);
		currentPosition = starOffset.plus(Static.starCenterPos);
	}
	
	
	
	
	
	/**
	 * Calculate a trajectory arc based on the gravitational pull from stars and planets, along with the
	 * forces of the objects own propulsion
	 */
	public void calculateTrajectoryArc(){
		//System.out.println("CALCULATING TRAJECTORY: DRAW BY SUN: " + closestStar.mass);
		
		positionApproximation.clear();
		trajectoryApproximation.clear();
				
		//ArrayList<Planet> planets = Static.getStarPlanets(closestStar);
		
		positionApproximation.add(currentPosition.plus(Static.starCenterPos).clone());
		trajectoryApproximation.add(currentTrajectory.clone());
		//System.out.println("PRE-LOOP::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
		ArrayList<Position> objPosis = new ArrayList<Position>();
		objPosis.add(Static.starCenterPos);
		for(Star s : closestStar.neighbors){
			Position starOffset = new Position(s.x, s.y).minus(new Position(closestStar.x, closestStar.y)).plus(Static.starCenterPos.times(1.0/Static.lightYearsToMillionKM)).times(Static.lightYearsToMillionKM);
			objPosis.add(starOffset);
		}
		for(int i = 0; i < navComputer.memory; i++){
			
			
			boolean print = false;
			if(i == navComputer.memory-1){print = true;}
			gravity(1, objPosis, closestStar.mass, i, print);
			//ArrayList<Star> closest = closestStar.galax.getSector(closestStar);
			//closest.remove(closestStar);			

			
			
			

		}
		
	}
	
	public void Paint(Graphics g, double zoom, double zoom_x, double zoom_y, GPanel gp, SPanel sp){
		Position pp = starSpacePosition;
		Position p = new Position((pp.x-Static.starCenterPos.x)*zoom, (pp.y-Static.starCenterPos.y)*zoom);
		g.setColor(Color.red);
		Position starPos = new Position(0,0);
		Double starScale = 1.0;
		if(gp != null){
	
			//calculate the total position
			double posX = closestStar.x+zoom_x;
			double posY = closestStar.y+zoom_y;
												

			starPos = new Position((int)(posX*zoom), (int)(posY*zoom));
			
			
			starScale = 1.0/(Static.lightYearsToMillionKM);
			p = p.times(starScale);
			
		}
		else if(sp != null){
				starPos = Static.starCenterPos;
		}

		g.fillOval((int)(starPos.x+p.x-5), (int)(starPos.y+p.y-5), (int)(2*5), (int)(2*5));

		

		
		if(true){
			//first calculate number of prints
			int counter = 0;
			double dotValue = 1000.0/positionApproximation.size();
			double dotIter = 0;
			Position lastPos = positionApproximation.get(0).minus(Static.starCenterPos).times(zoom*starScale).plus(starPos);
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
					Position p2 = dotp.minus(Static.starCenterPos).times(zoom*starScale).plus(starPos);
					g.drawLine((int)p2.x, (int)p2.y, (int)lastPos.x, (int)lastPos.y);
					lastPos = p2;
				}


			}
		}
		
//		g.setColor(Color.gray);
//		for(Position p: positionApproximation){
//			g.drawLine((int)p.x, (int)p.y, (int)p.x, (int)p.y);
//		}
		
		if(starSpacePosition.x < 0){
			g.drawString("X IS: " + starSpacePosition.x, (int)(Static.starCenterPos.x)+100, (int)(Static.starCenterPos.y+400));
			
		}
	}
	
	public void setDatePos(double hours){
		hours -= insertDay*24;
		if(positionApproximation.size() > 10){
			if(hours >= 0 && hours < positionApproximation.size()){
				starSpacePosition = positionApproximation.get((int)hours).clone();
				trajectory = trajectoryApproximation.get((int)hours).clone();
			}
		}
	}
	

}














