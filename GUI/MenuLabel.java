package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import Space.Star;
import Static.Static;

public class MenuLabel {
	public Menu menu;
	public Font f1;
	public Color color;
	
	int x;
	int y;
	int length;
	int height;
	String text;
	
	boolean clicked = false;
	boolean isDown = false;
	boolean f = false;
	
	public MenuLabel(Menu m, String label, Color c){
		menu = m;
		text = label;
		this.color = c;
	}
	

	
	public void paint(Graphics g){
		Star s = menu.gpanel.galax.selected_star;
		
		if(text.equals(Static.STAR_SPECS_LABEL) && s != null){
			g.setColor(Color.red);
			g.drawString("STAR DATA HERE", 1000, 400);
			
		}
	}
	

	
}




