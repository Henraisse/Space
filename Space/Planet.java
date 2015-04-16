package Space;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;

import GUI.SPanel;
import Static.Physics;
import Static.Position;
import Static.Static;

public class Planet {

	
	public int planetId;
	public Star star;
	public int age;
	
	public double albedo;
	public double greenhouse;
	//temperatures
	public int orbitalPeriodDays = 0;
	public int orbitalPeriodHours = 0;
	public int coreTemp;
	public double surfaceTemp;
	public double dayDeviation;
	public double summerDeviation;
	
	public ImageIcon image;
	
	public int orbitIterations = 0;
	public double distance;
	public double magneticField;
	public double radius;
	public double density;
	public double mass;
	public double atmosphere;
	public double gravity;
	public boolean gasGiant;	
	public boolean water;
	public boolean life;
	public Double kg;	
	
	public boolean selected;
	
	public Color color = Color.BLACK;
	//public SPanel spanel;
	public Position planetPos; 	
	public Position trajectory;
	
	public Position startPosition;
	public ArrayList<Position> orbitPositions = new ArrayList<Position>();
	public ArrayList<Position> orbitTrajectories = new ArrayList<Position>();
	//public double starmass=330000;
	
	public Planet(Star s, Random rand, int id, boolean earth, ArrayList<Planet> planets) 
	{		
		planetId = id;
		star = s;
		//spanel = sp;
		
		water = rand.nextBoolean();
		//randomizing the distance of the current planet
		int min = (int) Physics.getMinPlanetDistance(s);
		int max = (int) Physics.getMaxPlanetDistance(s);
		
		//min = 50;
		//max = 500;
		//if(max > 1000){System.out.println(max);}
		
		boolean distanceSet = false;
		while(!distanceSet){
			distanceSet = true;
			distance =  min + rand.nextInt(max-min);
			for(Planet p: planets){
				if(Math.abs(p.distance-distance) < 25){
					distanceSet = false;
				}			
			}
		}

		
		
		//distance = 50+rand.nextInt(Static.MAX_SOLAR_DISTANCE);


		if(earth){
			if(id == 0){
				//VENUS
				distance = 107.5;
				albedo = 0.750; 
				atmosphere = 92.0;
				image = Static.venus;
				radius = 6051;

			}
			if(id == 1){
				//EARTH
				distance = 148.9;
				albedo = 0.306;
				atmosphere = 1.0;
				image = Static.earth;
				radius = 6371;

			}
			if(id == 2){
				//MARS
				distance = 226.9;
				albedo = 0.250;
				atmosphere = 0.01;
				image = Static.mars;
				radius = 3396;

			}
		}
		setPlanetSpecs(s, rand, earth);
		setTrajectory(rand, earth);
		calculateOrbit();
		DressPlanet(earth);
	}

	private void calculateOrbit() {
		boolean half = false;

		orbitPositions.add(planetPos.clone());
		orbitTrajectories.add(trajectory.clone());
		
		Position startPosition = planetPos.clone();
		Position lastPosition = planetPos.clone();
		
		double lastDistance = 0;
		double currentDistance = 0;
		
		//System.out.println();
		while(!half){
			lastPosition.clone(planetPos);
			
			UpdateTrajectory(1);
			orbitIterations++;
			
			
			orbitPositions.add(planetPos.clone());
			orbitTrajectories.add(trajectory.clone());			
			
			currentDistance = planetPos.distance(startPosition);
			if(lastDistance > currentDistance){
				half = true;
			}
			lastDistance = currentDistance;
		}
		while(half){
			lastPosition.clone(planetPos);


			UpdateTrajectory(1);
			orbitIterations++;
			
			
			orbitPositions.add(planetPos.clone());
			orbitTrajectories.add(trajectory.clone());
			
			currentDistance = planetPos.distance(startPosition);
			if(lastDistance < currentDistance){
				half = false;
			}
			lastDistance = currentDistance;
		}
		
		planetPos = orbitPositions.get(0).clone();
		trajectory = orbitTrajectories.get(0).clone();
		orbitalPeriodDays = (int) (orbitIterations/Static.ITERATIONS_PER_DAY);
		orbitalPeriodHours = orbitIterations;
		//iterationsPerDay = 14;
	}

