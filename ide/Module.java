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
	
	/*
	 * false - ready to exit
	 * true - not ready
	 * if one of modules say true exitting will be cancelled
	 */
	
	public boolean onExit(){
		return false;
	}
	
	@Override
	public String toString() {
		return this.getClass().getCanonicalName();
	}
}
