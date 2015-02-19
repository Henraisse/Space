package Tech;

import java.util.ArrayList;

import Space.SpaceObject;

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
	
	public void setBoost(double days, double percentage, char direction){	
		
		BoostData newdata = new BoostData(days*24, percentage, booster, direction);
		boolean inserted = false;
		int increment = 0;
		while(!inserted){
			if(boosterInstructions[(int) days*24 + increment] == null){
				boosterInstructions[(int) days*24 + increment] = newdata;
				System.out.println("New BoostData! - - - [hour:" + (days*24 + increment) + "]");
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
		//avgör om målet ligger på en yttre eller inre bana
			//öka eller sänk hastigheten(gasa ortogonalt mot stjärnan)
				//tills banorna korsar överlappar varandra vid någon punkt
				//flytta till denna punkt
					//öka eller sänk hastigheten ytterligare från denna punkt
					//tills varje punkt på en av banorna ligger inom ett givet intervall till NÅGON punkt på den andra banan
	}
	
	
	
	
	
	
	
	
	
	
	
}




