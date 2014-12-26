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
	
	boolean clicked = false;
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
			clicked = true;
			action();
			//System.out.println(text);
			menuImg = image2.getImage();
			f = true;
			return true;
		}
		else{
			f = false;
			menuImg = image1.getImage();
			return false;
		}
	}
	
	public void paint(Graphics g){
		g.setColor(Color.black);
		if(f){
			g.setColor(Color.red);
			//g.drawImage(menuImg,x0,y0,510,1080, null);
		}
		//g.drawRect(x, y, length, height);


		g.drawImage(menuImg,x,y,132,50, null);

		g.setFont(f1);
		g.drawString(text, x+10, y+height-(25-f1.getSize()/2));
	}
	
	public void action(){
		if(text.equals(Static.EXAMINE_BUTTON_TEXT)){
			Frame frame = menu.panel.frame;			
			frame.remove(frame.gpanel);
			frame.add(frame.spanel);
			Star s = frame.gpanel.galax.selected_star;
			frame.spanel.setStar(s);
		}

		
	}
	
}




