//module for testing

public class GammaModule extends Module implements MsgRcvr{

	public GammaModule(MainFrame mf, RussianPost rp){
		super(mf, rp);
		// russian post testing
		try {
					Thread.sleep(5000);
				} catch(InterruptedException ex) {ex.printStackTrace(); }
		rp.subscribe("puppy", this);

		// here or in Main()
	}
	
	public void handle(String msg) {
		System.out.println(msg);
	
	}

	
	

}
