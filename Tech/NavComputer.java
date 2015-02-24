package Tech;

import java.util.ArrayList;

import Space.SpaceObject;
import Space.Star;

public class NavComputer {
	
	public int memory = 100000;
	public String name = "mother";
	Booster booster;
	public SpaceObject object;
	BoostData[] boosterInstructions = new BoostData[memory];
	
	
	public NavComputer(Booster b){
		booster = b;
	}


	public BoostData getBoost(int approxIndex) {
		if(approxIndex < boosterInstructions.length){
			if(boosterInstructions[approxIndex] == null){return BoostData.idle();}
			return boosterInstructions[approxIndex];
		}
		return BoostData.idle();
	}
	
	public void setBoost(double days, double percentage, char direction, int targetId){	
		Star target = object.closestStar.galax.stars.get(targetId);
		BoostData newdata = new BoostData(days*24, percentage, booster, direction, target);
		boolean inserted = false;
		int increment = 0;
		while(!inserted){
			if(boosterInstructions[(int) days*24 + increment] == null){
				boosterInstructions[(int) days*24 + increment] = newdata;
				//System.out.println("New BoostData! - - - [hour:" + (days*24 + increment) + "]");
				inserted = true;
			}
			else{
				increment++;
			}			
		}		
	}
	
	public void resetNavInstructions(){
		for(int i = 0; i < boosterInstructions.length; i++){
			boosterInstructions[i] = null;
		}
		
	}
	
	
	public void plotNavigation(){
		//avg�r om m�let ligger p� en yttre eller inre bana
			//�ka eller s�nk hastigheten(gasa ortogonalt mot stj�rnan)
				//tills banorna korsar �verlappar varandra vid n�gon punkt
				//flytta till denna punkt
					//�ka eller s�nk hastigheten ytterligare fr�n denna punkt
					//tills varje punkt p� en av banorna ligger inom ett givet intervall till N�GON punkt p� den andra banan
	}


	public void movePermanent(int h) {
		int counter = 0;
		int end = 0;
		for(int i = h; i < boosterInstructions.length; i++){
			boosterInstructions[counter] = boosterInstructions[i];
			counter++;
			end = i;
		}
		for(int i = counter+1; i < boosterInstructions.length; i++){
			boosterInstructions[i] = null;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
}




