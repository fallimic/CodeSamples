package sample;

/**
 * Node represents a single immutable node of a graph that can be connected to
 * other nodes through an Edge. It contains some data of any type E.
 */
public class Node<E> {
	// Abstraction function:
	// Node n is a node in a graph with data n.data of type E
	// Representation invariant:
	// data != null
	private E data;

	/**
	 * @param data The data of type E stored by the Node
	 * @requires data != null
	 * @effects Creates a new Node storing data = data
	 */
	public Node(E data) {
		this.data = data;
		checkRep();
	}

	/** Checks that the representation invariant holds */
	private void checkRep() throws RuntimeException {
		if (data == null) {
			throw new RuntimeException("Data cannot be null");
		} 
	}

	/**
	 * Returns the data stored in the Node
	 * 
	 * @return the Node's data of type E
	 */
	public E getData() {
		return data;
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
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		return result;
	}

	/**
	 * Standard equality operation.
	 * 
	 * @param obj The object to be compared for equality.
	 * @return true if and only if 'obj' is an instance of a Node and 'this' and
	 *         'obj' represent the same Node.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node<?> other = (Node<?>) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		return true;
	}
	
	public String toString(){
		return data.toString();
	}

}
