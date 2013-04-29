package ide;
import java.util.*;

public class OutBox {
	
	private ArrayList<MsgRcvr> list;
	
	public int getNumberOfListeners(){
		return list.size();
	}
	
	public OutBox(ArrayList<MsgRcvr> sub) {
		list = sub;
	}
	
	
	public void putAndSend(Object msg) {
		// sending messages to subscribers
		for(int i = 0; i< list.size(); i++) {
			list.get(i).handle(msg);
		}
		
	}
}
