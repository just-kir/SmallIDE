import java.util.*;


public class RussianPost {
	
	Hashtable<String, MsgRcvr> IncPost = new Hashtable<String, MsgRcvr>();
	
	Hashtable<String, ArrayList<MsgRcvr>> OutPost= new Hashtable<String, ArrayList<MsgRcvr>>(); // fuck!! It works!
	
	
	public void addIncPostBox (String name, MsgRcvr mr) {
		IncPost.put(name, mr);
	}
	
	public OutBox addOutPostBox(String name) {
		ArrayList<MsgRcvr> sub = new ArrayList<MsgRcvr>();
		OutPost.put(name, sub); // adding note in OutPost 
		
		OutBox newbox = new OutBox(name);
		return newbox;
	} 
	
	public void subscribe(String name, MsgRcvr self) {
		OutPost.get(name).add(self);
	}
	
	
	
	
	public void send(String name, String msg) {
		IncPost.get(name).handle(msg);
	}



}
