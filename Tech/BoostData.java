package Tech;

import Space.Star;
import Static.Position;

public class BoostData {
		public Position starPos = new Position(0,0);
		public double hour = -1;
		
		public double boostPercentage = 1.0;
		public char direction = 0;
		
		public Booster booster;
		public boolean reference;
		
		public BoostData(double hour2, double p, Booster b, char d, Position pos, boolean ref){
			reference = ref;
			starPos = pos;			
			direction = d;
			hour = hour2;
			boostPercentage = p;
			booster = b;
			
		}

		public static BoostData idle() {
			// return an idle, non-moving data
			return new BoostData(-1, 0.0, null, 'a', null, false);
		}
}
