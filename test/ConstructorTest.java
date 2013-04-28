package test;
import java.io.*;
import java.lang.*;

public class ConstructorTest {

	public ConstructorTest(){
		//read from file
		try {
			File testFile = new File("constructor");
			FileReader fileReader = new FileReader(testFile);
			BufferedReader reader = new BufferedReader(fileReader);
			String line = reader.readLine();
			System.out.println(line);
			// good reading!	
		} catch (Exception ex){
			ex.printStackTrace();
		}
		// some magic
		//Class c = Class.forName(line);
		//c tt = new c(mf, rp, conf);
	}
	
	public String ReadName(){
		return null;
	}
}
