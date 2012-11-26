package org.jpoweredcart.common.view;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.text.StrSubstitutor;
import org.jpoweredcart.common.PageParam;

public class Pagination {
	
	private int total = 0;
	private int page = 1;
	private int limit = 20;
	private int numLinks = 10;
	private String url = "";
	private String sortKey;
	private String orderDir;
	
	private String text = "Showing {start} to {end} of {total} ({pages} Pages)";
	private String textFirst = "|&lt;";
	private String textLast = "&gt;|";
	private String textNext = "&gt;";
	private String textPrev = "&lt;";
	private String styleLinks = "links";
	private String styleResults = "results";
	
	public Pagination setTotal(int total) { this.total = total; return this;}

	public Pagination setPage(int page) { this.page = page < 1? 1: page; return this;}
	
	public Pagination setLimit(int limit) { this.limit = limit < 1 ? 10: limit; return this;}
	
	public Pagination setNumLinks(int numLinks) {this.numLinks = numLinks; return this;}

	public Pagination setUrl(String url) { this.url = url; return this;}

	public Pagination setSortedField(String sortKey, String orderDir){
		this.sortKey = sortKey;
		this.orderDir = orderDir;
		return this;
	}
	
	public Pagination setPageParam(PageParam pageParam){
		setPage(pageParam.getPage());
		setLimit(pageParam.getLimit());
		setSortedField(pageParam.getSortKey(), pageParam.getOrderDir());
		return this;
	}
	
	public Pagination setText(String text) { this.text = text; return this;}

	public Pagination setTextFirst(String textFirst) {this.textFirst = textFirst; return this;}

	public Pagination setTextLast(String textLast) {this.textLast = textLast; return this;}

	public Pagination setTextNext(String textNext) {this.textNext = textNext; return this;}

	public Pagination setTextPrev(String textPrev) {this.textPrev = textPrev; return this;}

	public Pagination setStyleLinks(String styleLinks) {this.styleLinks = styleLinks; return this;}

	public Pagination setStyleResults(String styleResults) {this.styleResults = styleResults; return this;}
	
	public Pagination(){}
	
	public String render(){
		
		boolean hasParamUrl = false;
		if(sortKey!=null && orderDir!=null){
			url += "?sort="+sortKey+"&amp;order="+orderDir;
			hasParamUrl = true;
		}else{
			hasParamUrl = url.indexOf("?")!=-1;
		}
		
		int numPages = (int)Math.ceil((double)total/limit);
		String pageUrl = url+(hasParamUrl?"&amp;":"?")+"page=";
		
		StringBuilder output = new StringBuilder();
		if(page>1){
			output.append("<a href=\"").append(pageUrl).append("1\">")
			.append(textFirst).append("</a> <a href=\"").append(pageUrl).append(page-1).append("\">")
			.append(textPrev).append("</a>");
		}
		if(numPages>1){
			int start, end = 0;
			if(numPages <= numLinks){
				start = 1;
				end = numPages;
			}else{
				start = page - (int)Math.floor(numLinks/2);
				end = page + (int)Math.floor(numLinks/2);
				if(start < 1){
					end += Math.abs(start)+1;
					start = 1;
				}
				if(end > numPages){
					start -= (end - numPages);
					end = numPages;
				}
			}
			
			if(start > 1){
				output.append("....");
			}
			
			for(int i=start; i <= end; i++){
				if(page == i){
					output.append(" <b> ").append(i).append("</b>");
				}else{
					output.append("<a href=\"").append(pageUrl).append(i).append("\">")
					.append(i).append("</a>");
				}
			}
			
			if(end < numPages){
				output.append("....");
			}
		}
		
		if(page < numPages){
			output.append("<a href=\"").append(pageUrl).append(page+1).append("\">")
			.append(textNext).append("</a>")
			.append("<a href=\"").append(pageUrl).append(numPages).append("\">")
			.append(textLast).append("</a>");
		}
		Map<String, Object> valueMap = new HashMap<String, Object>();
		valueMap.put("start", total > 0 ? ((page - 1) * limit) + 1 : 0);
		valueMap.put("end", (((page - 1) * limit) > (total - limit)) ? total : (((page - 1) * limit) + limit));
		valueMap.put("total", total);
		valueMap.put("pages", numPages);
		
		String outputStr = output.toString();
		if(outputStr.length()>0){
			outputStr = "<div class=\""+styleLinks+"\">"+outputStr+"</div>";
		}
		outputStr += "<div class=\""+styleResults+"\">"
				+ StrSubstitutor.replace(text, valueMap, "{", "}")
				+ "</div>";
		return outputStr;
	}
	
}


