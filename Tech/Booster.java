package Tech;

import Space.SpaceObject;
import Static.Position;

public class Booster {

	public SpaceObject object;
	NavComputer navComputer;
	
	public double maxPower = 0.001;
	public boolean maximum = false;
	
	double warp = 1;
	
	
	public Booster(NavComputer nc){
		navComputer = nc;
	}
	
	public Position getBoost(int approxIndex, Position objPos, Position sourcePos, Position trajectory){
		if(object != null){
		
		BoostData bd = object.navComputer.getBoost(approxIndex);
			if(bd != null){
				Position direction = getVector(bd.direction, objPos, sourcePos, trajectory);
				
				return direction.times(bd.boostPercentage*maxPower*warp);
			}
		}
		//System.out.println("booster object is null");
		return new Position(0,0);
		//ur boost-data, extrahera en boostriktning, och en booststyrka
	}
	
	
	public Position getVector(char c, Position objPos, Position sourcePos, Position trajectory){
		Position solarOffset = sourcePos.minus(objPos);
		if(c == 'a'){			
			return solarOffset.times(-1).normalize();
		}
		
		if(c == 'b'){
			return solarOffset.normalize();
		}
		
		if(c == 'c'){
			Position newPos = new Position(-solarOffset.y, solarOffset.x).normalize();
			return newPos;
		}
		
		if(c == 'd'){
			Position newPos = new Position(solarOffset.y, -solarOffset.x).normalize();
			return newPos;
		}
		
		if(c == 'e'){
			Position newPos = trajectory.normalize();
			return newPos;
		}
		
		if(c == 'f'){
			Position newPos = trajectory.times(-1).normalize();
			return newPos;
		}
		return new Position(0, 0);
	}

}



