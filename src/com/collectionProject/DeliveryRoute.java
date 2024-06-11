package com.collectionProject;

import java.util.AbstractSequentialList;
import java.util.Collection;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class DeliveryRoute<E> extends AbstractSequentialList <E> {

	private Node<E> top; // Head of the list
	private Node<E> bottom; // Tail of the list
	private int size; // Current size of the list

	
	private static class Node<E> {
		E item;
		Node<E> next;
		Node<E> prev;
		
// default consructor for Node class
		Node(Node<E> prev, E element, Node<E> next) {
			this.item = element;
			this.next = next;
			this.prev = prev;
		}
	}
// public constructor for class  DeliveryRoute
	public DeliveryRoute() {
		top = null;
		bottom = null;
		size = 0;
	}

  //throws IndexOutOfBoundsException if the index is out of range
	 
	@Override
	public ListIterator<E> listIterator(int index) {
		return new ListItr(index);
	}

	private class ListItr implements ListIterator<E> {
		private Node<E> lastReturned;
		private Node<E> next;
		private int nextIndex;

		//throws IndexOutOfBoundsException if the index is out of range
		ListItr(int index) {
			if (index < 0 || index > size) {
				throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
			}
			if (index == size) {
				next = null;
				nextIndex = size;
			} else {
				next = node(index);
				nextIndex = index;
			}
		}

		public boolean hasNext() {
			return nextIndex < size;
		}

		public E next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			lastReturned = next;
			next = next.next;
			nextIndex++;
			return lastReturned.item;
		}

		public boolean hasPrevious() {
			return nextIndex > 0;
		}

		public E previous() {
			if (!hasPrevious()) {
				throw new NoSuchElementException();
			}
			lastReturned = next = (next == null) ? bottom : next.prev;
			nextIndex--;
			return lastReturned.item;
		}

		public int nextIndex() {
			return nextIndex;
		}

		public int previousIndex() {
			return nextIndex - 1;
		}

		public void remove() {
			if (lastReturned == null) {
				throw new IllegalStateException();
			}
			Node<E> lastNext = lastReturned.next;
			Node<E> lastPrev = lastReturned.prev;
			if (lastPrev == null) {
				top = lastNext;
			} else {
				lastPrev.next = lastNext;
				lastReturned.prev = null;
			}
			if (lastNext == null) {
				bottom = lastPrev;
			} else {
				lastNext.prev = lastPrev;
				lastReturned.next = null;
			}
			lastReturned.item = null;
			lastReturned = null;
			size--;
			nextIndex--;
		}

		public void set(E e) {
			if (lastReturned == null) {
				throw new IllegalStateException();
			}
			lastReturned.item = e;
		}

		public void add(E e) {
			lastReturned = null;
			if (next == null) {
				Node<E> newNode = new Node<>(bottom, e, null);
				if (bottom == null) {
					top = newNode;
				} else {
					bottom.next = newNode;
				}
				bottom = newNode;
			} else {
				Node<E> newNode = new Node<>(next.prev, e, next);
				next.prev = newNode;
				if (newNode.prev == null) {
					top = newNode;
				} else {
					newNode.prev.next = newNode;
				}
			}
			size++;
			nextIndex++;
		}
	}

	
	Node<E> node(int index) {
		if (index < (size >> 1)) {
			Node<E> x = top;
			for (int i = 0; i < index; i++) {
				x = x.next;
			}
			return x;
		} else {
			Node<E> x = bottom;
			for (int i = size - 1; i > index; i--) {
				x = x.prev;
			}
			return x;
		}
	}

	
	@Override
	public int size() {
		return size;
	}
}