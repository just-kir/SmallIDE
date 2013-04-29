package test;
import ide.Configuration;
import ide.Module;
import ide.MsgRcvr;
import ide.RussianPost;
import mainframe.MainFrame;

// module for testing

public class AlphaModule extends Module implements MsgRcvr{

	public AlphaModule(){
		super(null,null,null);
		System.out.println("FUCK!");
	}

	public AlphaModule(MainFrame mf, RussianPost rp, Configuration conf){
		super(mf, rp, conf);
		System.out.println("Fuck me right now");
		// russian post testing
		rp.addIncPostBox("kitty", this);
		// fuck! System.out.println(rp.IncPost.get("kitty")); // it works!
		

		// here or in Main()
	}
	
	public void handle(Object msg) {
		System.out.println(msg);
	
	}

	//rp.addIncPostBox("kitty", this);
	

}
