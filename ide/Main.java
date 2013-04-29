package ide;
import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import mainframe.GraphicsModule;
import mainframe.MainFrame;
import modules.SingleFileProjectManager;
import modules.TextEditor;

import com.sun.java.swing.plaf.gtk.GTKLookAndFeel;
import com.sun.java.swing.plaf.motif.MotifLookAndFeel;


public class Main {

	/**
	 * @param args
	 * @throws ConflictException 
	 * @throws UnsupportedLookAndFeelException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws ConflictException, UnsupportedLookAndFeelException{
		//UIManager.setLookAndFeel(new com.sun.java.swing.plaf.gtk.GTKLookAndFeel());

		RussianPost rp = new RussianPost(); //added Russian post
		Configuration conf = new Configuration();
		
		MainFrame mf = new MainFrame(rp);
		///// modules for testing
		/*AlphaModule am = new AlphaModule(mf, rp, conf);
		BetaModule bm = new BetaModule(mf,rp, conf);
		GammaModule gm = new GammaModule(mf, rp, conf);*/
		/////
		new TextEditor(mf, rp, conf);
		new SingleFileProjectManager(mf, rp, conf);
		System.out.println("[DONE] ");
		
	}

}
