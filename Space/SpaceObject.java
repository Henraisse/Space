package Space;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import Static.Physics;
import Static.Position;
import Static.Static;

public class SpaceObject {
	
	int insertDay;
	
	public Star closestStar;
	

	Position starSpacePosition;
	
	
	Position trajectory;
	ArrayList<Position> trajectoryApproximation = new ArrayList<Position>();
	ArrayList<Position> positionApproximation = new ArrayList<Position>();
	
	public SpaceObject(int day, Position p, Star s){
		closestStar = s;
		insertDay = day;
		starSpacePosition = p;
		
		//trajectory = new Position(0, 0.18338908242143742);
		trajectory = new Position(0, 0.1057);
	}
	
	/**
	 * Adds a momentary acceleration to the trajectory. For example to burn engines for awhile, use this
	 * method for some iterations.
	 * @param p
	 */
	public void alterTrajectory(Position p){
		trajectory.add(p);
	}
	
	

	
	public void gravity(double speed, Position objPos, double mass, int approxIndex)
	{
		Position currentPos = positionApproximation.get(approxIndex);
		Position currentTraj = trajectoryApproximation.get(approxIndex);
		//System.out.println("CurrentPos: " + currentPos);
		double distance=currentPos.distance(objPos);

		double acc=(Static.GRAVITY_CONSTANT*mass)/(distance*distance);
		Position delta=objPos.minus(currentPos);
		double theta=Math.atan2(delta.y, delta.x);
		Position a0=new Position(acc*Math.cos(theta), acc*Math.sin(theta));
		Position a1=a0.times(Static.TIMEFACTOR*speed);
		currentTraj.add(a1);
		//System.out.println(trajectory.length());
		currentPos.add(currentTraj.times(speed));
		
		positionApproximation.add(currentPos.clone());
		trajectoryApproximation.add(currentTraj.clone());
	}
	

	public void updateOrbit(ArrayList<Planet> planets, int approxIndex){
		//trajectoryApproximation.get(approxIndex).add(new Position(0, -.00001));
		gravity(1, Static.starCenterPos, closestStar.mass, approxIndex);
		//trajectory.add(new Position(0, -.00001));
	}
	
	
	/**
	 * Calculate a trajectory arc based on the gravitational pull from stars and planets, along with the
	 * forces of the objects own propulsion
	 */
	public void calculateTrajectoryArc(){

		positionApproximation.clear();
		trajectoryApproximation.clear();
				
		ArrayList<Planet> planets = Static.getStarPlanets(closestStar);
		
		positionApproximation.add(starSpacePosition.plus(Static.starCenterPos).clone());
		trajectoryApproximation.add(trajectory.clone());
		System.out.println("PRE-LOOP::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
		for(int i = 0; i < 10000; i++){
			
			//gravity(1, Static.starCenterPos, closestStar.mass);
			//UpdateTrajectory(1);
			updateOrbit(planets, i);
		}
		System.out.println("POST-LOOP::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
		starSpacePosition = positionApproximation.get(0).clone();
		trajectory = trajectoryApproximation.get(0).clone();
		
	}
	
	public void Paint(Graphics g){
		Position pp = starSpacePosition;
		
		g.setColor(Color.red);
		g.fillOval((int)pp.x-5, (int)pp.y-5, 10, 10);
		
		//g.drawString((pp.x + " " + pp.y), 200, 700);
		
		g.setColor(Color.gray);
		for(Position p: positionApproximation){
			g.drawLine((int)p.x, (int)p.y, (int)p.x, (int)p.y);
		}
		
		if(starSpacePosition.x < 0){
			g.drawString("X IS: " + starSpacePosition.x, (int)(Static.starCenterPos.x)+100, (int)(Static.starCenterPos.y+400));
			
		}
	}
	
	public void setDatePos(double days){
		int index = (int)((days - insertDay)*Static.ITERATIONS_PER_DAY);
		//System.out.println("days: " + days + "      index: " + index);
		//int its = (int) (days);
		if(index < 0 || (index >= positionApproximation.size() && positionApproximation.size() > 10)){
			//System.out.println("WOW INDEX OFF!!   " + index);
			//starSpacePosition = new Position(-99999, -99999);
			//trajectory = new Position(0, 0);
			
		}
		else if(positionApproximation.size() > 10){
			if(index == 0){
				
			}
			//System.out.println("WOW INDEX ON!!");
			starSpacePosition = positionApproximation.get(index).clone();
			trajectory = trajectoryApproximation.get(index).clone();
			System.out.println("SSP: " + starSpacePosition);
		}

		
//		for(int i = 0; i < its; i++){
//			UpdateTrajectory(1);
//		}
		
	}
	

}














