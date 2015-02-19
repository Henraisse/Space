package Tech;

public class BoostData {
		public double hour = -1;
		
		public double boostPercentage = 1.0;
		public char direction = 0;
		
		public Booster booster;
		
		public BoostData(double hour2, double p, Booster b, char d){
			direction = d;
			hour = hour2;
			boostPercentage = p;
			booster = b;
		}

		public static BoostData idle() {
			// return an idle, non-moving data
			return new BoostData(-1, 0.0, null, 'a');
		}
}
