import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;


public class Star {
	int id;
	String name;
	double x, y;
	double radius;
	
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
		x = rand.nextInt(20000);
		y = rand.nextInt(10000);
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
	
	public void drawStar(Graphics g, double zoom, double zx, double zy){
		double posX = zx+x;
		double posY = zy+y;
		
		double factorX = posX*zoom;
		double factorY = posY*zoom;		
		if(factorX < -60 || factorX > 2000 || factorY < -60 || factorY > 1200){
			return;
		}
		
		//HIGHLIGHT STAR #1000
		if(id == 1000){
			int ovalRad = (int)(30+(zoom*10));
			g.setColor(Color.green);
			int x0 = (int)(((posX)-((radius)/2))*zoom);
			int y0 = (int)(((posY)-((radius)/2))*zoom);
			

			g.drawOval((int)((x0-(ovalRad/2))+(radius/2)*zoom), (int)((y0-(ovalRad/2))+(radius/2)*zoom), ovalRad, ovalRad);

		}
		
		int z = new Random().nextInt(500)+2;
		double sizefactor = radius*zoom;
		// STANDARD STRENGTH
		int t3 = (int)(radius/3);		//secondary orb size
		int t4 = (int)(radius/2);		//tertiary orb size

		if((sizefactor) >= 2){
			drawStarOrb(g, 0.5, zoom, posX, posY, t4);
			if((sizefactor) >= 3){
				drawStarOrb(g, 0.7, zoom, posX, posY, t3);
				if((sizefactor) >= 6){	
					drawStarOrb(g, 10, zoom, posX, posY, 1);
				}
			}
		}
		else if(sizefactor >= 0.3){
			drawStarDot(g, zoom, sizefactor, posX, posY);
		}

	}

	public void drawStarDot(Graphics g, double zoom, double size, double x, double y){		
		int posX = (int)(((x)-((radius)/2))*zoom);
		int posY = (int)(((y)-((radius)/2))*zoom);
		g.setColor(getTempColor(size));
		g.drawLine(posX , posY, posX , posY);
	}
	
	public void drawStarOrb(Graphics g, double intensity, double zoom, double x, double y, int size){
		int posX = (int)(((x)-((radius+size)/2))*zoom);
		int posY = (int)(((y)-((radius+size)/2))*zoom);
		int sizeX = (int)((radius+size)*zoom);
		int sizeY = (int)((radius+size)*zoom);
		
		g.setColor(getTempColor(intensity));
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



