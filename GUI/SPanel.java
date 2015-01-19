package GUI;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Random;

import javax.swing.JPanel;

import Space.Galaxy;
import Space.Planet;
import Space.SpaceObject;
import Space.Star;
import Static.Physics;
import Static.Position;
import Static.Static;


public class SPanel extends JPanel{
	public Frame frame;
	public Galaxy galaxy;
	
	public Star currentStar;
	Star sun;
	SpaceObject voyager;
	double rotationSpeed = 0.5;
	//public boolean visible = false;
	Menu menu = new Menu(this, 1410,0,1900,1200);

	ArrayList<Planet> planets=new ArrayList<Planet>();
	public double zoom_scale = 1.0;
	public boolean active = false;
	
	
	
	public boolean display_orbits = true;
	public boolean display_descriptions;
	public boolean manual_orbits = false;
	
	
	public double days;

	public SPanel(Frame f, Galaxy g) {				
		frame = f;
		galaxy = g;
		
		sun = g.sun;
		voyager = new SpaceObject(0, new Position(150, 0), sun );
		
		
		super.setBackground(Color.black);
		//(Menu m, int x0, int y0, int length, int height, String text, Font f1){
		MenuButton b0 = new MenuButton(menu, 170, 300, 200, 50, Static.DISPLAY_ORBITS_BUTTON_TEXT, Static.ruler_font12);
		menu.buttons.add(b0);
		MenuButton b1 = new MenuButton(menu, 170, 355, 200, 50, Static.DISPLAY_DESCRIPTION_TEXT, Static.ruler_font12);
		menu.buttons.add(b1);
		MenuButton b2 = new MenuButton(menu, 170, 410, 200, 50, Static.MANUAL_ORBIT_SWITCH_TEXT, Static.ruler_font12);
		menu.buttons.add(b2);
	}

	public void paintComponent(Graphics g)
	{				
		super.paintComponent(g);
		g.drawImage(Static.StarSystemBackground01.getImage(), 0, 0, 2000, 1100, null);
		
		g.setFont(Static.ruler_font18);
		g.drawString("Planets: " + planets.size(), 1750, 600);
		
		for(Planet p:planets)
		{
			p.Paint(g, zoom_scale, display_orbits, display_descriptions);
		}
		
		currentStar.drawStarCenter(g, zoom_scale, display_descriptions);
		
		
		voyager.Paint(g);
		
		
		menu.paint(g);
	}
	
	
	public void setStar(Star s){

		if(s != null){
			Static.calculateNeighbors(s);
			currentStar = s;
			planets.clear();
			planets.trimToSize();
			
			planets = Static.getStarPlanets(s);
			voyager.closestStar = s;
			voyager.calculateTrajectoryArc();
		}
		//visible = true;		
	}
	
	


	
	public void Update()
	{
		try{
		if(active){
			//System.out.println(days);
			if(days < 0){
				//System.out.println(days);
			}
			if(manual_orbits){							
				days += rotationSpeed;
			}

			voyager.setDatePos(days);
			
			for(Planet p:planets)
			{
				p.setDatePos(days);

			}
		}
		}catch(ConcurrentModificationException e){
			
		}
	}
}
