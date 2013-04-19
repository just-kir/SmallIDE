package mainframe;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import javax.swing.plaf.metal.MetalSplitPaneUI;

public class SplitPane extends JSplitPane implements ChangeListener {
	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	private int mainPane;
	private JTabbedPane tp;
	//private JComponent main;
	
	public SplitPane(JComponent left, JComponent right, int oriention, int main, double resize) {
		super(oriention, true, main == LEFT ? left : null, main == RIGHT ? right : null);
		this.tp = (JTabbedPane)(main == LEFT ? right : left);
		//this.main = (main == LEFT ? left : right);
		//setOneTouchExpandable(true);
		mainPane = main;
		setDividerSize(5);
		setDividerLocation(resize);
		setResizeWeight(resize);
		tp.addChangeListener(this);
		/*JButton b = ((JButton) ((MetalSplitPaneUI)getUI()).getDivider().getComponent(1));
		b.doClick();
		
		System.out.println(b.toString());*/
	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		if(tp.getTabCount() > 0){
			if(mainPane == LEFT){
				setRightComponent(tp);
			}else{
				setLeftComponent(tp);
			}
		}else{
			if(mainPane == LEFT){
				setRightComponent(null);
			}else{
				setLeftComponent(null);
			}
		}
	}
}
