package net.javajigi.adder;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

import org.junit.Test;


public class CollectionTest {
	@Test
	public void concurrent() throws Exception {
		CopyOnWriteArrayList list = new CopyOnWriteArrayList();
		list.add("vivek");
		list.add("kumar");
		Iterator i =list.iterator();
		while(i.hasNext()){
			System.out.println(i.next());
			list.add("abhishek");
		}
		
		System.out.println("After modification:");
		System.out.println("Size:" + list.size());
		Iterator i2 =list.iterator();
		while(i2.hasNext()){
			System.out.println(i2.next());
		}
	}
}
