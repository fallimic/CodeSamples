package sample;

/**
 * A CampusLocation holds an immutable x and y position for a location on campus.
 *
 */
public class CampusLocation {
	private double x;
	private double y;
	//Abstraction function: 
	// x corresponds to the x position of a location on campus
	// y corresponds to the y position of a location on campus
	// Rep inv.: x != null, y != null;
	
	/**
	 * @requires x != null, y != null
	 * @param x the x location of a location on campus
	 * @param y the y location of a location on campus
	 * @effects creates a new CampusLocation corresponding to x and y
	 */
	public CampusLocation(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	/**
	 * 
	 * @return the x position of this
	 */
	public double getX(){
		return x;
	}
	
	/**
	 * 
	 * @return the y position of this
	 */
	public double getY(){
		return y;
	}

	/**
	 * Standard hashCode function
	 * @return an int that all CampusLocations equal to this
	 * will also return when hashCode is called on them
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	/**
	 * Standard equality function
	 * @param obj Object to compare to this
	 * @return true iff obj is of type CampusLocation and obj.x = this.x and obj.y == this.y
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CampusLocation other = (CampusLocation) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		return true;
	}
	
	/**
	 * returns a string representation of this with
	 * x and y rounded to the nearest integer.
	 */
	public String toString(){
		return "(" + String.format("%.0f",x) + ", " + String.format("%.0f",y) + ")";
	}
}
