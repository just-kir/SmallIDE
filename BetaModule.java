import java.util.concurrent.TimeUnit;

public class BetaModule extends Module implements MsgRcvr{

	public BetaModule(MainFrame mf, RussianPost rp, Configuration conf){
		super(mf, rp, conf);
		
		////rp testing
		rp.send("kitty", "Hey from beta-module!"); // it works!
		
		
		Thread t = new Thread(new Runnable(){
			public void run(){
				OutBox testbox;
				testbox = russianpost.addOutPostBox("puppy");
				try {
					Thread.sleep(10000);
				} catch(InterruptedException ex) {ex.printStackTrace(); }
		
				testbox.putAndSend("Voice from BetaModule!");
		
				////rp testing
			}
		});
		t.start();
	}
	
	public void handle(String msg) {
	}

}




