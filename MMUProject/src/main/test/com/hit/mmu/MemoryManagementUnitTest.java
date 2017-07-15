package com.hit.mmu;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import com.hit.algorithm.LFUAlgoCacheImpl;
import com.hit.algorithm.LRUAlgoCacheImpl;
import com.hit.algorithm.SecondChanceAlgoCacheImpl;
import com.hit.memoryunits.MemoryManagementUnit;
import com.hit.memoryunits.Page;

public class MemoryManagementUnitTest {
	
	static final int Ram_cap = 3;
	 
	@Test
	public void test() throws IOException, ClassNotFoundException {
		MemoryManagementUnit lfu = new MemoryManagementUnit(Ram_cap,new LFUAlgoCacheImpl<>(Ram_cap));
		MemoryManagementUnit lru= new MemoryManagementUnit(Ram_cap,new LRUAlgoCacheImpl<>(Ram_cap));
		MemoryManagementUnit scndChance = new MemoryManagementUnit(Ram_cap,new SecondChanceAlgoCacheImpl(Ram_cap));
		Long[] test_lfu={9L,4L,2L,10L,9L,20l};
		// Defined RAM capacity is 3, we running LFU algorithm while inserting 5 pages. 2 last pages to insert should be replaced or change content if already in RAM.
		// PLease see output.
		Page<byte[]>[] data= (Page<byte[]>[]) new  Page[test_lfu.length];
		System.out.println("TESTING LFU");
		data=lfu.getPages(test_lfu);
		
		
		// Same test for LRU
		Long[] test_lru={9L,4L,2L,10L,9L,10L};
		System.out.println("\nTESTING LRU");
		data=lru.getPages(test_lru);
		
		// Same test for RR
		Long[] test_sc={9L,4L,2L,10L,9L,10L};
		System.out.println("\nTESTING Second Chance");
		data=scndChance.getPages(test_sc);

	
		
		
		
		
		
	}
}
