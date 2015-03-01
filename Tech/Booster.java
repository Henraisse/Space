package Tech;

import GUI.Frame;
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
		
//		if(approxIndex == 25){
//			System.out.println(bd.);
//		}
			if(bd != null){
				
				direction = getVector(bd.direction, objPos, bd.starPos, trajectory, bd.reference);
				if(direction == null){
					return new Position(0,0);
				}
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
	
	
	public Position getVector(char c, Position objPos, Position sourcePos, Position trajectory, boolean ref){
		if(sourcePos == null){
			return null;
		}
		Position solarOffset = sourcePos.
				minus(objPos);
		
		
		if(c == 'w'){		
			if(ref){
				return new Position(solarOffset.y, -solarOffset.x).normalize();
			}
			else{
				return trajectory.normalize();
			}
		}
		
		if(c == 'a'){
			if(ref){
				return solarOffset.times(-1).normalize();
			}
			else{
				return new Position(trajectory.y, -trajectory.x).normalize();
			}
		}
		
		if(c == 's'){
			if(ref){
				return  new Position(-solarOffset.y, solarOffset.x).normalize();
			}
			else{
				return trajectory.times(-1).normalize();				
			}
		}
		
		if(c == 'd'){
			if(ref){
				return solarOffset.normalize();
			}
			else{
				return new Position(-trajectory.y, trajectory.x).normalize();
			}
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



