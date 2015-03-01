package Static;

import java.awt.geom.AffineTransform;
import java.io.Serializable;
import java.util.Random;

public class BigPosition implements Serializable{
 public Double x;
 public Double y;
 
 public BigPosition(Double a, Double b){
  x = a;
  y = b;
 }
 
    public BigPosition normalize(){    
   if(x != 0 && y != 0){
    double xUnit = x / Math.sqrt((x*x)+(y*y));
      double yUnit = y / Math.sqrt((x*x)+(y*y));
      return new BigPosition(xUnit, yUnit);
   }  
   else if(x == 0 && y != 0){y = 1.0;}
   else if(y == 0 && x != 0){x = 1.0;}
   else{
    x = 0.0;
    y = 0.0;
   }
     return new BigPosition(x,y);
    }
    
    public double length(){   
     return Math.sqrt((x*x)+(y*y));
    }
    
    public BigPosition minus(BigPosition p){
     double xSub = x - p.x;
     double ySub = y - p.y;
     return new BigPosition(xSub, ySub);
    }
    
    public BigPosition minus(double d){
     double xSub = x - d;
     double ySub = y - d;
     return new BigPosition(xSub, ySub);
    }
    
    public BigPosition plus(BigPosition p){
     double xSub = x + p.x;
     double ySub = y + p.y;
     return new BigPosition(xSub, ySub);
    }
    
    public BigPosition plus(double d){
     double xSub = x + d;
     double ySub = y + d;
     return new BigPosition(xSub, ySub);
    }  
    
    public void add(double d){
     x = x + d;
     y = y + d;
    }  
    
    public void add(BigPosition p){
     x = x + p.x;
     y = y + p.y;
    }
    
    public BigPosition times(double d){
     double xSub = (double)x * d;
     double ySub = (double)y * d;
     return new BigPosition(xSub, ySub);
    }
    
    public BigPosition getNormal(){
     return new BigPosition(-y, x);
    }
    
    public double distance(BigPosition p){
     double distance = Math.sqrt(((x-p.x)*(x-p.x))+((y-p.y)*(y-p.y)));
  return distance;   
    }
    
     /*
   * Calculates the unit dotproduct, a double between 1 and -1, reflecting how much
   * the two vectors differ in direction. 1 is same direction, -1 is opposite directions, 0 is orthogonal.
   */
  public double unitDotProduct(BigPosition b){
   BigPosition an = new BigPosition(x,y).normalize();
   BigPosition bn = b.normalize();
   double dotProduct = (an.x*bn.x) + (an.y*bn.y);     
   return dotProduct;
  }

 public void subtract(BigPosition times) {

     x = x - times.x;
     y = y - times.y;
  
 }
 
 public void randomize(double x2, double y2){
	 Random rand = new Random();
	 x = rand.nextDouble()*(double)x2;
	 y = rand.nextDouble()*(double)y2;
 }
 
 public String toString(){
	 String r = "(" + x + ", " + y + ")";
	 return r;
 }
 
 public void rotate(double radius, double degrees){
	 double[] pt = {x, y};		
		AffineTransform.getRotateInstance(Math.toRadians(degrees), x-radius, y).transform(pt, 0, pt, 0, 1);
		
		x = pt[0];
		y = pt[1];
		
//		double[] pt = {x, y};
//		AffineTransform.getRotateInstance(Math.toRadians(angle), center.x, center.y).transform(pt, 0, pt, 0, 1); // specifying to use this double[] to hold coords
//		double newX = pt[0];
//		double newY = pt[1];
 }
 
 public void rotate(double radius, double degrees, int centerX, int centerY){
	 double[] pt = {x, y};		
		AffineTransform.getRotateInstance(Math.toRadians(degrees), centerX, centerY).transform(pt, 0, pt, 0, 1);
		
		x = pt[0];
		y = pt[1];
		
//		double[] pt = {x, y};
//		AffineTransform.getRotateInstance(Math.toRadians(angle), center.x, center.y).transform(pt, 0, pt, 0, 1); // specifying to use this double[] to hold coords
//		double newX = pt[0];
//		double newY = pt[1];
 }
 
 public BigPosition clone(){
	 BigPosition p = new BigPosition(0.0, 0.0);
	 p.x = x;
	 p.y = y;
	 
	 return p;
 }
 
 public void clone(BigPosition p){
	 x = p.x;
	 y = p.y;
 }
    
}









