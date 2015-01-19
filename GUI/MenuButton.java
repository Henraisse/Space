package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import Space.Star;
import Static.Static;

public class MenuButton {
	public ImageIcon image1;
	public ImageIcon image2;
	public Image menuImg;
	public Menu menu;
	
	public Font f1;
	
	int x;
	int y;
	int length;
	int height;
	String text;
	
	boolean isSwitch = false;
	boolean clicked = false;
	boolean isDown = false;
	boolean f = false;
	
	public MenuButton(Menu m, int x0, int y0, int length, int height, String text, Font f1){
		this.text = text;
		this.f1 = f1;
		menu = m;
		this.x = m.x0 + x0;
		this.y = m.y0 + y0;
		this.length = length;
		this.height = height;
		image1 = new ImageIcon("button01.png");
		image2 = new ImageIcon("button02.png");
		menuImg = image1.getImage();
	}
	
	public boolean clickedAt(double xi, double yi){

		if((x < xi && xi < (x+length) )&&( y < yi && yi < (y+height))){
			setDown();
			//clicked = true;
			//isDown = true;
			//action();
			//System.out.println(text);
			//menuImg = image2.getImage();
			//f = true;
			return true;
		}
		else{
			setUp();
			return false;
		}
	}
	
	public boolean releasedAt(double xi, double yi){
	
		if((x < xi && xi < (x+length) )&&( y < yi && yi < (y+height))){
			if(isDown == true){
				clicked = true;
				action();
			}
			setUp();
			//action();
			//System.out.println(text);
			//menuImg = image2.getImage();
			//f = true;
			return true;
		}
		setUp();
		return false;
	}
	
	
	public void setDown(){
		isDown = true;
		menuImg = image2.getImage();
		
	}
	
	public void setUp(){
		
		menuImg = image1.getImage();
		isDown = false;		
	}
	
	
	public void paint(Graphics g){
		g.setFont(f1);
				
		if(isDown || isSwitch){
			g.setColor(Color.red);
			g.drawImage(image2.getImage(),x,y,132,50, null);
		}
		else{
			g.setColor(Color.black);
			g.drawImage(image1.getImage(),x,y,132,50, null);
		}


		
		g.drawString(text, x+10, y+height-(25-f1.getSize()/2));
	}
	
	public void action(){
		if(text.equals(Static.EXAMINE_BUTTON_TEXT)){
			Frame frame = menu.gpanel.frame;			
			frame.remove(frame.gpanel);
			frame.add(frame.spanel);
			frame.spanel.active = true;
			frame.gpanel.active = false;
			//frame.gpanel.galax.selected_star = frame.spanel.currentStar;
			Star s = frame.gpanel.galax.selected_star;
			frame.spanel.setStar(s);
			frame.momentum = 0;
		}
		if(text.equals(Static.DISPLAY_ORBITS_BUTTON_TEXT)){
			menu.spanel.display_orbits = !menu.spanel.display_orbits;
		}
		if(text.equals(Static.DISPLAY_DESCRIPTION_TEXT)){
			menu.spanel.display_descriptions = !menu.spanel.display_descriptions;
		}
		if(text.equals(Static.MANUAL_ORBIT_SWITCH_TEXT)){
			isSwitch = !isSwitch;
			menu.spanel.manual_orbits = !menu.spanel.manual_orbits;
		}
	}
	
}




