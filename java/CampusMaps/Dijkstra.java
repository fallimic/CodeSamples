package sample;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class Dijkstra {


	/**
	 * Finds the least cost path between two given Nodes in a weighted
	 * Graph<E,Double>
	 * 
	 * @param startN
	 *            the starting node of the path
	 * @param destN
	 *            the destination node of the path
	 * @param g
	 *            the graph that contains both startN and destN
	 * @throws IllegalArgumentException
	 *             if startN or destN is not contained in g
	 * @return a List of Edge<Double> representing the path between startN and
	 *         destN Returns null if no path is found.
	 */
	public static <E> List<Edge<Double>> getPath(Node<E> startN, Node<E> destN,
			Graph<E, Double> g) {
		if (!g.containsNode(startN) || !g.containsNode(destN)) {
			throw new IllegalArgumentException(
					"Start and destination must be contained in the given graph");
		}
		PriorityQueue<List<Edge<Double>>> active = new PriorityQueue<List<Edge<Double>>>(
				50, pathComparator());
		Set<Node<?>> finished = new HashSet<Node<?>>();
		List<Edge<Double>> initialPath = new ArrayList<Edge<Double>>();
		initialPath.add(new Edge<Double>(startN, startN, 0.0));
		active.add(initialPath);
		while (!active.isEmpty()) {
			List<Edge<Double>> minPath = active.poll();
			Node<?> minDest = minPath.get(minPath.size() - 1).getChild();
			if (minDest.equals(destN)) { // found correct path
				minPath.remove(0);
				return minPath;
			}
			if (finished.contains(minDest)) { // already know min cost path to
												// this node
				continue;
			}
			for (Edge<Double> e : g.getChildren(minDest)) {
				if (!finished.contains(e.getChild())) { // Don't already know
														// the min cost path to
														// this node
					List<Edge<Double>> newPath = new ArrayList<Edge<Double>>(
							minPath);
					newPath.add(e);
					active.add(newPath);
				}
			}
			finished.add(minDest);
		}
		return null;
	}

	//Returns a Comparator<List<Edge<Double>>> to compare paths for the PriorityQueue
	private static Comparator<List<Edge<Double>>> pathComparator() {
		return new Comparator<List<Edge<Double>>>() {
			@Override
			public int compare(List<Edge<Double>> o1, List<Edge<Double>> o2) {
				double o1Weight = 0;
				double o2Weight = 0;
				for (Edge<Double> e : o1) {
					o1Weight += e.getLabel();
				}
				for (Edge<Double> e : o2) {
					o2Weight += e.getLabel();
				}
				double result = o1Weight - o2Weight;
				if (result > 0) {
					return 1;
				} else if (result < 0) {
					return -1;
				} else {
					return 0;
				}
			}
		};
	}
}
