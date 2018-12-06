// Andrew Pilon
// Assignment 2
//
// thank goodness for Math.abs() so that my timing test doesn't take 292471208 years to complete :)
//
// NOTE: I tried to implement my own ArrayList class but was getting issues with "<>" and "type-variables,"
//		 so I switched to using java's ArrayList.
//
//		 Also, this implementation of the code works with Hashtable<K,V> or just by itself (no "<>"). I'm
//		 not sure if it is incorrect or bad style to leave it blank, but all the tests pass so I
//		 decided to leave it as is.

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Random;
import java.util.ArrayList;

public class Hashtable {

	// initialize variables (private vs. public didn't change anything, not sure if that matters)
	private ArrayList<HashNode> buckets;
	private int num_buckets;
	private int size;

	public Hashtable() {
		// I left num_buckets as the maximum amount from the range you gave us because
		// it gave me the fastest runtimes (about 90-105ms)
		num_buckets = 314527;
		size = 0;
		buckets = new ArrayList<>();
		for (int i=0; i<num_buckets; i++) {
			buckets.add(null);
		}
	}

	// create HashNode class for chain linking
	public class HashNode {
		public String key;
		public String value;
		public HashNode next;
		public HashNode(String key, String value) {
			this.key = key;
			this.value = value;
		}
	}

	public int getBucketIndex(String key) {
		// using default java hash function WITH ABS CAUSE THAT'S VERY IMPORTANT
		int hashCode = Math.abs(key.hashCode());
		int index = hashCode%num_buckets;
		return index;
	}
	/**
	 * Returns “true” if a key/value object pair (with the key matching the 
	 * argument and any value).
	 */
	public boolean containsKey(String key) {
		int bucketIndex = getBucketIndex(key); //get index from hash function
		HashNode head = buckets.get(bucketIndex); // try to get head

		while (head != null) { // search for key
			if (head.key.equals(key)) {
				return true; // found the key
			}
			head = head.next;
		}
		return false; // didn't.

	}

	/**
	 * Returns the value associated with the key which is passed as an argument;
	 * returns null if no key/value pair is contained by the Hashtable instance.
	 */
	public String get(String key) {
		int bucketIndex = getBucketIndex(key); //get index from hash function
		HashNode head = buckets.get(bucketIndex); // get head

		while(head != null) { // iterate through chain
			if (head.key.equals(key)) {
				return head.value;
			}
			head = head.next;
		}
		return null; // no key/value pair found
	}

	/**
	 * Adds the key/value pair into the Hashtable instance. If there is an existing 
	 * key/value pair, the Hashtable instance replaces the stored value with the argument 
	 * value.
	 */
	public void put(String key, String value) {
		int bucketIndex = getBucketIndex(key); //get index from hash function
		HashNode head = buckets.get(bucketIndex); // get head
	
		while (head != null) { // iterate through chain, see if key already there
			if (head.key.equals(key)) {
				head.value = value; // overwrite value at key
				return;
			}
			head = head.next;
		}

		// key doesn't already exist so we need to put it there
		head = buckets.get(bucketIndex);
		HashNode newHashNode = new HashNode(key, value); // make new node with info
		newHashNode.next = head; // connect to chain
		buckets.set(bucketIndex, newHashNode);
		size++; // increment size
	}

	/**
	 * Removes the key/value pair from the Hashtable instance and returns the value 
	 * associated with the key to the caller. Throws an Exception instance if the key 
	 * is not present in the Hashtable instance.
	 */
	public String remove(String key) {
		int bucketIndex = getBucketIndex(key); //get index from hash function
		HashNode head = buckets.get(bucketIndex); // get head
		HashNode prev = null; // set placeholder for checking key values

		while (head != null) { // search for key
			if (head.key.equals(key)) {
				break; // found the key
			}
			prev = head;
			head = head.next;
		}
		if (head == null) { // key doesn't exist
			throw new NullPointerException();
		}
		if (prev != null) {
			prev.next = head.next; // remove by overwriting
		} else {
			buckets.set(bucketIndex, head.next);
		}
		size--; // decrement size after removing
		return head.value;

	}
}

