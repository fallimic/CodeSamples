package sample;

/**
 * Edge represents an immutable, directed, connection between two Nodes in a
 * Graph with an optional label of type E. Edge<E> represents a and edge between
 * two nodes with a label of type E.
 * 
 * The edge is directional from a parent Node to a child Node. An Edge can have
 * the same parent Node and child Node in the case of a Node connected to
 * itself. Edge e = <A,B> indicates that B is directly reachable from A. This
 * does not indicate that A is directly reachable from B.
 */
public class Edge<E> implements Comparable<Edge<?>>{
	// Abstraction function:
	// An Edge e is a connection from e.parent to e.child
	// with label e.label
	// Representation invariant:
	// parent != null && child != null
	private Node<?> parent;
	private Node<?> child;
	private E label;

	/**
	 * 
	 * @param parent
	 *            the parent Node of the Edge
	 * @param child
	 *            the child Node of the Edge
	 * @param label
	 *            a label of type E for the Edge
	 * @effects creates a new Edge representing a connection from parent to child with
	 *          a label equal to label
	 */
	public Edge(Node<?> parent, Node<?> child, E label) {
		this.parent = parent;
		this.child = child;
		this.label = label;
		checkRep();
	}

	/**
	 * 
	 * @param parent
	 *            the parent Node of the Edge
	 * @param child
	 *            the child Node of the Edge
	 * @effects creates a new Edge representing a connection from parent to child
	 */
	public Edge(Node<?> parent, Node<?> child) {
		this.parent = parent;
		this.child = child;
		checkRep();
	}

	/** Checks that the representation invariant holds */
	private void checkRep() throws RuntimeException {
		if (parent == null) {
			throw new RuntimeException("Parent cannot be null");
		} else if (child == null) {
			throw new RuntimeException("Child cannot be null");
		}
	}

	/**
	 * Returns the parent Node of the Edge
	 * 
	 * @return the parent Node of the Edge
	 */
	public Node<?> getParent() {
		return parent;
	}

	/**
	 * Returns the child Node of the Edge
	 * 
	 * @return the child Node of the Edge
	 */
	public Node<?> getChild() {
		return child;
	}

	/**
	 * Returns the label of the Edge
	 * 
	 * @return the label of type E associated with the Edge
	 * returns null if no label was set.
	 */
	public E getLabel() {
		return label;
	}

	/**
	 * Returns a string representation of an Edge in the form <Parent,Child>
	 * 
	 * @return a string representation of the Edge
	 */
	@Override
	public String toString() {
		return "<" + parent.getData() + "," + child.getData() + ">(" + label + ")";
	}

	/**
	 * Standard hashCode function.
	 * 
	 * @return an int that all objects equal to this will also return.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((child == null) ? 0 : child.hashCode());
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
		return result;
	}

	/**
	 * Standard equality operation.
	 * 
	 * @param obj
	 *            the object to be compared for equality.
	 * @return true if and only if 'obj' is an instance of a Edge and 'this' and
	 *         'obj' represent the same Edge.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Edge<?> other = (Edge<?>) obj;
		if (child == null) {
			if (other.child != null)
				return false;
		} else if (!child.equals(other.child))
			return false;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		if (parent == null) {
			if (other.parent != null)
				return false;
		} else if (!parent.equals(other.parent))
			return false;
		return true;
	}

	
	/**
	 * compareTo compares two Edge objects and returns an integer.
	 * only does comparisons on Edges that are between two Node<String>
	 * objects and also have a String label.
	 * @param o Edge to compare this to
	 * @return Returns 0 if this.parent, this.child, o.parent, o.child are not Node<String>
	 * or if this.label or o.label are not of type String. 
	 */
	@Override
	public int compareTo(Edge<?> o) {
		int result = 0;
		if (!this.label.getClass().equals(o.label.getClass()) && !(label instanceof String)){
			return 0;
		} else {
			if(!(this.child.getData() instanceof String) || !(this.parent.getData() instanceof String) 
					|| !(o.child.getData() instanceof String) || !(o.parent.getData() instanceof String)){
				return 0;
			}
			String tPD = (String) parent.getData();
			String oPD = (String) o.parent.getData();
			result = tPD.compareTo(oPD);
			if (result == 0){
				String tCD = (String) child.getData();
				String oCD = (String) o.child.getData();
				result = tCD.compareTo(oCD);
			}
			if (result == 0){
				String tl = (String) label;
				result = tl.compareTo((String) o.label);
			}
		}
		return result;
	}
}
