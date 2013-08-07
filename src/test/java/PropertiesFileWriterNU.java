import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Properties;


public class PropertiesFileWriterNU {

	public static void main(String[] args) throws Exception{
		Properties props = new Properties();
		props.load(new FileReader("tmp/config.properties"));
		props.setProperty("val1", "12345");
		props.store(new FileWriter("tmp/config.properties"), "");
	}
}
