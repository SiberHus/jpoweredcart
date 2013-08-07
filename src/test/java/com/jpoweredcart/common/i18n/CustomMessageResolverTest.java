package com.jpoweredcart.common.i18n;

import java.io.File;
import java.util.Arrays;

import junit.framework.Assert;

import com.jpoweredcart.common.i18n.CustomMessageResolver;
import com.jpoweredcart.common.mock.servlet.MockHttpServletRequest;

import org.junit.Test;

public class CustomMessageResolverTest {
	
	private CustomMessageResolver messageResolver;
	
	public CustomMessageResolverTest(){
		messageResolver = new CustomMessageResolver();
	}
	
	@Test
	public void testgetMessagePaths(){
		
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setContextPath("/jpoweredcart");
		
		String uri = "/jpoweredcart/admin/localisation/language";
		request.setRequestURI(uri);
		String messagePaths[] =  messageResolver.getMessagePaths(request);
		System.out.println(Arrays.toString(messagePaths));
		Assert.assertEquals(2, messagePaths.length);
		String part1 = File.separator+"admin";
		String part2 = part1 + File.separator + "localisation";
		String part3 = part2 + File.separator + "language";
		Assert.assertEquals(part1, messagePaths[0]);
		Assert.assertEquals(part3, messagePaths[1]);
		
		//add / at the end of URI
		uri = "/jpoweredcart/admin/localisation/language/";
		request.setRequestURI(uri);
		messagePaths =  messageResolver.getMessagePaths(request);
		System.out.println(Arrays.toString(messagePaths));
		Assert.assertEquals(2, messagePaths.length);
		Assert.assertEquals(part1, messagePaths[0]);
		Assert.assertEquals(part3, messagePaths[1]);
		
		//add parameters
		uri = "/jpoweredcart/admin/localisation/language?key1=value1&key2=value2";
		request.setRequestURI(uri);
		messagePaths =  messageResolver.getMessagePaths(request);
		System.out.println(Arrays.toString(messagePaths));
		Assert.assertEquals(2, messagePaths.length);
		Assert.assertEquals(part1, messagePaths[0]);
		Assert.assertEquals(part3, messagePaths[1]);
		
		//add parameters and /
		uri = "/jpoweredcart/admin/localisation/language/?key1=value1&key2=value2";
		request.setRequestURI(uri);
		messagePaths =  messageResolver.getMessagePaths(request);
		System.out.println(Arrays.toString(messagePaths));
		Assert.assertEquals(2, messagePaths.length);
		Assert.assertEquals(part1, messagePaths[0]);
		Assert.assertEquals(part3, messagePaths[1]);
		
		uri = "/jpoweredcart/admin/localisation/language/create";
		request.setRequestURI(uri);
		messagePaths =  messageResolver.getMessagePaths(request);
		System.out.println(Arrays.toString(messagePaths));
		Assert.assertEquals(2, messagePaths.length);
		Assert.assertEquals(part1, messagePaths[0]);
		Assert.assertEquals(part3, messagePaths[1]);
		
	}
	
}
