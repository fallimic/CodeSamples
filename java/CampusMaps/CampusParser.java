package sample;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.SortedMap;
/**
 * Loads data from files to allow CampusPaths to initialize its data structures
 * 
 *
 */
public class CampusParser {
	//Class does not represent an ADT
	
	/** Loads building data into a map from the buildings short name to
	 * the CampusBuilding object corresponding to it.
	 * 
	 * @param filename file containing building data
	 * @param buildings map to be filled with the building data.
	 * @modifies buildings
	 * @throws Exception if file not found or if the file was
	 * malformed in any way.
	 */
	public static void parseBuildingsList(String filename, 
			SortedMap<String,CampusBuilding> buildings) throws Exception{
		BufferedReader reader = null;
	    try {
	    	reader = new BufferedReader(new FileReader(filename));
	    	String inputLine;
	    	while ((inputLine = reader.readLine()) != null) {
	    		String[] tokens = inputLine.split("\t");
	    		if (tokens.length != 4){
	    			throw new Exception("Line should contain exactly 3 tabs: " + inputLine);
	    		}
	    		String shortName = tokens[0];
	    		String longName = tokens[1];
	    		double x = new Double(tokens[2]);
	    		double y = new Double(tokens[3]);
	    		CampusLocation l = new CampusLocation(x,y);
	    		CampusBuilding b = new CampusBuilding(shortName, longName, l);
	    		buildings.put(shortName, b);
	    	}
	    } catch (IOException e) {
	    	System.err.println(e.toString());
	    	e.printStackTrace(System.err);
	    } finally {
	    	if (reader != null) {
	    		reader.close();
	    	}
	    }
	}
	
	/** Loads path data from a file into a Graph
	 * 
	 * @param filename file to load path data from
	 * @param paths Graph to populate with path data
	 * @throws Exception if file not found or if the file
	 * is malformed in any way.
	 */
	public static void parsePaths(String filename, 
			Graph<CampusLocation, Double> paths) throws Exception{
		
		BufferedReader reader = null;
	    try {
	    	reader = new BufferedReader(new FileReader(filename));
	    	String inputLine;
	    	Node<CampusLocation> currentPar = new Node<CampusLocation>(new CampusLocation(0,0));
	    	while ((inputLine = reader.readLine()) != null) {
	    		String[] tokens = inputLine.split("\t");
	    		if (tokens.length == 1){
	    			String parentPos = tokens[0];
	    			String[] coords = parentPos.split(",");
	    			if (coords.length == 2){
	    				currentPar = new Node<CampusLocation>(new CampusLocation(new Double(coords[0]), new Double(coords[1])));
	    			} else {
	    				throw new Exception("Line should have an x and y position " + inputLine);
	    			}
	    			paths.addNode(currentPar);
	    		} else if (tokens.length == 2){
	    			String childDat = tokens[1];
	    			String[] childTok = childDat.split(": ");
	    			if (childTok.length == 2){
	    				double distance = new Double(childTok[1]);
	    				String[] childPos = (childTok[0].split(","));
	    				CampusLocation childLoc = new CampusLocation(new Double(childPos[0]), new Double(childPos[1]));
	    				Node<CampusLocation> child = new Node<CampusLocation>(childLoc);
	    				Edge<Double> connect = new Edge<Double>(currentPar, child, distance);
	    				paths.addNode(child);
	    				paths.addEdge(connect);
	    			}
	    		} else {
	    			throw new Exception("Line should not contain more than 1 tab: " + inputLine);
	    		}
	    	}
	    } catch (IOException e) {
	    	System.err.println(e.toString());
	    	e.printStackTrace(System.err);
	    } finally {
	    	if (reader != null) {
	    		reader.close();
	    	}
	    }
	}

}
