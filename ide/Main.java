package ide;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import mainframe.GraphicsModule;
import mainframe.MainFrame;

import test.ConfTestModule;
import test.ConstructorTest;

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
		MainFrame mf = new MainFrame();

		RussianPost rp = new RussianPost(); //added Russian post
		Configuration conf = new Configuration();
		
		
		///// modules for testing
		ConfTestModule ctm = new ConfTestModule(mf, rp, conf);
		ConstructorTest con =  new ConstructorTest();
		/*AlphaModule am = new AlphaModule(mf, rp, conf);
		BetaModule bm = new BetaModule(mf,rp, conf);
		GammaModule gm = new GammaModule(mf, rp, conf);*/
		/////
		
		
		
		GraphicsModule m = new GraphicsModule(mf, rp, conf) {

			@Override
			public void menuClick(int index) {
				System.out.println("fuck "+index);
			}
			
			@Override
			public void buttonClick(int index) {
				System.out.println("button fuck "+index);
			}
			
			@Override
			public String toString() {
				
				return "fucking sample module";
			}

			@Override
			public void onTabClose() {
				System.out.println("I'll be back!");
				
			}
		};
		try{
		mf.addMenu("File/fucking.../fucking/....fuck file", m);
		mf.addMenu("Edit/copypast", m);
		mf.addMenu("File/fucking.../let this file fuck me",m);
		
		
		}catch(Exception e){e.printStackTrace();}
		mf.addGraphicsModule(m, MainFrame.TOP, "fucktop1").add(new JButton("fuck"));
		mf.addGraphicsModule(m, MainFrame.TOP, "fucktop2").add(new JButton("fuck1"));
		mf.addGraphicsModule(m, MainFrame.BOTTOM, "fuckbottom").add(new JButton("fuck2"));
		mf.addGraphicsModule(m, MainFrame.LEFT, "fuckleft").add(new JButton("fuck3"));
		mf.addGraphicsModule(m, MainFrame.RIGHT, "fuckright").add(new JButton("fuck4"));
		mf.addGraphicsModule(m, MainFrame.BOTTOM, "fuckbot2").add(new JButton("fuck5"));
		mf.addGraphicsModule(m, MainFrame.RIGHT, "fuckrig2").add(new JButton("fuck6"));
		System.out.println("[DONE] ");
		
	}

}
