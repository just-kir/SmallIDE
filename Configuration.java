import java.io.*;
import java.util.*;

public class Configuration implements Serializable{

	Hashtable<Module, ArrayList<Object>> SysConf = new Hashtable<Module, ArrayList<Object>>();


	public void add(Module self, Object forserialize) {
		SysConf.get(self).add(forserialize); // be carefull!
	}
	
	public Object get() {
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
