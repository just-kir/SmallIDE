package test;
import ide.Configuration;
import ide.Module;
import ide.MsgRcvr;
import ide.RussianPost;
import mainframe.MainFrame;

//module for testing

public class GammaModule extends Module implements MsgRcvr{

	public GammaModule(MainFrame mf, RussianPost rp, Configuration conf){
		super(mf, rp, conf);
		// russian post testing
		try {
					Thread.sleep(5000);
				} catch(InterruptedException ex) {ex.printStackTrace(); }
		rp.subscribe("puppy", this);

		// here or in Main()
	}
	
	public void handle(Object msg) {
		System.out.println(msg);
	
	}

	
	

}
