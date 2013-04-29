package ide;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

//import java.lang.*;
import java.io.*;
import java.util.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import mainframe.GraphicsModule;
import mainframe.MainFrame;
import modules.SingleFileProjectManager;
import modules.TextEditor;

import test.ConfTestModule;
import test.ConstructorTest;
import test.AlphaModule;

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
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws OnvocationTargetException 
	 */
	
	private static ArrayList<Module> moduleList;
	 
	public static void main(String[] args) throws ConflictException, UnsupportedLookAndFeelException,
	ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException,
	IllegalArgumentException, InvocationTargetException{
	
		
		moduleList = new ArrayList<Module>();
		//UIManager.setLookAndFeel(new com.sun.java.swing.plaf.gtk.GTKLookAndFeel());

		RussianPost rp = new RussianPost(); //added Russian post
		Configuration conf = new Configuration();
		

		MainFrame mf = new MainFrame(rp);

		///// modules for testing
		ConfTestModule ctm = new ConfTestModule(mf, rp, conf);
		ConstructorTest con =  new ConstructorTest();
		
		
		//constructor
		try {
			File testFile = new File("constructor");
			FileReader fileReader = new FileReader(testFile);
			BufferedReader reader = new BufferedReader(fileReader);
			
			String line = null;
			while ((line = reader.readLine()) != null) {
				
				//System.out.println(line);
				// good reading!
			
				Class cs = Class.forName(line);
				Class[] types = {MainFrame.class, RussianPost.class, Configuration.class};
				Constructor construct = cs.getConstructor(types);
				Object[] parms = {mf, rp, conf};
			
				Module m = (Module) construct.newInstance(parms);
				moduleList.add(m);
			}
			
			
			
				
		} catch (Exception ex){
			ex.printStackTrace();
		}
		// some magic
		/*Class cs = Class.forName(line);
		Class[] types = {MainFrame.class, RussianPost.class, Configuration.class};
		Constructor construct = cs.getConstructor(types);
		Object[] parms = {mf, rp, conf};
		Module Alpha = (Module) construct.newInstance(parms);
		*/
		//
		
		
		
		/*AlphaModule am = new AlphaModule(mf, rp, conf);
		BetaModule bm = new BetaModule(mf,rp, conf);
		GammaModule gm = new GammaModule(mf, rp, conf);*/
		/////
		new TextEditor(mf, rp, conf);
		new SingleFileProjectManager(mf, rp, conf);
		System.out.println("[DONE] ");
		
	}

}
