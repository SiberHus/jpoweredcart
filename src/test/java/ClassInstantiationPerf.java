
public class ClassInstantiationPerf {

	public static void main(String[] args) throws Exception{
		long t1 = System.currentTimeMillis();
		long m1 = Runtime.getRuntime().freeMemory();
		
		for(int i=0;i<100000;i++){
			CreateMe.class.newInstance();
//			new CreateMe();
		}
		long tElapse = System.currentTimeMillis()-t1;
		long mElapse = m1-Runtime.getRuntime().freeMemory();
		System.out.println(tElapse);
		System.out.println(mElapse/1024);
	}
}

class CreateMe {
	
}