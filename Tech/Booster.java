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
	
	public Position getBoost(int approxIndex, Position objPos, Position trajectory){
		if(object != null){
		Position direction = new Position(0,0);
		BoostData bd = object.navComputer.getBoost(approxIndex);
			if(bd != null){
				
				direction = getVector(bd.direction, objPos, bd.starPos, trajectory);
				
				if(direction.x == -666 || direction.y == -666){
					return direction;
				}
				
				return direction.times(bd.boostPercentage*maxPower*warp);
			}
		}
		//System.out.println("booster object is null");
		return new Position(0,0);
		//ur boost-data, extrahera en boostriktning, och en booststyrka
	}
	
	
	public Position getVector(char c, Position objPos, Position sourcePos, Position trajectory){
		Position solarOffset = sourcePos.minus(objPos);
		if(c == 'w'){			
			return solarOffset.times(-1).normalize();
			//return new Position(-trajectory.y, trajectory.x).normalize();
		}
		
		if(c == 'a'){
			return solarOffset.normalize();
			//return new Position(trajectory.y, -trajectory.x).normalize();
		}
		
		if(c == 's'){
			Position newPos = new Position(-solarOffset.y, solarOffset.x).normalize();
			return newPos;
			//return trajectory.normalize();
		}
		
		if(c == 'd'){
			Position newPos = new Position(solarOffset.y, -solarOffset.x).normalize();
			return newPos;
			//return trajectory.times(-1).normalize();
		}
		
		if(c == 'e'){
			Position newPos = trajectory.normalize();
			return newPos;
		}
		
		if(c == 'f'){
			Position newPos = trajectory.times(-1).normalize();
			return newPos;
		}
		
		if(c == 'h'){
			Position newPos = new Position(-666, -666);
			return newPos;
		}
		
		return new Position(0, 0);
	}

}



