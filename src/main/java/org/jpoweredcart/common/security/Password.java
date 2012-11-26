package org.jpoweredcart.common.security;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

public class Password {
	
	/**
	 * This method comply to OpenCart password hashing algorithm.
	 * Currently OpenCart uses the same hashing algorithm for admin account
	 * and customer account.
	 * @param salt
	 * @param password
	 * @return
	 */
	public static String encode(String salt, String password){
		ShaPasswordEncoder sha1 = new ShaPasswordEncoder();
		String result = sha1.encodePassword(salt+sha1.encodePassword(salt+sha1.encodePassword(password,null),null),null);
		return result;
	}
	
	public static String generateSalt(){
		return RandomStringUtils.randomAlphanumeric(9);
	}
	
	
}
