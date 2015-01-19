package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import Space.Star;
import Static.Static;

public class MenuDisplay {
	public Menu menu;
	String text;
	
	public MenuDisplay(Menu m, String text){
		this.text = text;
		this.menu = m;
	}
	
	public void paint(Graphics g){
		g.setColor(Color.black);
		if(text.equals(Static.GALAXY_STAR_DISPLAY) && menu.gpanel.galax.selected_star != null){
			Star s = menu.gpanel.galax.selected_star;
			s.drawStarDisplay(g, s.radius*Static.STAR_DISPLAY_SIZE_MULTIPLIER, Static.STAR_DISPLAY_POS);
			//g.setColor(Color.red);
			//g.drawImage(menuImg,x0,y0,510,1080, null);
		}
		//g.drawRect(x, y, length, height);


//		g.drawImage(menuImg,x,y,132,50, null);
//
//		g.setFont(f1);
//		g.drawString(text, x+10, y+height-(25-f1.getSize()/2));
	}
	

	
}




