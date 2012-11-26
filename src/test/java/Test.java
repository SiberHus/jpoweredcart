
public class Test {

	public static class Slow{
		public Slow()throws Exception{
			Thread.sleep(1000);
		}
	}
	
	public static void main(String[] args) throws Exception{
		Test t = new Test();
		System.out.println("HI");
	}
}
