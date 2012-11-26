package org.jpoweredcart.common.i18n;

import java.io.File;

import junit.framework.Assert;

import org.jpoweredcart.common.i18n.CustomMessageResolver;
import org.junit.Test;

public class CustomMessageResolverTest {
	
	private CustomMessageResolver messageResolver;
	
	public CustomMessageResolverTest(){
		messageResolver = new CustomMessageResolver();
	}
	
	@Test
	public void testExtractMessagePaths(){
		
		String uri = "/jpoweredcart/admin/localisation/language";
		String messagePaths[] = messageResolver.extractMessageParts(uri);
		Assert.assertEquals(2, messagePaths.length);
		String part1 = File.separator+"admin";
		String part2 = part1 + File.separator + "localisation";
		String part3 = part2 + File.separator + "language";
		Assert.assertEquals(part1, messagePaths[0]);
		Assert.assertEquals(part3, messagePaths[1]);
		
		//add / at the end of URI
		uri = "/jpoweredcart/admin/localisation/language/";
		messagePaths = messageResolver.extractMessageParts(uri);
		Assert.assertEquals(2, messagePaths.length);
		Assert.assertEquals(part1, messagePaths[0]);
		Assert.assertEquals(part3, messagePaths[1]);
		
		//add parameters
		uri = "/jpoweredcart/admin/localisation/language?key1=value1&key2=value2";
		messagePaths = messageResolver.extractMessageParts(uri);
		Assert.assertEquals(2, messagePaths.length);
		Assert.assertEquals(part1, messagePaths[0]);
		Assert.assertEquals(part3, messagePaths[1]);
		
		//add parameters and /
		uri = "/jpoweredcart/admin/localisation/language/?key1=value1&key2=value2";
		messagePaths = messageResolver.extractMessageParts(uri);
		Assert.assertEquals(2, messagePaths.length);
		Assert.assertEquals(part1, messagePaths[0]);
		Assert.assertEquals(part3, messagePaths[1]);
		
		uri = "/jpoweredcart/admin/localisation/language/create";
		messagePaths = messageResolver.extractMessageParts(uri);
		Assert.assertEquals(2, messagePaths.length);
		Assert.assertEquals(part1, messagePaths[0]);
		Assert.assertEquals(part3, messagePaths[1]);
	}
	
	public static void main(String[] args) {
		long t1 = System.currentTimeMillis();
		CustomMessageResolver t = new CustomMessageResolver();
		for(int i=0; i<100000; i++){
			t.extractMessageParts("/jpoweredcart/admin/localisation/language/?key1=value1&key2=value2");
		}
		System.out.println(System.currentTimeMillis()-t1);
	}
	
}
