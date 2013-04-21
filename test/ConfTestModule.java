package test;
import ide.Configuration;
import ide.Module;
import ide.RussianPost;
import mainframe.MainFrame;

// module for config-testing

public class ConfTestModule extends Module {
	public ConfTestModule(MainFrame mf, RussianPost rp, Configuration conf){
		super(mf, rp, conf);
		
		// ask-register test
		boolean c = conf.ask(this);
		System.out.println(c);
		conf.register(this);
		c = conf.ask(this);
		System.out.println(c);
		
		
		int counts = 200;
		conf.add(this, "counts", counts);
		int counts1 = 0;
		try {
			counts1 = (int) conf.get(this, "counts");
		} catch(Exception ex) {
		
		}
		System.out.println(counts1);
	
	}
}
