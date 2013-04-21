package ide;
import java.io.*;
import java.util.*;

public class Configuration implements Serializable{

	private Hashtable<Module, Hashtable<String, Object>> sysConf = new Hashtable<Module, Hashtable<String, Object>>();
	
	public Configuration() {
		// trying to fill SysConf
		try {
			ObjectInputStream is = new ObjectInputStream(new FileInputStream("SysConf.ser"));
			sysConf =(Hashtable) is.readObject(); // makes some unsefty things
		} catch(Exception ex) {
			// exception
		}
	}
	
	public void ser_staff(){
		
	}


	public void add(Module self, String objname, Object forserialize) {
		// add variable for serialization and serializing SysConf
		sysConf.get(self).put(objname, forserialize); // be carefull!
		try {
			ObjectOutputStream os = new ObjectOutputStream (new FileOutputStream("SysConf.ser")); // not sure about it
			os.writeObject(sysConf); // writing object
			os.close();
		} catch(IOException ex) {
			// exception!
		}
	}
	
	public Object get(Module name, String objname) { // should work!
		Object c = null;
		// Restoring	
		try {
			ObjectInputStream is = new ObjectInputStream(new FileInputStream("SysConf.ser"));
			sysConf =(Hashtable) is.readObject(); // makes some unsefty things
			
		} catch(Exception ex) {
			// exception
		}
		// giving away
		c = sysConf.get(name).get(objname);
		
		
		return c;
	}
	
	public boolean ask(Module self) {
		boolean c = false;
		if (sysConf.get(self) != null) {
			c = true;
		}
		return c;
	}
	
	public void register(Module self){
		Hashtable<String, Object> myobjects = new Hashtable<String, Object>();
		sysConf.put(self, myobjects); // register module 
	
	}
}
