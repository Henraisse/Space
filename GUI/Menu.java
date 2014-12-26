package GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import Space.Star;
import Static.Static;

public class Menu {
	public ImageIcon image1;
	public ImageIcon image2;
	public Image menuImg;
	public GPanel panel;
	
	int x0;
	int x1;
	int y0;
	int y1;
	
	public String currentStarName = "";
	
	boolean f = false;
	
	ArrayList<MenuButton> buttons = new ArrayList<MenuButton>();
	
	public Menu(GPanel p, int x0, int y0, int x1, int y1){

		panel = p;
		this.x0 = x0;
		this.x1 = x1;
		this.y0 = y0;
		this.y1 = y1;
		image1 = new ImageIcon("menu1.png");
		menuImg = image1.getImage();
		
		//set up buttons
		MenuButton b1 = new MenuButton(this, 20, 142, 132, 50, Static.SELECT_BUTTON_TEXT, Static.ruler_font18);
		MenuButton b2 = new MenuButton(this, 20, 197, 132, 50, Static.NAME_BUTTON_TEXT , Static.ruler_font18);
		MenuButton b3 = new MenuButton(this, 20, 252, 132, 50, Static.EXAMINE_BUTTON_TEXT , Static.ruler_font18);
		buttons.add(b1);
		buttons.add(b2);
		buttons.add(b3);
	}
	
	public boolean clickedAt(double x, double y){

		if(withinBounds((int) x, (int) y)){
			for(MenuButton m : buttons){
				m.clickedAt(x,y);
				
			}
			return true;
		}
		return false;
	}
	
	public boolean withinBounds(int x, int y){
		if(x0 < x && x < x1 && y0 < y && y < y1){
			return true;
		}
		return false;
	}
	
	public void paint(Graphics g){
		((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		//g.drawLine(1000, 0, 2000, 1200);
		g.setColor(Color.blue);
		if(f){
			g.setColor(Color.red);
		}
		g.drawImage(menuImg,x0,y0,510,1080, null);
		//g.drawRect(x0, y0, x1-x0, y1-y0);
		
		g.setColor(Color.blue);
		for(MenuButton m : buttons){
			m.paint(g);
		}
		
		//Om det finns en stjärna att visa
		if(panel.galax.selected_star != null){			
			Star s = panel.galax.selected_star;
			
			//Draw star name and nickname
			g.setColor(Static.STAR_NAME_COLOR);
			g.setFont(Static.ruler_font24);
			g.drawString(currentStarName, 1440, 50);
			g.drawString("''"+ s.nick + "''", 1440, 75);
			
			//

			g.setFont(Static.ruler_font22);
			g.drawString("Age: ", 1440, 474);
			g.drawString("Size: ", 1440, 504);
			g.drawString("Mass: ", 1440, 534);
			g.drawString("Temp: ", 1440, 564);
			g.setColor(Color.red);
			g.drawString(s.age + " million years", 1560, 474);
			g.drawString((s.radius*2) + "000 km diameter ", 1560, 504);
			g.drawString(s.mass + "000 EM", 1560, 534);
			g.drawString(s.temperature + " Kelvin", 1560, 564);
		}
		((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
	}
}











