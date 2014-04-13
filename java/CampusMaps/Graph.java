package sample;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Set;

/**
 * Graph represents a mutable collection of Nodes and Edges that connect the
 * nodes. Each Node contains data of some type and each Edge contains
 * an optional label of some type. An edge represents one connection between two Nodes n1, n2
 * where n1 can be the same as n2. A node may have multiple connections to any
 * other nodes meaning that there can be multiple edges connecting a Node n1 to
 * a Node n2. Edges cannot reference Nodes that have been removed from the
 * graph. Two identical nodes cannot be contained in the same graph.
 * Graph<E,S> represents a graph containing nodes with data of type E
 * and Edges containing labels of type S
 */
public class Graph<E,S> {
	private Map<Node<E>, ArrayList<Edge<S>>> g;

	// Abstraction function:
	// Graph g represents a graph where each node in the graph is stored as
	// a key in g and maps to a list of edges that have that node as a parent.
	// Representation invariant:
	// g != null; for all Node n in g.keySet(), g.get(n) != null
	// for all Edge e in g, g.contains(e.getParent()) &&
	// g.contains(e.getChild())

	/**
	 * Constructs a new empty Graph
	 * 
	 * @effects creates a new Graph with no nodes and no edges
	 */
	public Graph() {
		g = new HashMap<Node<E>, ArrayList<Edge<S>>>();
		checkRep();
	}

	/**
	 * 
	 * @param nodes
	 *            Set of Node objects to be added to the new graph.
	 * @effects Creates a graph containing all Node objects in nodes
	 */
	public Graph(Set<Node<E>> nodes) {
		this();
		Iterator<Node<E>> i = nodes.iterator();
		while (i.hasNext()) {
			g.put(i.next(), new ArrayList<Edge<S>>());
		}
		checkRep();
	}

	/**
	 * @param nodes
	 *            Set of Node objects to be added to the new graph
	 * @param edges
	 *            List of Edge objects to be added to the new graph
	 * @effects creates a graph containing all Node objects in nodes and all
	 *          Edge objects in edges
	 */
	public Graph(Set<Node<E>> nodes, List<Edge<S>> edges) {
		this(nodes);
		for (int i = 0; i < edges.size(); i++) {
			Edge<S> e = edges.get(i);
			g.get(e.getParent()).add(e);
		}
		checkRep();
	}

	/**
	 * Returns the collection of Node's in the graph.
	 * 
	 * @return the Set of all Node objects in the graph
	 */
	public Set<Node<E>> getNodes() {
		return Collections.unmodifiableSet(g.keySet());
	}

	/**
	 * Returns a list of all edges contained in the graph.
	 * 
	 * @return a list of all edges contained in the graph.
	 */
	public List<Edge<S>> getEdges() {
		List<Edge<S>> res = new ArrayList<Edge<S>>();
		for (Node<E> n : g.keySet()) {
			res.addAll(g.get(n));
		}
		return res;
	}

	/**
	 * Returns the list of Edges that have a given Node as a parent.
	 * 
	 * @param n
	 *            the parent Node of the children to be returned
	 * @requires n != null
	 * @throws IllegalArgumentException
	 *             if n is not contained in the graph.
	 * @return a list containing all Edges that have n as a parent Node
	 */
	public List<Edge<S>> getChildren(Node<?> n) {
		if (!this.containsNode(n)) {
			throw new IllegalArgumentException();
		}
		List<Edge<S>> res = new ArrayList<Edge<S>>();
		res.addAll(g.get(n));
		return res;
	}

	/**
	 * Counts children of a given node
	 * 
	 * @param n
	 *            parent Node for the children to be counted
	 * @requires n != null, n must be contained in the graph
	 * @throws IllegalArgumentException
	 *             if n is not contained in the graph.
	 * @return Returns an int a such that a is the number of Nodes that n
	 *         connects to
	 */
	public int getNumChildren(Node<E> n) {
		if (!this.containsNode(n)) {
			throw new IllegalArgumentException();
		}
		return g.get(n).size();
	}

