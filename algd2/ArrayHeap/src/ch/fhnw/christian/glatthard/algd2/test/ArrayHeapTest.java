package ch.fhnw.christian.glatthard.algd2.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ch.fhnw.christian.glatthard.algd2.ArrayHeap;

public class ArrayHeapTest {
	
	ArrayHeap heap;
	
	@Before
	public void setup() {
		heap = new ArrayHeap();
		heap.insertKey(1);
		heap.insertKey(2);
		heap.insertKey(3);
		heap.insertKey(4);
		heap.insertKey(5);
		heap.insertKey(6);
		
	}
	
	@Test
	public void testInsertKey() {
		
		assertEquals(heap.insertKey(13), 6);
		assertEquals(heap.getNodeKey(6), 13);
		assertEquals(heap.size(), 7);
		
		assertEquals(heap.insertKey(12), 7);
		assertEquals(heap.getNodeKey(7), 12);
		assertEquals(heap.size(), 8);
		
		assertEquals(heap.insertKey(11), 8);
		assertEquals(heap.getNodeKey(8), 11);
		assertEquals(heap.size(), 9);
	}
	
	@Test
	public void testRelations() {
	
		/* Test getChild Functions */
		assertEquals(heap.getLeftChild(0).getKey(), 2);
		assertEquals(heap.getRightChild(0).getKey(), 3);
		
		/* Test getParent Function */
		assertEquals(heap.getParent(1).getKey(), 1);
		assertEquals(heap.getParent(2).getKey(), 1);
	}

	@Test
	public void testSiftDown1() {
		heap.siftDown(0, 3);
		assertEquals(heap.getNodeKey(0), 3);
	}
	
	public void testSiftDown2() {
		heap.siftDown(0, heap.size());
		assertEquals(heap.getNodeKey(0), 3);
	}
}
