package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

import Space.Star;
import Static.Static;

public class MenuButton {
	public ImageIcon image1;
	public ImageIcon image2;
	public Image menuImg;
	public Menu menu;
	
	int id;
	public Font f1;
	public int x;
	public int y;
	public int length;
	public int height;
	public String text;
	
	public boolean isSwitch = false;
	public boolean clicked = false;
	public boolean isDown = false;
	public boolean f = false;
	
	public MenuButton(int i, Menu m, int x0, int y0, String text, Font f1, String type){
		id = i;
		this.text = text;
		this.f1 = f1;
		menu = m;
		this.x = m.x0 + x0;
		this.y = m.y0 + y0;

		setType(type);
		menu.buttons.add(this);
	}
	
	
	public void setType(String type){		
		image1 = new ImageIcon(type + "0.png");
		image2 = new ImageIcon(type + "1.png");
		length = image1.getIconWidth();
		height = image1.getIconHeight();
		menuImg = image1.getImage();
	}
	
	
	public boolean clickedAt(double xi, double yi){
		if((x < xi && xi < (x+length) )&&( y < yi && yi < (y+height))){
			isDown = true;
			return true;
		}
		else{
			isDown = false;
			return false;
		}
	}
	
	public boolean releasedAt(double xi, double yi){
	
		if((x < xi && xi < (x+length) )&&( y < yi && yi < (y+height))){
			if(isDown == true){
				clicked = true;
				action();
			}
			isDown = false;
			return true;
		}
		isDown = false;
		return false;
	}
	
//	
//	public void setDown(){
//		isDown = true;
//		menuImg = image2.getImage();
//		
//	}
//	
//	public void setUp(){
//		
//		menuImg = image1.getImage();
//		isDown = false;		
//	}
	
	
	public void paint(Graphics g){
		g.setFont(f1);				
		if(isDown || isSwitch){
			g.setColor(Color.red);
			g.drawImage(image2.getImage(),x,y,length,height, null);
		}
		else{
			g.setColor(Color.black);
			g.drawImage(image1.getImage(),x,y,length,height, null);
		}		
		g.drawString(text, x+10, y+height-(25-f1.getSize()/2));
	}
	
	
	
	public void action(){
		Frame frame = null;
		if(menu.gpanel != null){
			frame = menu.gpanel.frame;
		}
		if(menu.spanel != null){
			frame = menu.spanel.frame;
		}
		
		if(id == 6){
						
			frame.remove(frame.gpanel);
			frame.add(frame.spanel);
			frame.spanel.active = true;
			frame.gpanel.active = false;
			//frame.gpanel.galax.selected_star = frame.spanel.currentStar;
			Star s = frame.gpanel.galax.selected_star;
			frame.spanel.setStar(s);
			frame.momentum = 0;
		}
		else if(id == 0){
			menu.spanel.display_orbits = !menu.spanel.display_orbits;
		}
		else if(id == 1){
			menu.spanel.display_descriptions = !menu.spanel.display_descriptions;
		}
		else if(id == 2){
			isSwitch = !isSwitch;
			menu.spanel.manual_orbits = !menu.spanel.manual_orbits;
		}
		
		else if(id == 3){
			frame.setBoostInstruction('a');
		}
		else if(id == 4){
			frame.setBoostInstruction('d');
		}
		else if(id == 5){			
			frame.setBoostInstruction('w');
		}
		else if(id == 7){
			frame.setBoostInstruction('s');
		}
		else if(id == 8){
			frame.resetPlanningTime();
		}
		else if(id == 9){
			frame.spacecraftSunRef = !frame.spacecraftSunRef;
			isSwitch = !isSwitch;
		}
		else if(id == 10){
			frame.resetOrbitPlanning();
		}
	}
	

		
	
	
}




