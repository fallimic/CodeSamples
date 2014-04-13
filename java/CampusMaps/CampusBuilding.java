package sample;
/**
 *CampusBuilding represents a immutable building on campus
 *
 */

public class CampusBuilding implements Comparable<CampusBuilding>{
	private String shortName;
	private String longName;
	private CampusLocation location;
	//Abstraction function: 
	//shortName corresponds to an abbreviation of a name for a campus building.
	//longName corresponds to the full name of a building on campus
	//location holds the location data for the building.
	//Rep inv:
	//shortName != null longName != null;
	//location != null;
	
	/**
	 * 
	 * @param shortName the abbreviated name of a building
	 * @param longName the full name of a building
	 * @param location the location of that building
	 * @effects creates a new CampusBuilding with the shortName, longName and location
	 */
	public CampusBuilding(String shortName, String longName, CampusLocation location){
		this.shortName = shortName;
		this.longName = longName;
		this.location = location;
	}
	
	/**
	 * 
	 * @return the short name of the building
	 */
	public String getShortName(){
		return shortName;
	}
	
	/**
	 * 
	 * @return the full name of the building.
	 */
	public String getLongName(){
		return longName;
	}
	
	/**
	 * 
	 * @return the CampusLocation of the building
	 */
	public CampusLocation getLocation(){
		return location;
	}

	/**
	 * @return a string representation of this. 
	 */
	public String toString(){
		return shortName + ": " + longName;
	}

	
	/**
	 * Standard hashCode function
	 * @return an int that all CampusBuildings equal to this
	 * will also return when hashCode is called on them
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((shortName == null) ? 0 : shortName.hashCode());
		return result;
	}

	/**
	 * Standard equality function
	 * @param obj Object to compare to this
	 * @return true iff obj is of type CampusBuilding and obj.shortName == this.shortName
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CampusBuilding other = (CampusBuilding) obj;
		if (shortName == null) {
			if (other.shortName != null)
				return false;
		} else if (!shortName.equals(other.shortName))
			return false;
		return true;
	}

	/**
	 * @param o CampusBuilding to compare this to.
	 * @return 0 if o.shortName == this.shortName, 
	 * a positive int if o.shortName is alphabetically greater than this.shortName,
	 * a negative int if o.shortName is alphabetically less than this.shortName
	 */
	@Override
	public int compareTo(CampusBuilding o) {
		return this.shortName.compareTo(o.shortName);
	}

	
	
}
