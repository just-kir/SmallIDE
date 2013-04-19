package mainframe;
import ide.Configuration;
import ide.Module;
import ide.RussianPost;


public abstract class GraphicsModule extends Module {

	public GraphicsModule(MainFrame mf, RussianPost rp, Configuration conf) {
		super(mf, rp, conf);
		
	}

	public abstract void onTabClose();

}
