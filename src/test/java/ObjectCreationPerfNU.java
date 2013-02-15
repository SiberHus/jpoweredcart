import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;


public class ObjectCreationPerfNU {
	
	public static class Slow{
		public Slow()throws Exception{
			Thread.sleep(1000);
		}
	}
	
	public static void main(String[] args) throws Exception{
		int total = 10000;
		A array[] = new A[total];
		long t1 = System.currentTimeMillis();
		long m1 = Runtime.getRuntime().freeMemory();
		for(int i=0;i<total;i++){
			array[i] = new A();
//			BeanUtils.copyProperties(new A(), array[i]);
			array[i].getA1();
			array[i].getA2();
			array[i].getA3();
			array[i].getA4();
		}
		long tElapse = System.currentTimeMillis()-t1;
		long mElapse = m1-Runtime.getRuntime().freeMemory();
		System.out.println(tElapse);
		System.out.println(mElapse/1024);
	}
}

class A {
	private String a1;
	private String a2;
	private String a3;
	private String a4;
	public String getA1() {
		return a1;
	}
	public void setA1(String a1) {
		this.a1 = a1;
	}
	public String getA2() {
		return a2;
	}
	public void setA2(String a2) {
		this.a2 = a2;
	}
	public String getA3() {
		return a3;
	}
	public void setA3(String a3) {
		this.a3 = a3;
	}
	public String getA4() {
		return a4;
	}
	public void setA4(String a4) {
		this.a4 = a4;
	}
}

class ExtendedA extends A{
	private List<String> l1 = new ArrayList<String>();
	private List<String> l2 = new ArrayList<String>();
	private List<String> l3 = new ArrayList<String>();
	private List<String> l4 = new ArrayList<String>();
	private Map<String, String> m1 = new HashMap<String, String>();
	private Map<String, String> m2 = new HashMap<String, String>();
	private Map<String, String> m3 = new HashMap<String, String>();
	private Map<String, String> m4 = new HashMap<String, String>();
	public List<String> getL1() {
		return l1;
	}
	public void setL1(List<String> l1) {
		this.l1 = l1;
	}
	public List<String> getL2() {
		return l2;
	}
	public void setL2(List<String> l2) {
		this.l2 = l2;
	}
	public List<String> getL3() {
		return l3;
	}
	public void setL3(List<String> l3) {
		this.l3 = l3;
	}
	public List<String> getL4() {
		return l4;
	}
	public void setL4(List<String> l4) {
		this.l4 = l4;
	}
	public Map<String, String> getM1() {
		return m1;
	}
	public void setM1(Map<String, String> m1) {
		this.m1 = m1;
	}
	public Map<String, String> getM2() {
		return m2;
	}
	public void setM2(Map<String, String> m2) {
		this.m2 = m2;
	}
	public Map<String, String> getM3() {
		return m3;
	}
	public void setM3(Map<String, String> m3) {
		this.m3 = m3;
	}
	public Map<String, String> getM4() {
		return m4;
	}
	public void setM4(Map<String, String> m4) {
		this.m4 = m4;
	}
}