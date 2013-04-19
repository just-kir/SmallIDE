package ide;
import java.util.*;

public class OutBox {
	
	ArrayList<MsgRcvr> list;
	
	
	public OutBox(ArrayList<MsgRcvr> sub) {
		list = sub;
	}
	
	
	public void putAndSend(String msg) {
		// sending messages to subscribers
		for(int i = 0; i< list.size(); i++) {
			list.get(i).handle(msg);
		}
		
	}
}
