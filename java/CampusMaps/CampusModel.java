package sample;

import java.util.Collection;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * CampusPaths loads campus building data and path data from files and 
 * allows the calculation of paths between buildings on campus.
 * 
 * CampusPaths reads from two files. One containing all the building data
 * in the format of "abbrev	fullname	x	y" where all values are separated by tabs.
 * The other file is a .dat file containing the path data in the form
 * "x,y
 * 		x2,y2: dist2
 * 		x3,y3: dist3" where there are paths between x,y and the indented pairs xi,yi and 
 * the distance between the points is disti
 *
 */

public class CampusModel {
	private Graph<CampusLocation, Double> paths;
	private SortedMap<String, CampusBuilding> buildings;
	//Abstraction function: All buildings in campus_buildings.dat and 
	//all path data from campus_paths.dat are loaded into buildings and
	//paths. buildings provides a mapping from a buildings short name to
	// a CampusBuilding object. 
	
	//Representation Invariant: paths != null, buildings != null,
	// Every CampusBuilding.getLocation() in buildings.values() corresponds to a 
	// CampusLocation in paths.
	
	/**
	 * @requires buildingFile and pathsFile exist and are valid .dat files
	 * @param buildingFile .dat file containing the building information
	 * @param pathsFile .dat file containing the path information
	 * @effects creates a new CampusPaths object with buildings and
	 * path data loaded in from buildingFile and pathsFile.
	 */
	public CampusModel(String buildingFile, String pathsFile){
		paths = new Graph<CampusLocation, Double>();
		buildings = new TreeMap<String, CampusBuilding>();
		try {
			CampusParser.parseBuildingsList(buildingFile, buildings);
			CampusParser.parsePaths(pathsFile, paths);
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * @effects creates a new CampusPaths object with buildings and
	 * path data loaded in from data/campus_buildings.dat and data/campus_paths.dat
	 */
	public CampusModel(){
		this("src/data/campus_buildings.dat", "src/data/campus_paths.dat");
	}
	
	/**
	 * 
	 * @return a Collection containing all CampusBuildings loaded into this.
	 * An iteration over this collection will return buildings in sorted
	 * alphabetical order by the CampusBuildings short name.
	 */
	public Collection<CampusBuilding> getBuildings(){
		return buildings.values();
	}
	
	/**
	 * @requires this.containsBuilding(start) && this.containsBuilding(dest)
	 * @param start the CampusBuilding to start the path from
	 * @param dest the CampusBuidling to connect the path to.
	 * @return A CampusPath representing the path between start and dest
	 * returns null if no path found.
	 */
	public CampusPath getPath(CampusBuilding start, CampusBuilding dest){
		Node<CampusLocation> startLoc = new Node<CampusLocation>(start.getLocation());
		Node<CampusLocation> destLoc = new Node<CampusLocation>(dest.getLocation());
		List<Edge<Double>> path = Dijkstra.getPath(startLoc,destLoc, paths);
		if (path != null){
			return new CampusPath(path,start,dest);		
		} else {
			return null;
		}
	}
	
	/**
	 * 
	 * @param shortName the short name of the building to return
	 * @return a CampusBuilding whose short name equals shortName. 
	 * Returns null if shortName corresponds to no CampusBuilding
	 */
	public CampusBuilding getBuilding(String shortName){
		return buildings.get(shortName);
	}
	
	/**
	 * 
	 * @param shortName the short name of the building to be checked
	 * @return true iff a CampusBuilding containing the short name shortName exists.
	 * false otherwise.
	 */
	public boolean containsBuilding(String shortName){
		return buildings.containsKey(shortName);
	}
}