	public void setPlanetSpecs(Star s, Random rand, boolean earth){
		
		age = (int) (s.age - ((s.age*9/10)*rand.nextDouble()));		
		if(!earth){
		radius = rand.nextInt(Physics.PLANET_MAX_KM_RADIUS-1000) + 1000;
		}
		
		Physics.calculatePlanetDensity(this, s.magnitude, rand);	
		Physics.calculatePlanetMass(this);
		Physics.calculatePlanetMagneticField(this);
		if(!earth){
			Physics.calculatePlanetAtmosphere(this, rand);
		}
		
		Physics.calculatePlanetSurfaceTemperature(this);

		

		

		
		if(surfaceTemp > 283 && surfaceTemp < 323){
			life = true;
		}
	}
	
	public void Paint(Graphics g, double zoom, boolean display_orbits, boolean descriptions)
	{
		
		g.setColor(new Color(25, 25, 25));
		Position orbitPos = new Position(0,0);
		if(display_orbits){
			//first calculate number of prints
			double dotValue = 1000.0/orbitPositions.size();
			double dotIter = 0;
			for(Position pos : orbitPositions){
				dotIter += dotValue;
				if(dotIter > 1){
					dotIter -= 1;
					orbitPos = pos.minus(Static.starCenterPos).times(zoom).plus(Static.starCenterPos);
					g.drawLine((int)(orbitPos.x), (int)(orbitPos.y), (int)(orbitPos.x), (int)(orbitPos.y));
				}


			}
		}
		
		Position p = new Position((planetPos.x-Static.starCenterPos.x)*zoom, (planetPos.y-Static.starCenterPos.y)*zoom);
		
		g.setColor(color);
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		double gasGiantBonus = 1;
		//if(gasGiant){gasGiantBonus = 2;}
		int projectedRadius = (int) (gasGiantBonus*zoom*Math.sqrt(radius)/10.0);
		if(projectedRadius < 1){projectedRadius = 1;}
		//setDevColor(g);
		g.setColor(color);		
		g.fillOval((int)(Static.starCenterPos.x+p.x-projectedRadius), (int)(Static.starCenterPos.y+p.y-projectedRadius), (int)(2*projectedRadius), (int)(2*projectedRadius));
		g.drawImage(image.getImage(), (int)(Static.starCenterPos.x+p.x-projectedRadius), (int)(Static.starCenterPos.y+p.y-projectedRadius), (int)(2*projectedRadius), (int)(2*projectedRadius), null);
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		printPlanetStats(g, zoom, projectedRadius, descriptions);
		

	}
		
	public void setDatePos(double hours){
//		while(days < 0){
//			days += orbitalPeriod;
//		}
		
		hours %= orbitalPeriodHours;
		if(hours > 0){
			planetPos = orbitPositions.get((int)hours).clone();
			trajectory = orbitTrajectories.get((int)hours).clone();
		}
		//int its = (int) (days*Static.ITERATIONS_PER_DAY);

		

		
//		for(int i = 0; i < its; i++){
//			UpdateTrajectory(1);
//		}
		
	}
	
	public void setTrajectory(Random rand, boolean earth){
		planetPos=new Position(Static.starCenterPos.x + distance, Static.starCenterPos.y);
		trajectory=new Position(0, Math.sqrt(Static.GRAVITY_CONSTANT *star.mass/(planetPos.x-Static.starCenterPos.x))*Math.sqrt(Static.TIMEFACTOR)); // direction vector
		//System.out.println("length of initial trajectory: " + trajectory.length());
		if(!earth){
			trajectory=trajectory.times(0.95+(0.1*rand.nextDouble()));
		}
		
		
		double degrees = rand.nextInt(360);
		planetPos.rotate(distance, degrees);
		trajectory.rotate(radius, degrees, 0, 0);
	}
	
	
	public void UpdateTrajectory(double speed)
	{
		double distance=planetPos.distance(Static.starCenterPos);
		//Position target=starPos.minus(planetPos); 
		//target.normalize();
		double acc=(Static.GRAVITY_CONSTANT*star.mass)/(distance*distance);
		Position delta=Static.starCenterPos.minus(planetPos);
		double theta=Math.atan2(delta.y, delta.x);
		Position a0=new Position(acc*Math.cos(theta), acc*Math.sin(theta));
		Position a1=a0.times(Static.TIMEFACTOR*speed);
		trajectory.add(a1);
		planetPos.add(trajectory.times(speed));
	}
	
