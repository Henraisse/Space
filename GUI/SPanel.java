package GUI;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

import Space.Galaxy;
import Space.Planet;
import Space.Star;
import Static.Position;
import Static.Static;


public class SPanel extends JPanel{
	
	public Frame frame;
	public Galaxy galaxy;
	
	public Star currentStar;
	Star sun = new Star(0,"", new Galaxy(0,0,1, 1, 1));
	Random rand=new Random();
	int numPlanets = 3;
	public boolean visible = false;
	

	ArrayList<Planet> planets=new ArrayList<Planet>();

	public SPanel(Frame f, Galaxy g) {
		frame = f;
		galaxy = g;
		
		sun.x = 950;
		sun.y = 500;
		sun.temperature = Static.G2;
		sun.radius = 7;
		int i;
		
		for (i=0; i<numPlanets; i++)
		{
			Planet p = new Planet(sun, rand, this, i, true);
			planets.add(p);
		}
		currentStar = sun;
		super.setBackground(Color.black);
	}

	public void paintComponent(Graphics g)
	{				
		super.paintComponent(g);
		g.drawImage(Static.StarSystemBackground01.getImage(), 0, 0, 2000, 1100, null);
		for(Planet p:planets)
		{
			p.Paint(g);
		}
		
		currentStar.drawStarCenter(g);
	}
	
	
	public void setStar(Star s){
		if(s != null){
			currentStar = s;
			planets.clear();
			planets.trimToSize();
			Random rand = new Random(s.star_id);
			numPlanets = rand.nextInt(10);
			
			for(int i = 0; i < numPlanets; i++){
				Planet p = new Planet(s, rand, this, i, false);
				planets.add(p);
			}
		}
		visible = true;		
	}
	
	
	public void Update()
	{
		if(visible){
			for(Planet p:planets)
			{
				p.UpdateTrajectory();
			}
		}		
	}
}
