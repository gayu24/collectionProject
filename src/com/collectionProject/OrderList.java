package com.collectionProject;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;

public class OrderList <E> extends AbstractList<E> {
	private Object[] elements;    // Array to store elements
	private int size;             // Current size of the list

	
	public OrderList() {         // constructor forr
		elements = new Object[10];
		size = 0;
	}

	// if the index is out of the range then throws IndexOutOfBoundsException	
	@Override
	public E get(int index) {
		if (index >= size || index < 0) {
			
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
		}
		return (E) elements[index];
	}

	
	@Override
	public boolean add(E e) {
		if (size == elements.length) {
			elements = Arrays.copyOf(elements, size * 2); // Resize array if needed
		}
		elements[size++] = e;
		return true;
	}

	// if the index is out of the range then throws IndexOutOfBoundsException	
	@Override
	public E remove(int index) {
		if (index >= size || index < 0) {
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
		}
		E oldValue = (E) elements[index];
		int numMoved = size - index - 1;
		if (numMoved > 0) {
			System.arraycopy(elements, index + 1, elements, index, numMoved);
		}
		elements[--size] = null; // Clear to let GC do its work
		return oldValue;
	}

	@Override
	public int size() {
		return size;
	}
}


