package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import Space.Star;
import Static.Static;

public class Menu {
	public static int MENU_WIDTH = 510;
	public static int MENU_HEIGHT = 1080;
	
	public ImageIcon image1;
	public ImageIcon image2;
	public Image menuImg;
	public GPanel gpanel;
	public SPanel spanel;
	
	int x0;
	int x1;
	int y0;
	int y1;
	
	public String currentStarName = "";
	
	boolean f = false;
	
	ArrayList<MenuButton> buttons = new ArrayList<MenuButton>();
	ArrayList<MenuDisplay> displays = new ArrayList<MenuDisplay>();
	ArrayList<MenuLabel> labels = new ArrayList<MenuLabel>();
	
	public Menu(GPanel p, int x0, int y0, int x1, int y1){

		gpanel = p;
		this.x0 = x0;
		this.x1 = x1;
		this.y0 = y0;
		this.y1 = y1;
		image1 = new ImageIcon("menu1.png");
		menuImg = image1.getImage();
		
		//set up buttons
	}
	
	public Menu(SPanel p, int x0, int y0, int x1, int y1){

		spanel = p;
		this.x0 = x0;
		this.x1 = x1;
		this.y0 = y0;
		this.y1 = y1;
		image1 = new ImageIcon("menu1.png");
		menuImg = image1.getImage();
		
		//set up buttons
	}
	
	public void addButton(int i, int x, int y, int length, int height, String text, Font font, String type){
		MenuButton b1 = new MenuButton(i, this, x, y, text, font, type);
		buttons.add(b1);
	}
	
	public void addDisplay(){
		MenuDisplay md = new MenuDisplay(this, Static.GALAXY_STAR_DISPLAY);
		displays.add(md);
	}
	
	public void addLabel(String label){
		MenuLabel ml = new MenuLabel(this, label, Color.red);
		labels.add(ml);
	}
	
	public boolean downAt(double x, double y){

		if(withinBounds((int) x, (int) y)){
			for(MenuButton m : buttons){
				m.clickedAt(x,y);
				
			}
			return true;
		}
		return false;
	}
	
	public boolean releasedAt(double x, double y){

		if(withinBounds((int) x, (int) y)){
			for(MenuButton m : buttons){
				m.releasedAt(x,y);
				
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
		g.drawImage(menuImg,x0,y0,MENU_WIDTH, MENU_HEIGHT, null);
		if(gpanel != null){}
		else if(spanel != null){}
		//rita upp alla knappar
		g.setColor(Color.blue);
		for(MenuButton mb : buttons){
			mb.paint(g);
		}
		
		for(MenuDisplay md : displays){
			md.paint(g);
		}
		
		for(MenuLabel ml : labels){
			ml.paint(g);
		}
		
		
		((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
	}
}











