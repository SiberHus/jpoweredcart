import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;


public class GenerateStaticSettingKeys {

	public static void main(String args[]) throws Exception{
		BufferedReader bin = new BufferedReader(new FileReader("tables.csv"));
		BufferedWriter bout = new BufferedWriter(new FileWriter("out.txt"));
		String line;
		while( (line=bin.readLine())!=null){
			if(line.length()>7 && line.substring(0, 7).equals("config_")){
				bout.write("public static final String CFG_"+line.substring(7).toUpperCase()+" = \""+line+"\";\n");
			}else{
				bout.write("public static final String "+line.toUpperCase()+" = \""+line+"\";\n");
			}
		}
		bout.flush();
		bout.close();
		bin.close();
	}
	
}