	/**
	 * Adds the given node to the graph. If the given node is already in the
	 * graph, no effects.
	 * 
	 * @param n
	 *            Node to be added to the Graph
	 * @requires n != null
	 * @effects n is added to the graph unless it is already in the graph.
	 */
	public void addNode(Node<E> n) {
		if (!this.containsNode(n)) {
			g.put(n, new ArrayList<Edge<S>>());
		}
		checkRep();
	}

	/**
	 * Removes a given node from the graph and all edges that pointed to the
	 * given node.
	 * 
	 * @param n
	 *            Node to be removed from the Graph
	 * @requires n != null, n must be contained in the graph
	 * @throws IllegalArgumentException
	 *             if n is not contained in the graph
	 * @effects Removes n from the graph and all edges contained in the graph
	 *          that reference n
	 */
	public void removeNode(Node<E> n) {
		if (!this.containsNode(n)) {
			throw new IllegalArgumentException();
		}
		List<Edge<S>> ed = this.getEdges();
		for (Edge<S> e : ed) {
			if (e.getChild().equals(n)) {
				removeEdge(e);
			}
		}
		g.remove(n);
		checkRep();
	}

	/**
	 * Adds the given edge to the graph. If the given edge is already in the
	 * graph, there will be a duplicate edge. The parent and child of the edge
	 * need to be contained in the graph.
	 * 
	 * @param e
	 *            Edge to be added to the Graph
	 * @requires e != null
	 * @throws IllegalArgumentException
	 *             if e.getParent() or e.getChild() are not contained in the
	 *             graph
	 * @effects e is added to the graph.
	 */
	public void addEdge(Edge<S> e) {
		if (!this.containsNode(e.getParent())
				|| !this.containsNode(e.getChild())) {
			throw new IllegalArgumentException();
		}
		g.get(e.getParent()).add(e);
	}

	/**
	 * Removes a given edge from the graph. Both the parent of the edge and the
	 * child are unaffected. If two identical edges are in the graph, only
	 * removes one
	 * 
	 * @param e
	 *            Edge to be removed from the Graph
	 * @requires e != null, e must be contained in the graph
	 * @throws IllegalArgumentException
	 *             if e is not contained in the graph or e == null
	 * @effects Removes e from the graph.
	 */
	public void removeEdge(Edge<S> e) {
		if (!this.containsEdge(e)) {
			throw new IllegalArgumentException();
		}
		g.get(e.getParent()).remove(e);
		checkRep();
	}

	/**
	 * Returns true if the graph is empty, False otherwise.
	 * 
	 * @return true iff the graph contains zero nodes, false otherwise
	 */
	public boolean isEmpty() {
		return g.keySet().size() == 0;
	}

	/**
	 * Checks whether a given node is contained in the graph
	 * 
	 * @param node
	 *            Node to check whether it is contained in the graph
	 * @return true iff n is contained in the graph, false otherwise.
	 */
	public boolean containsNode(Node<?> node) {
		return g.containsKey(node);
	}

	/**
	 * Checks whether a given edge is a member of the graph
	 * 
	 * @param e
	 *            Edge to check whether it is contained in the graph
	 * @return true iff e is contained in the graph false otherwise
	 */
	public boolean containsEdge(Edge<?> e) {
		return this.containsNode(e.getParent())
				&& g.get(e.getParent()).contains(e);
	}

	/** Checks that the representation invariant holds */
	private void checkRep() throws RuntimeException {
		if (g == null) {
			throw new RuntimeException("g cannot be null");
		}
		for (Node<E> n : g.keySet()) {
			if (g.get(n) == null) {
				throw new RuntimeException(
						"Each node must map to non null list");
			}
			for (Edge<S> e : g.get(n)) {
				if (!(e.getParent().equals(n))){
					throw new RuntimeException("Node must map to list of edges with itself as the parent");
				}
				if (!(g.containsKey(e.getParent()))
						|| !(g.containsKey(e.getChild()))) {
					throw new RuntimeException(
							"Parent and child must be contained in graph");
				}
			}
		}
	}

}
