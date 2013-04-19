package ide;
import mainframe.MainFrame;


public abstract class Module {
	protected MainFrame mainframe;
	protected RussianPost russianpost;
	protected Configuration configuration;
	
	
	public Module(MainFrame mf, RussianPost rp, Configuration conf){
		mainframe = mf;
		russianpost = rp; // added russian post in constructor
		configuration = conf; // added conf in constructor
	}
	
	public void menuClick(int index){
	}
	
	public void buttonClick(int index){
	}
}
