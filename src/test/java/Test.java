import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.validation.AbstractBindingResult;
import org.springframework.validation.BindingResult;


public class Test {

	public static class Slow{
		public Slow()throws Exception{
			Thread.sleep(1000);
		}
	}
	
	public static void main(String[] args) throws Exception{
		Date d = new Date();
		SimpleDateFormat f = new SimpleDateFormat("EEEE");
		System.out.println(f.format(d));
	}
}
