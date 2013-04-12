import java.io.*;
import java.util.*;

public class Configuration implements Serializable{

	Hashtable<Module, ArrayList<Object>> SysConf = new Hashtable<Module, ArrayList<Object>>();
	
	public Configuration() {
		// trying to fill SysConf
		try {
			ObjectInputStream is = new ObjectInputStream(new FileInputStream("SysConf.ser"));
			SysConf =(Hashtable) is.readObject(); // makes some unsefty things
		} catch(Exception ex) {
			// exception
		}
	}

	public void add(Module self, Object forserialize) {
		SysConf.get(self).add(forserialize); // be carefull!
		try {
			ObjectOutputStream os = new ObjectOutputStream (new FileOutputStream("SysConf.ser")); // not sure about it
			os.writeObject(SysConf); // writing object
			os.close();
		} catch(IOException ex) {
			// exception!
		}
	}
	
	public Object get() { // not working yet
		Object c = null;
		return c;
	}
	
	public boolean ask(Module self) {
		boolean c = false;
		if (SysConf.get(self) != null) {
			c = true;
		}
		return c;
	}
	
	public void register(Module self){
		ArrayList<Object> myobjects = new ArrayList<Object>();
		SysConf.put(self, myobjects); // register module 
	
	}
}
