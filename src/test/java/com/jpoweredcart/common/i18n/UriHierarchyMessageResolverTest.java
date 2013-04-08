package com.jpoweredcart.common.i18n;

import java.io.File;
import java.util.Arrays;

import junit.framework.Assert;

import org.junit.Test;

import com.jpoweredcart.common.web.mock.MockHttpServletRequest;

public class UriHierarchyMessageResolverTest {

	private UriHierarchyMessageResolver messageResolver;
	
	public UriHierarchyMessageResolverTest(){
		messageResolver = new UriHierarchyMessageResolver();
	}
	
	@Test
	public void testGetMessagePaths(){
		
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setContextPath("/jpoweredcart");
		
		String uri = "/jpoweredcart/admin/localisation/language";
		request.setRequestURI(uri);
		String messagePaths[] = messageResolver.getMessagePaths(request);
		System.out.println(Arrays.toString(messagePaths));
		Assert.assertEquals(3, messagePaths.length);
		String part1 = File.separator+"admin";
		String part2 = part1 + File.separator + "localisation";
		String part3 = part2 + File.separator + "language";
		Assert.assertEquals(part1, messagePaths[0]);
		Assert.assertEquals(part2, messagePaths[1]);
		Assert.assertEquals(part3, messagePaths[2]);
		
		//add / at the end of URI
		uri = "/jpoweredcart/admin/localisation/language/";
		request.setRequestURI(uri);
		messagePaths = messageResolver.getMessagePaths(request);
		System.out.println(Arrays.toString(messagePaths));
		Assert.assertEquals(3, messagePaths.length);
		Assert.assertEquals(part1, messagePaths[0]);
		Assert.assertEquals(part2, messagePaths[1]);
		Assert.assertEquals(part3, messagePaths[2]);
		
		//add parameters
		uri = "/jpoweredcart/admin/localisation/language?key1=value1&key2=value2";
		request.setRequestURI(uri);
		messagePaths = messageResolver.getMessagePaths(request);
		System.out.println(Arrays.toString(messagePaths));
		Assert.assertEquals(3, messagePaths.length);
		Assert.assertEquals(part1, messagePaths[0]);
		Assert.assertEquals(part2, messagePaths[1]);
		Assert.assertEquals(part3, messagePaths[2]);
		
		//add parameters and /
		uri = "/jpoweredcart/admin/localisation/language/?key1=value1&key2=value2";
		request.setRequestURI(uri);
		messagePaths = messageResolver.getMessagePaths(request);
		
		System.out.println(Arrays.toString(messagePaths));
		Assert.assertEquals(3, messagePaths.length);
		Assert.assertEquals(part1, messagePaths[0]);
		Assert.assertEquals(part2, messagePaths[1]);
		Assert.assertEquals(part3, messagePaths[2]);
		
		uri = "/jpoweredcart/admin/localisation/language/create";
		String part4 = part3+File.separator+"create";
		request.setRequestURI(uri);
		messagePaths = messageResolver.getMessagePaths(request);
		
		System.out.println(Arrays.toString(messagePaths));
		Assert.assertEquals(4, messagePaths.length);
		Assert.assertEquals(part1, messagePaths[0]);
		Assert.assertEquals(part2, messagePaths[1]);
		Assert.assertEquals(part3, messagePaths[2]);
		Assert.assertEquals(part4, messagePaths[3]);
		
		uri = "/jpoweredcart/admin/localisation/language/create/";
		request.setRequestURI(uri);
		messagePaths = messageResolver.getMessagePaths(request);
		
		System.out.println(Arrays.toString(messagePaths));
		Assert.assertEquals(4, messagePaths.length);
		Assert.assertEquals(part1, messagePaths[0]);
		Assert.assertEquals(part2, messagePaths[1]);
		Assert.assertEquals(part3, messagePaths[2]);
		Assert.assertEquals(part4, messagePaths[3]);
		
		uri = "/jpoweredcart/admin/localisation/language/create;jsessionid=34343?key=1";
		request.setRequestURI(uri);
		messagePaths = messageResolver.getMessagePaths(request);
		
		System.out.println(Arrays.toString(messagePaths));
		Assert.assertEquals(4, messagePaths.length);
		Assert.assertEquals(part1, messagePaths[0]);
		Assert.assertEquals(part2, messagePaths[1]);
		Assert.assertEquals(part3, messagePaths[2]);
		Assert.assertEquals(part4, messagePaths[3]);
		
		uri = "/jpoweredcart/admin/localisation/language/create/;jsessionid=34343?key=1";
		request.setRequestURI(uri);
		messagePaths = messageResolver.getMessagePaths(request);
		
		System.out.println(Arrays.toString(messagePaths));
		Assert.assertEquals(4, messagePaths.length);
		Assert.assertEquals(part1, messagePaths[0]);
		Assert.assertEquals(part2, messagePaths[1]);
		Assert.assertEquals(part3, messagePaths[2]);
		Assert.assertEquals(part4, messagePaths[3]);
		
		/* remove context path */
		request.setContextPath("");
		uri = "/jpoweredcart/admin/localisation/language/create/;jsessionid=34343?key=1";
		request.setRequestURI(uri);
		messagePaths = messageResolver.getMessagePaths(request);
		
		System.out.println(Arrays.toString(messagePaths));
		Assert.assertFalse(part1.equals(messagePaths[0]));
		Assert.assertFalse(part2.equals(messagePaths[1]));
		Assert.assertFalse(part3.equals(messagePaths[2]));
		Assert.assertFalse(part4.equals(messagePaths[3]));
	}
	
}


