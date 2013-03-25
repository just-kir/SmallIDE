import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Set;



public class RussianPost {
	
	Hashtable<String, MsgRcvr> IncPost = new Hashtable<String, MsgRcvr>();
	
	
	public void addIncPostBox (String name, MsgRcvr mr) {
		IncPost.put(name, mr);// smth wrong
	}
	
	public void addOutPostBox(String name) {
	
	} 
	
	public void subscribe(String name) {
		
	}
	
	
	
	
	public void send(String name, String msg) {
		IncPost.get(name).handle(msg);
	}



}
