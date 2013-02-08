package com.jpoweredcart.common.web.bridge.conversion;

import java.util.List;

import com.jpoweredcart.common.utils.Literal;

public interface DateFormatTranslator {

	public static final List<Character> JAVA_DFMT_ALL_CHARS = Literal.list(
			'G','y','M','w','W','D','d','F','E','a','H','k','K','h','m','s','S','z','Z'
	);
	
	public String translate(String pattern);
	
	public String translate(String pattern, boolean validate);
	
}
