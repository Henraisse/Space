import java.awt.Color;
import java.util.Random;


public class Star {
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

	
	public Star(){
		Random rand = new Random();
		x = rand.nextInt(1900);
		y = rand.nextInt(1000);
		kelvin = rand.nextInt(6)+1;
		radius = 2;

	}
	
	public Color getTempColor(){
		
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
		
		return ret;
	}
}
