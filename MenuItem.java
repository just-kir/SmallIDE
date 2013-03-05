import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;


public class MenuItem extends JMenuItem implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Module m;
	private int index;
	
	public MenuItem(String s, int index, Module m) {
		super(s);
		this.m = m;
		this.index = index;
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		m.menuClick(index);
	}

	public Module getModule() {
		return m;
	}
	
}
