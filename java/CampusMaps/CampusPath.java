package sample;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * CampusPath represents an immutable route between two CampusBuilding's
 * The path can be accessed through three lists. One that holds all
 * the locations along the path. One that holds all the compass directions
 * to each location along the path. And one that holds all the distances
 * to each location along the path.
 *
 */
public class CampusPath {
	private List<CampusLocation> pathLocations;
	private List<String> directions;
	private List<Double> distances;
	private CampusBuilding start;
	private CampusBuilding dest;
	private double totalDistance;
	//Abstraction function: 
	//Each location along the path is broken into three parts
	//The next location to travel to stored in pathLocations.
	//The direction to travel to that location stored in directions.
	//The distance to travel to that location stored in distances.
	//The CampusPath also manages the start and dest CampusBuilding
	//and the totalDistance of the path.
	//
	//Representation Inv:
	//pathLocations.size() == directions.size() == distances.size()
	//none of these lists are null. start,dest != null.
	//totalDistance == sum of all elements in distances
	
	/**
	 * 
	 * @param path contains a Edges that connect CampusLocations with double distances stored in the label
	 * @param start starting CampusBuilding of the path
	 * @param dest destination CampusBuilding of the path
	 * @effects creates a new CampusPath 
	 */
	public CampusPath(List<Edge<Double>> path, CampusBuilding start, CampusBuilding dest){
		this.start = start;
		this.dest = dest;
		pathLocations = new ArrayList<CampusLocation>();
		directions = new ArrayList<String>();
		distances = new ArrayList<Double>();
		double dist = 0;
		for (Edge<Double> e : path){
			dist += e.getLabel();
			distances.add(e.getLabel());
			directions.add(getDirection(e));
			pathLocations.add((CampusLocation) e.getChild().getData());
			
		}
		totalDistance = dist;
	}
	
	/**
	 * 
	 * @return a List<CampusLocation> where each element in the list,
	 * when traversed in order, corresponds to the next CampusLocation
	 * necessary to travel from this.getStartBuilding to this.getDestinationBuilding
	 */
	public List<CampusLocation> getPathLocations(){
		return Collections.unmodifiableList(pathLocations);
	}
	
	/**
	 * 
	 * @return a List<String> where each element in the list,
	 * when traversed in order, corresponds to the compass direction
	 * necessary to travel to the corresponding CampusLocation from
	 * this.getPathLocations()
	 */
	public List<String> getPathDirections(){
		return Collections.unmodifiableList(directions);
	}
	
	/**
	 * 
	 * @return a List<Double> where each element in the list,
	 * when traversed in order, corresponds to the distance
	 * in feet needed to travel to the corresponding CampusLocation
	 * from this.getPathLocations()
	 */
	public List<Double> getPathDistances(){
		return Collections.unmodifiableList(distances);
	}
	
	
	/**
	 * 
	 * @return the total distance, in feet, of the path
	 */
	public double getTotalDistance(){
		return totalDistance;
	}
	
	/**
	 * 
	 * @return the CampusBuilding at the start of the path
	 */
	public CampusBuilding getStartBuilding(){
		return start;
	}
	
	/**
	 * 
	 * @return the CampusBuilding at the end of the path
	 */
	public CampusBuilding getDestinationBuilding(){
		return dest;
	}
	
	//Takes an Edge<Double> that has parent and child Node<CampusLocation>
	//and returns the compass direction corresponding to the path between
	//the parent and child.
	private String getDirection(Edge<Double> e){
		CampusLocation l1 =(CampusLocation) e.getParent().getData();
		CampusLocation l2 = (CampusLocation) e.getChild().getData();
		double dy =  (l1.getY() - l2.getY());
		double dx = (l2.getX() - l1.getX());
		double angle = Math.atan2(dy,dx);
		if (angle <= Math.PI && angle > (7.0 / 8.0) * Math.PI){
			return "W";
		} else if (angle < (7.0 / 8.0) * Math.PI && angle > (5.0 / 8.0) * Math.PI){
			return "NW";
		} else if (angle <= (5.0 / 8.0)* Math.PI && angle >= (3.0 / 8.0) * Math.PI){
			return "N";
		} else if (angle < (3.0 / 8.0) * Math.PI && angle > (1.0 / 8.0) * Math.PI){
			return "NE";
		} else if (angle <= (1.0 / 8.0) * Math.PI && angle >= (-1.0 / 8.0) * Math.PI){
			return "E";
		} else if (angle < (-1.0 / 8.0) * Math.PI && angle > (-3.0 / 8.0) * Math.PI){
			return "SE";
		} else if (angle <= (-3.0 / 8.0) * Math.PI && angle >= (-5.0 / 8.0) * Math.PI){
			return "S";
		} else if (angle < (-5.0 / 8.0) * Math.PI && angle > (-7.0 / 8.0) * Math.PI){
			return "SW";
		} else if (angle <= (-7.0 / 8.0) * Math.PI && angle >= (-1.0) * Math.PI){
			return "W";
		}
		return "";
	}
}
