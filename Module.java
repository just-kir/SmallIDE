
public abstract class Module {
	protected MainFrame mainframe;
	protected RussianPost russianpost;
	
	
	public Module(MainFrame mf, RussianPost rp){
		mainframe = mf;
		russianpost = rp; // added russian post in constructor
	}
	
	public void menuClick(int index){
	}
	
	public void buttonClick(int index){
	}
}
