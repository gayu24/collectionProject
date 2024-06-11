package com.collectionProject;

import java.util.HashMap;
import java.util.Map;

public class InventoryItems<K , V> {
	
		private final int capacity; // Maximum capacity of the cache
		private final Map<K, Node<K, V>> map; // Map to store key-node pairs
		private final CustomLinkedList<K, V> list; // Doubly linked list to store the order of elements

	// constructor for global variables of InventoryItems class 
		public InventoryItems(int capacity) {
			this.capacity = capacity;
			this.map = new HashMap<>();
			this.list = new CustomLinkedList<>();
		}

		public V get(K key) {
			Node<K, V> node = map.get(key);
			if (node == null) {
				return null;
			}
			list.moveToHead(node);
			return node.value;
		}

		public void put(K key, V value) {
			Node<K, V> node = map.get(key);
			if (node == null) {
				node = new Node<>(key, value);
				map.put(key, node);
				list.addToHead(node);
				if (map.size() > capacity) {
					Node<K, V> tail = list.removeTail();
					map.remove(tail.key);
				}
			} else {
				node.value = value;
				list.moveToHead(node);
			}
		}

		private static class Node<K, V> {
			K key;
			V value;
			Node<K, V> prev;
			Node<K, V> next;

			Node(K key, V value) {
				this.key = key;
				this.value = value;
			}
		}

		private static class CustomLinkedList<K, V> {
			private Node<K, V> head;
			private Node<K, V> tail;

			void addToHead(Node<K, V> node) {
				if (head == null) {
					head = tail = node;
				} else {
					node.next = head;
					head.prev = node;
					head = node;
				}
			}

			void moveToHead(Node<K, V> node) {
				if (node == head) {
					return;
				}
				if (node == tail) {
					tail = tail.prev;
					tail.next = null;
				} else {
					node.prev.next = node.next;
					node.next.prev = node.prev;
				}
				node.next = head;
				node.prev = null;
				head.prev = node;
				head = node;
			}

			Node<K, V> removeTail() {
				if (tail == null) {
					return null;
				}
				Node<K, V> removed = tail;
				if (tail == head) {
					head = tail = null;
				} else {
					tail = tail.prev;
					tail.next = null;
				}
				removed.prev = null;
				return removed;
			}
		}

}