	public void DressPlanet(boolean earth)
	{
		if(!earth){
			Static.calculatePlanetColor(this);
		}
		
	}
	
	public void printSpecs(){
		System.out.println("age: " + age + " million years");
		System.out.println("density: " + (int)(density) + " kg/m3");
		System.out.println("mass: " + ((int)(mass*100.0)/100.0) + " earth masses");
		System.out.println("surfaceTemp: " + ((int)(surfaceTemp*10)/10.0) + " Kelvin");
		System.out.println("Planet id: " + planetId);
		System.out.println("----------------------------------");
	}
	
	public void setDevColor(Graphics g){
		if(life){
			g.setColor(Color.green);
		}
		else if(surfaceTemp < 373 && surfaceTemp > 273){
			g.setColor(Color.yellow);
		}
		else{
			g.setColor(Color.red);
		}		
		//g.setColor(Physics.getPlanetColor((int)surfaceTemp-273));
	}
	
	public void printPlanetStats(Graphics g, double zoom, double rad, boolean descriptions){		
		if(descriptions){
			int fontSize = 10;
			g.setFont(new Font(Static.DISTANCE_RULER_FONT, Font.PLAIN, fontSize));
			Position p = new Position((planetPos.x-Static.starCenterPos.x)*zoom, (planetPos.y-Static.starCenterPos.y)*zoom);
			setDevColor(g);
			if(selected){
				g.setColor(Color.blue);
			}
			g.drawString("Distance: " + distance + " M km", (int)(Static.starCenterPos.x+p.x+rad)+10, (int)(Static.starCenterPos.y+p.y+rad)+(0*fontSize));
			g.drawString("Temp: " + (double)((int)((surfaceTemp-273)*10)/10.0) + " C ", (int)(Static.starCenterPos.x+p.x+rad)+10, (int)(Static.starCenterPos.y+p.y+rad)+(1*fontSize));				
			g.drawString("Life: " + Static.yesNo(life), (int)(Static.starCenterPos.x+p.x+rad)+10, (int)(Static.starCenterPos.y+p.y+rad)+(2*fontSize));
			//g.drawString("Orbit: " + orbitalPeriod + " days", (int)(Static.starCenterPos.x+p.x+rad)+10, (int)(Static.starCenterPos.y+p.y+rad)+(3*fontSize));
			g.drawString("Orbit: " + orbitIterations + " iterations", (int)(Static.starCenterPos.x+p.x+rad)+10, (int)(Static.starCenterPos.y+p.y+rad)+(4*fontSize));
			//g.drawString("Orbit: " + (orbitIterations/orbitalPeriod) + " (iterations/day)", (int)(Static.starCenterPos.x+p.x+rad)+10, (int)(Static.starCenterPos.y+p.y+rad)+(5*fontSize));
			g.drawString("Radius: " + (int)(radius) + " km", (int)(Static.starCenterPos.x+p.x+rad)+10, (int)(Static.starCenterPos.y+p.y+rad)+(6*fontSize));
			
			
			g.setColor(Color.gray);
			String type = "rock";
			if(gasGiant){
				type = "gas giant";
				g.setColor(new Color(70, 95, 82));
			}
			g.drawString("Type: " + type, (int)(Static.starCenterPos.x+p.x+rad)+10, (int)(Static.starCenterPos.y+p.y+rad)+(7*fontSize));
		}
	}
	

}










