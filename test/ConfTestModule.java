package test;

import java.io.*;
import java.util.*;
import java.lang.*;
import ide.Configuration;
import ide.Module;
import ide.RussianPost;
import mainframe.MainFrame;

// module for config-testing

public class ConfTestModule extends Module {
	public ConfTestModule(MainFrame mf, RussianPost rp, Configuration conf){
		super(mf, rp, conf);
		
		// ask-register test
		String className = this.getClass().getName();
		boolean c = conf.ask(className);
		System.out.println(c);
		conf.register(className);
		c = conf.ask(className);
		System.out.println(c);
		
		//add-get test
		int counts = 200;
		conf.add(className, "counts", counts);
		int counts1 = 0;
		try {
			counts1 = (int) conf.get(className, "counts");
		} catch(Exception ex) {
		
		}
		/*System.out.println(counts1);
		String serTest = "hellooo-o!";
		try {
			ObjectOutputStream os = new ObjectOutputStream (new FileOutputStream("SerTest.txt")); // not sure about it
			os.writeObject(serTest); // writing object
			os.close();
		} catch(IOException ex) {
			// exception!
		}
		serTest = "Noooo-ooo!";
		try {
			ObjectOutputStream os = new ObjectOutputStream (new FileOutputStream("SerTest.txt")); // not sure about it
			os.writeObject(serTest); // writing object
			os.close();
		} catch(IOException ex) {
			// exception!
		}
		
		*/
		
		// input-output-stream tests
		
		
	
	}
}
