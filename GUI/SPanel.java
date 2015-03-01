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
import Static.Game;
import Static.Physics;
import Static.Position;
import Static.Static;
import Tech.Booster;
import Tech.NavComputer;
import Tech.SpaceCraft;


public class SPanel extends JPanel{
	public Frame frame;
	public Galaxy galaxy;
	public Game game;
	
	public Star currentStar;
	Star sun;
	//SpaceObject voyager;
	//Booster atlas5;
	//NavComputer mother;
	double rotationSpeed = 0.5;
	//public boolean visible = false;
	Menu menu = new Menu(this, 1410,0,1900,1200);

	ArrayList<Planet> planets=new ArrayList<Planet>();
	ArrayList<SpaceCraft> presentSpacecrafts = new ArrayList<SpaceCraft>();
	SpaceCraft currentSpaceCraft;
	
	public double zoom_scale = 1.0;
	public boolean active = false;
	
	
	
	public boolean display_orbits = true;
	public boolean display_descriptions;
	public boolean manual_orbits = false;
	
	
	public double days = 1;

	public SPanel(Frame f, Galaxy g, Game ga) {		
		game = ga;
		frame = f;
		galaxy = g;		
		//sun = g.sun;
		super.setBackground(Color.black);
		MenuButton a0 = new MenuButton(0, menu, 170, 300, Static.DISPLAY_ORBITS_BUTTON_TEXT, Static.ruler_font12, "button");
		MenuButton a1 = new MenuButton(1, menu, 170, 355, Static.DISPLAY_DESCRIPTION_TEXT, Static.ruler_font12, "button");
		MenuButton a2 = new MenuButton(2, menu, 170, 410,  Static.MANUAL_ORBIT_SWITCH_TEXT, Static.ruler_font12, "button");		
		
		
		MenuButton a3 = new MenuButton(3, menu, 85-60, 940+60,  "", Static.ruler_font12, "left");
		MenuButton a4 = new MenuButton(4, menu, 85+60, 940+60,  "", Static.ruler_font12, "right");
		MenuButton a5 = new MenuButton(5, menu, 85, 940,  "", Static.ruler_font12, "up");
		MenuButton a6 = new MenuButton(7, menu, 85, 940+60*1,  "", Static.ruler_font12, "down");
		
		MenuButton a7 = new MenuButton(8, menu, 85+(60*3), 940+60*1,  "", Static.ruler_font12, "resettime");
		MenuButton a8 = new MenuButton(9, menu, 85+(60*4), 940+60*1,  "", Static.ruler_font12, "reference");
		MenuButton a9 = new MenuButton(10, menu, 85+(60*5), 940+60*1,  "", Static.ruler_font12, "resetorbit");
//		menu.buttons.add(a0);		
//		menu.buttons.add(a1);		
//		menu.buttons.add(a2);
//		menu.buttons.add(a3);
//		menu.buttons.add(a4);
//		menu.buttons.add(a5);
//		menu.buttons.add(a6);
//		menu.buttons.add(a7);
		
		MenuButton b0 = new MenuButton(3, menu, 170, 610,  "", Static.ruler_font12, "squareButton");
//		menu.buttons.add(b0);
	}

	public void paintComponent(Graphics g)
	{				
		super.paintComponent(g);
		g.drawImage(Static.StarSystemBackground01.getImage(), 0, 0, 2000, 1100, null);
		
		
		
		
		for(Planet p:planets)
		{
			p.Paint(g, zoom_scale, display_orbits, display_descriptions);
		}
		
		currentStar.drawStarCenter(g, zoom_scale, display_descriptions);
		
		
		//voyager.Paint(g, zoom_scale);
		for(SpaceCraft sc : game.spacecrafts){
			if(currentStar.star_id == sc.star_id){
				sc.object.Paint(g, zoom_scale, 0, 0, null, this);
			}
			
		}
		//currentSpaceCraft.object.Paint(g, zoom_scale);
		
		menu.paint(g);
		g.setFont(Static.ruler_font18);
		g.setColor(Color.red);
		g.drawString("Planets: " + planets.size(), 1750, 600);
		g.drawString("Star ID: " + currentStar.star_id, 1750, 640);
		g.drawImage(Static.boosters.getImage(), 10, 10, 238, 552, null);
	}
	
	
	public void setStar(Star s){		
		if(s != null){
			//Static.calculateNeighbors(s);

			currentStar = s;
			planets.clear();
			planets.trimToSize();
			
			planets = Static.getStarPlanets(s);
			//voyager.closestStar = s;
			//voyager.calculateTrajectoryArc();
			//System.out.println("neighbors: " + s.neighbors.size());
		}	
		
		//set the present spacecrafts and determine which to initially focus on
		presentSpacecrafts.clear();
		for(int i = 0; i < game.spacecrafts.size(); i++){
			SpaceCraft sc = game.spacecrafts.get(i);
			//if(sc.star_id == currentStar.star_id){
			
			sc.object.switchStar(s);
			presentSpacecrafts.add(sc);			
				//sc.object.closestStar = s;
			sc.object.calculateTrajectoryArc();
			
			//}
		}
		if(presentSpacecrafts.size() > 0){
			currentSpaceCraft = presentSpacecrafts.get(0);
		}
		
	}
	
		
	


	
	public void Update()
	{
		try{
		if(true){
			//System.out.println(days);
//			if(days < 0){
//				System.out.println(days);
//			}
			if(manual_orbits){							
				days += rotationSpeed;
			}

			//voyager.setDatePos(days*24);
			for(SpaceCraft sc: presentSpacecrafts){
				sc.object.setDatePos(days*24);
			}
			
//			if(currentSpaceCraft != null){
//				currentSpaceCraft.object.setDatePos(days*24);
//			}
			
			
			for(Planet p:planets)
			{
				p.setDatePos(days*24);

			}
		}
		}catch(ConcurrentModificationException e){
			
		}
	}
}
