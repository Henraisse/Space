import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;


public class Star {
	int id;
	String name;
	int x, y;
	int radius;
	
	int kelvin;
	
	Color O = new Color(155, 176, 255);
	Color B = new Color(170, 191, 255);
	Color A = new Color(202, 215, 255);
	Color F = new Color(248, 247, 255);
	Color G = new Color(255, 244, 234);
	Color K = new Color(255, 210, 161);
	Color M = new Color(255, 204, 111);


	
	public Star(int i, String n){
		id = i;
		name = n;
		Random rand = new Random();
		x = rand.nextInt(1900);
		y = rand.nextInt(1000);
		kelvin = rand.nextInt(6)+1;
		generateSize(rand);
		

	}
	
	public Color getTempColor(double a){
		if(a <= 0){ return Color.BLACK;}
		Color ret;
	
		switch (kelvin) {
		case 1:  ret = O;	break;
		case 2:  ret = B;	break;
		case 3:  ret = A;	break;
		case 4:  ret = F;	break;
		case 5:  ret = G;	break;
		case 6:  ret = K;	break;
		case 7:  ret = M;	break;
		default: ret = M; 	break;
		}
		int x = (int) (ret.getRed()*a);
		int y = (int) (ret.getGreen()*a);
		int z = (int) (ret.getBlue()*a);
		
		if(x > 255){x = 255;}
		if(y > 255){y = 255;}
		if(z > 255){z = 255;}

		Color ret2 = new Color(x, y, z);		
		return ret2;
	}
	
	public void drawStar(Graphics g, double zoom){
		
		int z = new Random().nextInt(500)+2;

		int t1 = 0;
		int t2 = 0;
		int t3 = 0;
		int t4 = 0;
		if(z > 500){
			// FLICKER STRENGTH
			t3 = (radius/3)+2;		//secondary orb size
			t4 = (int)(radius/2)+2;		//tertiary orb size
			
			}
		else{
			// STANDARD STRENGTH
			t3 = (radius/3);		//secondary orb size
			t4 = (int)(radius/2);		//tertiary orb size
			}		
		drawOrbTertiary(g, t4, zoom);
		drawOrbSecondary(g, t3, zoom);
		drawOrbPrimary(g, zoom);								
	}
	
	public void drawOrbPrimary(Graphics g, double zoom){
		int posX = (int)((x-((radius)/2))*zoom);
		int posY = (int)((y-((radius)/2))*zoom);
		int sizeX = (int)(radius*zoom);
		int sizeY = (int)(radius*zoom);
		g.setColor(Color.white);
		g.fillOval(posX , posY, sizeX, sizeY);
	}
	
	public void drawOrbSecondary(Graphics g, int z, double zoom){
		int posX = (int)((x-((radius+z)/2))*zoom);
		int posY = (int)((y-((radius+z)/2))*zoom);
		int sizeX = (int)((radius+z)*zoom);
		int sizeY = (int)((radius+z)*zoom);
		g.setColor(getTempColor(0.7));
		g.fillOval(posX , posY, sizeX, sizeY);
	}
	
	public void drawOrbTertiary(Graphics g, int z, double zoom){
		int posX = (int)((x-((radius+z)/2))*zoom);
		int posY = (int)((y-((radius+z)/2))*zoom);
		int sizeX = (int)((radius+z)*zoom);
		int sizeY = (int)((radius+z)*zoom);
		g.setColor(getTempColor(0.5));
		g.fillOval(posX , posY, sizeX, sizeY);
	}
	
	public void generateSize(Random rand){
		//randomize size class
		int sizeClass = rand.nextInt(100)+1;
		if(sizeClass <= 80){
			radius = rand.nextInt(4)+4;
		}
		else if(sizeClass > 80 && sizeClass < 95){
			radius = rand.nextInt(7)+8;
		}
		else{
			radius = rand.nextInt(10)+15;
		}
		
		
	}
	
	
	
	
	
	
	
}



