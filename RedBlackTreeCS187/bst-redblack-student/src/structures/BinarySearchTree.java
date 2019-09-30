package structures;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class BinarySearchTree<T extends Comparable<T>> implements
		BSTInterface<T> {
	protected BSTNode<T> root;

	public boolean isEmpty() {
		return root == null;
	}

	public int size() {
		return subtreeSize(root);
	}

	protected int subtreeSize(BSTNode<T> node) {
		if (node == null) {
			return 0;
		} else {
			return 1 + subtreeSize(node.getLeft())
					+ subtreeSize(node.getRight());
		}
	}

	public boolean contains(T t) {
		// TODO
		if(t == null) throw new NullPointerException();
		if (get(t) == null) return false;
		return true;
	}

	public boolean remove(T t) {
		if (t == null) {
			throw new NullPointerException();
		}
		boolean result = contains(t);
		if (result) {
			root = removeFromSubtree(root, t);
		}
		return result;
	}

	private BSTNode<T> removeFromSubtree(BSTNode<T> node, T t) {
		// node must not be null
		int result = t.compareTo(node.getData());
		if (result < 0) {
			node.setLeft(removeFromSubtree(node.getLeft(), t));
			if (node.getLeft() != null) { 
				node.getLeft().setParent(node);
			}
			return node;
		} else if (result > 0) {
			node.setRight(removeFromSubtree(node.getRight(), t));
			if (node.getRight() != null){
				node.getRight().setParent(node);
			}
			return node;
		} else { // result == 0
			if (node.getLeft() == null) {
				return node.getRight();
			} else if (node.getRight() == null) {
				return node.getLeft();
			} else { // neither child is null
				T predecessorValue = getHighestValue(node.getLeft());
				node.setLeft(removeRightmost(node.getLeft()));
				if (node.getLeft() != null) { 
					node.getLeft().setParent(node);
				}
				node.setData(predecessorValue);
				return node;
			}
		}
	}

	private T getHighestValue(BSTNode<T> node) {
		// node must not be null
		if (node.getRight() == null) {
			return node.getData();
		} else {
			return getHighestValue(node.getRight());
		}
	}

	private BSTNode<T> removeRightmost(BSTNode<T> node) {
		// node must not be null
		if (node.getRight() == null) {
			return node.getLeft();
		} else {
			node.setRight(removeRightmost(node.getRight()));
			if (node.getRight() != null){
				node.getRight().setParent(node);
			}
			return node;
		}
	}

	public T get(T t) {
		// TODO
		if(t == null) throw new NullPointerException();
		/*if (root == null) return null;
		if (t.compareTo(root.getData()) == 0) {
			return root.getData();
		}
		else if (t.compareTo(root.getData()) < 0) {
			T left = root.getLeft().getData();
			root = root.getLeft();
			return left;
		}
		else {
			T right = root.getRight().getData();
			root = root.getRight();
			return root.getRight().getData();
		}*/
		return recGet(t,root);
	}
	public T recGet(T element, BSTNode<T> node) {
		if(node == null) return null;
		else if (element.compareTo(node.getData()) < 0) {
			return recGet(element, node.getLeft());
		}
		else if (element.compareTo(node.getData()) > 0) {
			return recGet(element, node.getRight());
		}
		else 
			return node.getData();
	}

	public void add(T t) {
		if (t == null) {
			throw new NullPointerException();
		}
		root = addToSubtree(root, new BSTNode<T>(t, null, null));
	}

	protected BSTNode<T> addToSubtree(BSTNode<T> node, BSTNode<T> toAdd) {
		if (node == null) {
			return toAdd;
		}
		int result = toAdd.getData().compareTo(node.getData());
		if (result <= 0) {
			node.setLeft(addToSubtree(node.getLeft(), toAdd));
			if (node.getLeft() != null) { 
				node.getLeft().setParent(node);
			}
		} else {
			node.setRight(addToSubtree(node.getRight(), toAdd));
			if (node.getRight() != null){
				node.getRight().setParent(node);
			}
		}
		return node;
	}

	@Override
	public T getMinimum() {
		// TODO
		if (isEmpty()) return null;
		return getSmallestValue(root);
	}
	
	public T getSmallestValue(BSTNode<T> node) {
		if (node.getLeft() == null) {
			return node.getData();
		} else {
			return getSmallestValue(node.getLeft());
		}
	}


	@Override
	public T getMaximum() {
		// TODO
		if (isEmpty()) return null;
		return getHighestValue(root);
	}


	@Override
	public int height() {
		// TODO
		if (isEmpty()) return -1; 
		/*if (root.getRight() == null && root.getLeft() == null) return 0;
		if (!(root.getLeft() == null)) {
			root = root.getLeft();
			return 1 + height();
		}
		if (!(root.getRight() == null)) {
			root = root.getRight();
			return 1 + height();
		}
		return height();*/
		return height(root);
	}
	public int height(BSTNode<T> node) {
		if (node == null) return -1;
		return Math.max(height(node.getLeft()), height(node.getRight())) + 1;
	}


	public Iterator<T> preorderIterator() {
		// TODO
		Queue<T> queue = new LinkedList<T>();
		preorderTraverse(queue,root);
		return queue.iterator();
	}
	public void preorderTraverse(Queue<T> queue, BSTNode<T> node) {
		if (node != null) {
			queue.add(node.getData());
			preorderTraverse(queue, node.getLeft());
			preorderTraverse(queue, node.getRight());
		}
	}

	public Iterator<T> inorderIterator() {
		Queue<T> queue = new LinkedList<T>();
		inorderTraverse(queue, root);
		return queue.iterator();
	}


	private void inorderTraverse(Queue<T> queue, BSTNode<T> node) {
		if (node != null) {
			inorderTraverse(queue, node.getLeft());
			queue.add(node.getData());
			inorderTraverse(queue, node.getRight());
		}
	}

	public Iterator<T> postorderIterator() {
		// TODO
		Queue<T> queue = new LinkedList<T>();
		postorderTraverse(queue, root);
		return queue.iterator();
	}
	private void postorderTraverse(Queue<T> queue, BSTNode<T> node) {
		if (node != null) {
			postorderTraverse(queue, node.getLeft());
			postorderTraverse(queue, node.getRight());
			queue.add(node.getData());
		}
	}


	@Override
	public boolean equals(BSTInterface<T> other) {
		// TODO
		return equalsHelp(root, other.getRoot());
	}
	/*public boolean equalsHelp(BSTNode<T> a, BSTNode<T> b) {
		if (a == null || b == null) return false;
		else if (a == null && b == null) return true;
		else if (a.getData().equals(b.getData())) return true;
		return (equalsHelp(a.getLeft(), b.getLeft()) && equalsHelp(a.getRight(), b.getRight()));
	}*/
	private boolean equalsHelp(BSTNode<T> one, BSTNode<T> two) {
		if (one == null && two == null) return true;
		else if (one == null || two == null) return false;
		else {
			if (!one.getData().equals(two.getData())) return false;
			return equalsHelp(one.getLeft(), two.getLeft()) && equalsHelp(one.getRight(), two.getRight());
		}
	}
	

	@Override
	public boolean sameValues(BSTInterface<T> other) {
		// TODO
	/*	if (other == null) throw new NullPointerException();
		return sameValuesHelp(other, root);
	}
	public boolean sameValuesHelp(BSTInterface<T> tree, BSTNode<T> node) {
		if (node == null) return true;
		if (tree.isEmpty()) return false;
		else if (tree.contains(node.getData())) return true;
		return (sameValuesHelp(tree, node.getLeft()) && sameValuesHelp(tree, node.getRight()));
		
	}*/
		Iterator<T> iter1 = this.inorderIterator();
		Iterator<T> iter2 = other.inorderIterator();
		while (iter1.hasNext() && iter2.hasNext()) 
			if (!iter1.next().equals(iter2.next()))
				return false;
		return !iter1.hasNext() && !iter2.hasNext();
	}

	@Override
	public boolean isBalanced() {
		// TODO
		return Math.pow(2, height()) <= size() && size() < Math.pow(2, height() + 1);
	}

	@Override
    @SuppressWarnings("unchecked")

	public void balance() {
		// TODO
		Iterator<T> i = this.inorderIterator();
		T[] a = (T[]) new Comparable[size()];
		int index = 0;
		while (i.hasNext()) {
			a[index] = i.next();
			index++;
		}
		root = build(a, 0, a.length-1);
		
		
	}
	public BSTNode<T> build(T[] a, int start, int end) {
		if (start > end) return null;
		int mid = (start+end)/2;
		BSTNode<T> node = new BSTNode<T>(a[mid],null,null);
		node.setLeft(build(a, start, mid -1));
		node.setRight(build(a, mid + 1, end));
		return node;
	}

	@Override
	public BSTNode<T> getRoot() {
        // DO NOT MODIFY
		return root;
	}

	public static <T extends Comparable<T>> String toDotFormat(BSTNode<T> root) {
		// header
		int count = 0;
		String dot = "digraph G { \n";
		dot += "graph [ordering=\"out\"]; \n";
		// iterative traversal
		Queue<BSTNode<T>> queue = new LinkedList<BSTNode<T>>();
		queue.add(root);
		BSTNode<T> cursor;
		while (!queue.isEmpty()) {
			cursor = queue.remove();
			if (cursor.getLeft() != null) {
				// add edge from cursor to left child
				dot += cursor.getData().toString() + " -> "
						+ cursor.getLeft().getData().toString() + ";\n";
				queue.add(cursor.getLeft());
			} else {
				// add dummy node
				dot += "node" + count + " [shape=point];\n";
				dot += cursor.getData().toString() + " -> " + "node" + count
						+ ";\n";
				count++;
			}
			if (cursor.getRight() != null) {
				// add edge from cursor to right child
				dot += cursor.getData().toString() + " -> "
						+ cursor.getRight().getData().toString() + ";\n";
				queue.add(cursor.getRight());
			} else {
				// add dummy node
				dot += "node" + count + " [shape=point];\n";
				dot += cursor.getData().toString() + " -> " + "node" + count
						+ ";\n";
				count++;
			}

		}
		dot += "};";
		return dot;
	}

	public static void main(String[] args) {
		for (String r : new String[] { "a", "b", "c", "d", "e", "f", "g" }) {
			BSTInterface<String> tree = new BinarySearchTree<String>();
			for (String s : new String[] { "d", "b", "a", "c", "f", "e", "g" }) {
				tree.add(s);
			}
			Iterator<String> iterator = tree.inorderIterator();
			while (iterator.hasNext()) {
				System.out.print(iterator.next());
			}
			System.out.println();
			iterator = tree.preorderIterator();
			while (iterator.hasNext()) {
				System.out.print(iterator.next());
			}
			System.out.println();
			iterator = tree.postorderIterator();
			while (iterator.hasNext()) {
				System.out.print(iterator.next());
			}
			System.out.println();

			System.out.println(tree.remove(r));

			iterator = tree.inorderIterator();
			while (iterator.hasNext()) {
				System.out.print(iterator.next());
			}
			System.out.println();
		}

		BSTInterface<String> tree = new BinarySearchTree<String>();
		for (String r : new String[] { "a", "b", "c", "d", "e", "f", "g" }) {
			tree.add(r);
		}
		System.out.println(tree.size());
		System.out.println(tree.height());
		System.out.println(tree.isBalanced());
		tree.balance();
		System.out.println(tree.size());
		System.out.println(tree.height());
		System.out.println(tree.isBalanced());
	}
}