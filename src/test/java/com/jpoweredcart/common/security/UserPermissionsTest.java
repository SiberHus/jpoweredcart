package com.jpoweredcart.common.security;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;

import junit.framework.Assert;

import com.jpoweredcart.common.security.UserPermissions;
import org.junit.Test;


public class UserPermissionsTest {
	
	
	@Test
	public void testSerializeAndDeserializePermissionData(){
		String filenames[] = new String[]{"perms-short.txt", "perms.txt"};
		for(String filename: filenames){
			InputStream in = UserPermissionsTest.class.getResourceAsStream("/com/siberhus/jpoweredcart/common/security/"+filename);
			String before = readFileToString(in);
			Map<String, Set<String>> permMap = UserPermissions.unserializePermissions(before);
			String after = UserPermissions.serializePermissions(permMap);
			Assert.assertEquals(sumChars(before), sumChars(after));
		}
	}
	
	private long sumChars(String string){
		int sum = 0;
		for(char c: string.toCharArray()){
			sum+= c;
		}
		return sum;
	}
	
	private String readFileToString(InputStream in){
		StringBuilder string = new StringBuilder();
		BufferedInputStream bin = new BufferedInputStream(in);
		byte buffer[] = new byte[1024];
		try{
			int num = 0;
			while( (num=bin.read(buffer)) !=-1){
				String readData = new String(buffer, 0, num);
				string.append(readData);
			}
		}catch(IOException e){}
		finally{
			try{ bin.close(); }catch(IOException e){}
		}
		return string.toString();
	}
}


