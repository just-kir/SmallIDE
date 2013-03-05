import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Graphics;
import java.lang.reflect.InvocationTargetException;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


public class MainFrame implements Runnable{
	private JFrame frame;
	private JMenuBar menubar;
	private JPanel buttonPanel;
	private int menuIndex = 0;
	private int buttonIndex = 0;
	
	public MainFrame() {
		try {
			SwingUtilities.invokeAndWait(this);
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		frame = new JFrame("SmallIDE");
		frame.setVisible(false);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300,300);
		frame.setLayout(new BorderLayout());
		
		JPanel northPanel = new JPanel(new BorderLayout());
		
		menubar = new JMenuBar();
		northPanel.add(menubar,BorderLayout.NORTH);
		
		/*JMenu m1 = new JMenu("sdfsdf");
		menubar.add(m1);
		m1.add(new JMenuItem("sfssdfsgggggdf"));*/
		
		buttonPanel = new JPanel();
		buttonPanel.add(new JButton("fuck"));
		northPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		frame.add(northPanel, BorderLayout.NORTH);
		
		frame.setVisible(true);
	}
	
	public int addMenu(String path, Module m) throws ConflictException{
		String[] ss = path.split("/");
		if(ss.length == 1) return -1;
		JMenu jm = null;
		int i, l = menubar.getMenuCount();
		for(i = 0; i < l; i++){
			if(menubar.getMenu(i).getText().equalsIgnoreCase(ss[0])){
				jm = menubar.getMenu(i);
				break;
			}
		}
		if(i == l){
			jm = new JMenu(ss[0]);
			menubar.add(jm);
		}
		for(int j = 1; j < ss.length - 1; j++){
			Component[] c = jm.getMenuComponents();
			l = c.length;
			for(i = 0; i < l; i++){
				if(c[i] instanceof JMenu){
					if(((JMenu)c[i]).getText().equalsIgnoreCase(ss[j])){
						jm = (JMenu)c[i];
						break;
					}
				}else if(c[i] instanceof MenuItem){
					if(((MenuItem)c[i]).getText().equalsIgnoreCase(ss[j])){
						throw new ConflictException(((MenuItem)c[i]).getModule(), m);
					}
				}
			}
			if(i == l){
				JMenu njm = new JMenu(ss[j]);
				jm.add(njm);
				jm = njm;
			}
		}
		Component[] c = jm.getMenuComponents();
		l = c.length;
		for(i = 0; i < l; i++){
			if(c[i] instanceof JMenu){
				if(((JMenu)c[i]).getText().equalsIgnoreCase(ss[ss.length-1])){
					throw new ConflictException(m, m); //TODO norm exception for jmenu
				}
			}else if(c[i] instanceof MenuItem){
				if(((MenuItem)c[i]).getText().equalsIgnoreCase(ss[ss.length-1])){
					throw new ConflictException(((MenuItem)c[i]).getModule(), m);
				}
			}
		}
		if(i == l){
			MenuItem nmi = new MenuItem(ss[ss.length-1], menuIndex, m);
			jm.add(nmi);
		}
		return menuIndex++;
	}
	
}
