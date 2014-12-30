package Space;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Random;

import GUI.SPanel;
import Static.Physics;
import Static.Position;
import Static.Static;

public class Planet {

	
	public int planetId;
	public Star star;
	public int age;
	//temperatures
	public int coreTemp;
	public double surfaceTemp;
	public double dayDeviation;
	public double summerDeviation;
	
	public double distance;
	public double magneticField;
	public double radius;
	public double density;
	public double mass;
	public double atmosphere;
	public double gravity;
	public boolean gasGiant;	
	public boolean life;
	public Double kg;	
	
	public Color color;
	public SPanel spanel;
	public Position planetPos; 	
	public Position trajectory;
	

	public double starmass=330000;
	
	public Planet(Star s, Random rand, SPanel sp, int id) 
	{		
		planetId = id;
		star = s;
		spanel = sp;
		distance = 50+rand.nextInt(Static.MAX_SOLAR_DISTANCE);
		
		setSpecs(s, rand);
		DressPlanet();
		planetPos=new Position(Static.starPos.x + distance, Static.starPos.y);
		trajectory=new Position(0, Math.sqrt(Static.GRAVITY_CONSTANT *starmass/(planetPos.x-Static.starPos.x))*Math.sqrt(Static.TIMEFACTOR)); // direction vector
		trajectory=trajectory.times(0.95+(0.1*rand.nextDouble()));
		
		double degrees = rand.nextInt(360);
		planetPos.rotate(distance, degrees);
		trajectory.rotate(radius, degrees, 0, 0);
		//printSpecs();
	}

	public void setSpecs(Star s, Random rand){
		
		age = (int) (s.age - ((s.age*9/10)*rand.nextDouble()));		
		radius = rand.nextInt(Physics.PLANET_MAX_KM_RADIUS);
		
		Physics.calculatePlanetDensity(this, s.magnitude, rand);	
		Physics.calculatePlanetMass(this);
		Physics.calculatePlanetMagneticField(this);
		Physics.calculatePlanetAtmosphere(this);
		Physics.calculatePlanetSurfaceTemperature(this);

		

		

		
		if(surfaceTemp > 283 && surfaceTemp < 323){
			life = true;
		}
	}
	
	public void Paint(Graphics g)
	{
		g.setColor(color);
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		double gasGiantBonus = 1;
		if(gasGiant){gasGiantBonus = 3;}
		int projectedRadius = (int) (gasGiantBonus*Math.sqrt(radius)/10);
		setDevColor(g);
		//g.setColor(color);
		g.fillOval((int)(planetPos.x-projectedRadius), (int)(planetPos.y-projectedRadius), (int)(2*projectedRadius), (int)(2*projectedRadius));
		g.drawImage(Static.planet02.getImage(), (int)(planetPos.x-projectedRadius), (int)(planetPos.y-projectedRadius), (int)(2*projectedRadius), (int)(2*projectedRadius), null);
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		printPlanetStats(g);
	}
	
	public void UpdateTrajectory()
	{
		double distance=planetPos.distance(Static.starPos);
		//Position target=starPos.minus(planetPos); 
		//target.normalize();
		double acc=(Static.GRAVITY_CONSTANT*starmass)/(distance*distance);
		Position delta=Static.starPos.minus(planetPos);
		double theta=Math.atan2(delta.y, delta.x);
		Position a0=new Position(acc*Math.cos(theta), acc*Math.sin(theta));
		Position a1=a0.times(Static.TIMEFACTOR);
		trajectory.add(a1);
		planetPos.add(trajectory);
	}
	
	public void DressPlanet()
	{
		Static.calculatePlanetColor(this);
		
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
	
	public void printPlanetStats(Graphics g){
		int fontSize = 20;
		g.setFont(new Font(Static.DISTANCE_RULER_FONT, Font.PLAIN, 10));
		
		//g.drawString("Id: " + planetId, (int)planetPos.x+10, (int)planetPos.y+(0*fontSize));
		g.drawString("Distance: " + distance + " M km", (int)planetPos.x+10, (int)planetPos.y+(0*fontSize));
		//g.drawString("Mass: " + (double)((int)(mass*100)/100.0), (int)planetPos.x+10, (int)planetPos.y+(2*fontSize));
		g.drawString("Temp: " + (double)((int)((surfaceTemp-273)*10)/10.0) + " C ", (int)planetPos.x+10, (int)planetPos.y+(1*fontSize));
		//g.drawString("Atmosphere: " + atmosphere + " bar", (int)planetPos.x+10, (int)planetPos.y+(4*fontSize));
		//g.drawString("Life: " + life, (int)planetPos.x+10, (int)planetPos.y+(5*fontSize));
		
		
		
		
	}
	
}










