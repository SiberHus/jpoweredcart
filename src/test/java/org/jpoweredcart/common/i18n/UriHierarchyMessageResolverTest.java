package org.jpoweredcart.common.i18n;

import java.io.File;
import java.util.Arrays;

import junit.framework.Assert;

import org.jpoweredcart.common.i18n.UriHierarchyMessageResolver;
import org.junit.Test;

public class UriHierarchyMessageResolverTest {

	private UriHierarchyMessageResolver messageResolver;
	
	public UriHierarchyMessageResolverTest(){
		messageResolver = new UriHierarchyMessageResolver();
	}
	
	@Test
	public void testExtractMessagePaths(){
		
		String uri = "/jpoweredcart/admin/localisation/language";
		String messagePaths[] = messageResolver.extractMessageParts(uri);
		Assert.assertEquals(3, messagePaths.length);
		String part1 = File.separator+"admin";
		String part2 = part1 + File.separator + "localisation";
		String part3 = part2 + File.separator + "language";
		Assert.assertEquals(part1, messagePaths[0]);
		Assert.assertEquals(part2, messagePaths[1]);
		Assert.assertEquals(part3, messagePaths[2]);
		
		//add / at the end of URI
		uri = "/jpoweredcart/admin/localisation/language/";
		messagePaths = messageResolver.extractMessageParts(uri);
		Assert.assertEquals(3, messagePaths.length);
		Assert.assertEquals(part1, messagePaths[0]);
		Assert.assertEquals(part2, messagePaths[1]);
		Assert.assertEquals(part3, messagePaths[2]);
		
		//add parameters
		uri = "/jpoweredcart/admin/localisation/language?key1=value1&key2=value2";
		messagePaths = messageResolver.extractMessageParts(uri);
		Assert.assertEquals(3, messagePaths.length);
		Assert.assertEquals(part1, messagePaths[0]);
		Assert.assertEquals(part2, messagePaths[1]);
		Assert.assertEquals(part3, messagePaths[2]);
		
		//add parameters and /
		uri = "/jpoweredcart/admin/localisation/language/?key1=value1&key2=value2";
		messagePaths = messageResolver.extractMessageParts(uri);
		
		System.out.println(Arrays.toString(messagePaths));
		Assert.assertEquals(3, messagePaths.length);
		Assert.assertEquals(part1, messagePaths[0]);
		Assert.assertEquals(part2, messagePaths[1]);
		Assert.assertEquals(part3, messagePaths[2]);
		
		uri = "/jpoweredcart/admin/localisation/language/create";
		String part4 = part3+File.separator+"create";
		messagePaths = messageResolver.extractMessageParts(uri);
		
		System.out.println(Arrays.toString(messagePaths));
		Assert.assertEquals(4, messagePaths.length);
		Assert.assertEquals(part1, messagePaths[0]);
		Assert.assertEquals(part2, messagePaths[1]);
		Assert.assertEquals(part3, messagePaths[2]);
		Assert.assertEquals(part4, messagePaths[3]);
		
		uri = "/jpoweredcart/admin/localisation/language/create/";
		messagePaths = messageResolver.extractMessageParts(uri);
		
		System.out.println(Arrays.toString(messagePaths));
		Assert.assertEquals(4, messagePaths.length);
		Assert.assertEquals(part1, messagePaths[0]);
		Assert.assertEquals(part2, messagePaths[1]);
		Assert.assertEquals(part3, messagePaths[2]);
		Assert.assertEquals(part4, messagePaths[3]);
		
		uri = "/jpoweredcart/admin/localisation/language/create;jsessionid=34343?key=1";
		messagePaths = messageResolver.extractMessageParts(uri);
		
		System.out.println(Arrays.toString(messagePaths));
		Assert.assertEquals(4, messagePaths.length);
		Assert.assertEquals(part1, messagePaths[0]);
		Assert.assertEquals(part2, messagePaths[1]);
		Assert.assertEquals(part3, messagePaths[2]);
		Assert.assertEquals(part4, messagePaths[3]);
		
		uri = "/jpoweredcart/admin/localisation/language/create/;jsessionid=34343?key=1";
		messagePaths = messageResolver.extractMessageParts(uri);
		
		System.out.println(Arrays.toString(messagePaths));
		Assert.assertEquals(4, messagePaths.length);
		Assert.assertEquals(part1, messagePaths[0]);
		Assert.assertEquals(part2, messagePaths[1]);
		Assert.assertEquals(part3, messagePaths[2]);
		Assert.assertEquals(part4, messagePaths[3]);
	}
	
	public static void main(String[] args) {
		long t1 = System.currentTimeMillis();
		UriHierarchyMessageResolver t = new UriHierarchyMessageResolver();
		for(int i=0; i<100000; i++){
			t.extractMessageParts("/jpoweredcart/admin/localisation/language/?key1=value1&key2=value2");
		}
		System.out.println(System.currentTimeMillis()-t1);
	}
	
}


