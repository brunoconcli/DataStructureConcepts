package binaryTree;
import linkedLists.ordered.LinkedListOrdered;
import linkedLists.unordered.LinkedListUnordered;

public class BinarySearchTree<X extends Comparable<X>> implements Cloneable {
	
	private class Node implements Cloneable, Comparable<X> {
		private Node left;
		private Node right;
		private X info;

		public Node(Node left, X info, Node right) throws Exception {
			if (info == null) throw new Exception ("Info must not be null");
			this.left = left;
			this.info = info;
			this.right = right;
		}

		public Node(X info) throws Exception {
			this(null, info, null);
		}

		public X getInfo() {
			return this.info;
		}

		public Node getLeft() {
			return this.left;
		}

		public Node getRight() {
			return this.right;
		}

		public void setInfo(X info) {
			this.info = info;
		}

		public void setLeft(Node left) {
			this.left = left;
		}

		public void setRight(Node right) {
			this.right = right;
		}

		@Override
		public String toString() {
			return (
				this.left.getInfo() + 
				", " + 
				this.info + 
				", " + 
				this.right.getInfo());
		}
		
		@Override
		public int compareTo(X info) {
			return 1;
		}
	}

	private Node root;
	private int height = 0, size = 0;

	public void add(X info) throws Exception {
		if (info == null) throw new Exception("Info passed must not be null");
		if (this.root == null) {
			this.root = new Node(info);
			return;
		}
		Node current = this.root, previous = null;
		int comparison = info.compareTo(current.getInfo());
		
		for (;;) {
			if (comparison == 0) throw new Exception("Info passed already exists");
			if (comparison < 0) {
				previous = current;
				current = current.getLeft();
			}
			else {
				previous = current;
				current = current.getRight();
			}

			if (current == null) break;
			comparison = info.compareTo(current.getInfo());
		}
		if (previous.getLeft() == null && previous.getRight() == null) {
			this.height ++;
		}
		current = new Node(info);
		if (previous.getInfo().compareTo(current.getInfo()) < 0) previous.setRight(current);
		else previous.setLeft(current);
		this.size ++;
	}

	private LinkedListOrdered<X> getOrderedArray(Node root) throws Exception {
		LinkedListOrdered<X> list = new LinkedListOrdered<>();
		if (root == null) return new LinkedListOrdered<>();
		
		list.add(root.getInfo());
		return list.addList(getOrderedArray(root.getLeft())).addList(getOrderedArray(root.getRight()));
	}

	public String getOrderedArray() throws Exception {
		return this.getOrderedArray(this.root).toString();
	}

	private int getSize(Node root) {
		if (root == null) return 0;
		return 1 + (this.getSize(root.getLeft()) + this.getSize(root.getRight()));
	}

	public int getSize() {
		return this.getSize(this.root);
	}

	public LinkedListUnordered<X> getPreorderedArray() {
		// add nodes as they're visited for the first time
		return new LinkedListUnordered<>();
	}

	public LinkedListUnordered<X> getPostOrderedArray() {
		// add nodes as they're visited for the third time
		return new LinkedListUnordered<>();
	}

	public LinkedListUnordered<X> getInOrderedArray() {
		// add nodes as they're visited for the second time
		return new LinkedListUnordered<>();	
	}
	
	public boolean alreadyExists(X info) {
		if (info == null) return false;
		if (this.root == null) return false;

		Node current = this.root;
		int comparison;
		while (current != null) {
			comparison = current.compareTo(info);
			if (comparison == 0) 
				return true;
			if (comparison < 0)
				current = current.getLeft();
			else 
				current = current.getRight();
		}
		return false;
	}

	@Override
	public String toString() {
		LinkedListOrdered<X> arrayRepresentation = new LinkedListOrdered<>();

		return "";
	}

	// private boolean equals(Node thisRoot, Node modelRoot) {
	// 	if (thisRoot.getInfo() != modelRoot.getInfo()) return false;
	// 	if (thisRoot.getInfo() == modelRoot.getInfo() && thisRoot.getInfo() == null) return true;

	// 	return this.equals(thisRoot.getLeft(), modelRoot.getLeft());
	// }

	@Override
	@SuppressWarnings("unchecked")
	public boolean equals(Object obj) {
		try {
			if (this == obj) return true;
			if (obj == null) return false;
			if (this.getClass() != obj.getClass()) return false;
			
			BinarySearchTree<X> model = (BinarySearchTree<X>) obj;
			
			if (this.root.getInfo() != model.root.getInfo()) return false;
			if (this.size != model.size) return false;
			if (this.height != model.height) return false;

			return (this.getOrderedArray(this.root).equals(model.getOrderedArray(model.root)));
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
}
